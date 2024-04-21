package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Business logic for Goods Information Table
 **/
@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * Add
     */
    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }

    /**
     * Delete by GID
     */
    public void deleteById(Integer gid) {
         goodsMapper.deleteById(gid);
    }

    /**
     * Batch delete
     */
    public void deleteBatch(List<Integer> gids) {
        for (Integer gid : gids) {
            goodsMapper.deleteById(gid);
        }
    }

    /**
     * Update by ID
     */
    public void updateById(Goods goods) {
        goodsMapper.updateById(goods);
    }

    /**
     * Query by ID
     */
    public Goods selectById(Integer gid) { return goodsMapper.selectById(gid);}

    /**
     * Query all
     */
    public List<Goods> selectAll(Goods goods) {
        return goodsMapper.selectAll(goods);
    }

    /**
     * Page query
     */
    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(goods);
        return PageInfo.of(list);
    }

}
