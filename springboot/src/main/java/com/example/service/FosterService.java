package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.Foster;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.FosterMapper;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Business logic for Foster Information Table
 **/
@Service
public class FosterService {

    @Resource
    private FosterMapper fosterMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * Add
     */
    @Transactional
    public void add(Foster foster) {
        // Check if the balance is sufficient
        User user = userMapper.selectById(foster.getUserId());
        if (user.getWallet() < foster.getSpend().doubleValue()) {
            throw new CustomException(ResultCodeEnum.ACCOUNT_LOW_ERROR);
        }
        // Reduce user balance
        user.setWallet(user.getWallet() - foster.getSpend().doubleValue());
        userMapper.updateById(user);
        // Query available rooms
        List<Integer> integers = fosterMapper.selectRoomIds();
        ArrayList<Integer> rooms = new ArrayList<>();
        for (int i1 = 1; i1 < 51; i1++) {
            rooms.add(i1);
        }
        if (!integers.isEmpty()){
            for (Integer integer : integers) {
                rooms.remove(integer);
            }
        }
        if (rooms.size() == 0){
            throw new CustomException(ResultCodeEnum.ROOM_NUM_ERROR);
        }
        foster.setFid(0);
        foster.setRoom_id(rooms.get(0));
        fosterMapper.insert1(foster);
    }


    /**
     * Delete by ID
     */
    public void deleteById(Integer fid) {
        fosterMapper.deleteById(fid);
    }

    /**
     * Batch delete
     */
    public void deleteBatch(List<Integer> fids) {
        for (Integer fid : fids) {
            fosterMapper.deleteById(fid);
        }
    }

    /**
     * Update by ID
     */
    public void updateById(Foster foster) {
        fosterMapper.updateById(foster);
    }

    /**
     * Query by ID
     */
    public Foster selectById(Integer fid) { return fosterMapper.selectById(fid);}

    /**
     * Query all
     */
    public List<Foster> selectAll(Foster foster) {
        return fosterMapper.selectAll(foster);
    }

    /**
     * Page query
     */
    public PageInfo<Foster> selectPage(Foster foster, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Foster> list = fosterMapper.selectAll1(foster);
        return PageInfo.of(list);
    }

    public List<Foster> selectAllByUserId(int userId) {
        List<Foster> list = fosterMapper.selectAllByUserId(userId);
        return list;
    }
    public List<Foster> selectByNameAndUser(String pet_name, int userId) {
        return  fosterMapper.selectByNameAndUser(pet_name,userId);
    }

    public List<Foster> selectByUserId(String petName) {

        return  fosterMapper.selectByUserId("%"+petName+"%");
    }
}
