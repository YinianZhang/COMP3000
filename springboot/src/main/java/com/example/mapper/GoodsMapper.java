package com.example.mapper;

import com.example.entity.Goods;

import java.util.List;

/**
 * Interface for operations related to the Goods data
 */
public interface GoodsMapper {

    /**
     * Add a goods
     */
    int insert(Goods goods);

    /**
     * Delete a goods by GID
     */
    int deleteById(Integer gid);

    /**
     * Update a goods by ID
     */
    int updateById(Goods goods);

    /**
     * Select a goods by ID
     */
    Goods selectById(Integer gid);

    /**
     * Select all goodss
     */
    List<Goods> selectAll(Goods goods);

}
