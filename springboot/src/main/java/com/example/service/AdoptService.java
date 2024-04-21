package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Adopt;
import com.example.mapper.AdoptMapper;
import com.example.mapper.AdoptMapper;
import com.example.mapper.PetMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Business logic for Adopt Information Table
 **/
@Service
public class AdoptService {

    @Resource
    private AdoptMapper adoptMapper;
    @Resource
    private PetMapper petMapper;


    /**
     * Add
     */
    public void add(Adopt adopt) {
        adopt.setAtime(DateUtil.today());
        adoptMapper.insert(adopt);
    }

    /**
     * Delete by ID
     */
    public void deleteById(Integer aid) {
        adoptMapper.deleteById(aid);
    }

    /**
     * Batch delete
     */
    public void deleteBatch(List<Integer> aids) {
        for (Integer aid : aids) {
            adoptMapper.deleteById(aid);
        }
    }

    /**
     * Update by ID
     */
    public void updateById(Adopt adopt) {
        adoptMapper.updateById(adopt);
    }

    /**
     * Query by ID
     */
    public Adopt selectById(Integer aid) { return adoptMapper.selectById(aid);}

    /**
     * Query all
     */
    public List<Adopt> selectAll(Adopt adopt) {
        return adoptMapper.selectAll(adopt);
    }

    /**
     * Page query
     */
    public PageInfo<Adopt> selectPage(Adopt adopt, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Adopt> list = adoptMapper.selectAll(adopt);
        return PageInfo.of(list);
    }

    public void updateById1(Adopt adopt) {
        adoptMapper.updateById1(adopt);
    }
}
