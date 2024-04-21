package com.example.mapper;

import com.example.entity.Orders;

import java.util.List;

/**
 * Interface for operations related to the Orders data
 */

public interface OrdersMapper {

    /**
     * Add
     */
    int insert(Orders orders);

    /**
     * Delete
     */
    int deleteById(Integer id);

    /**
     * Update
     */
    int updateById(Orders orders);

    /**
     * Select a order by ID
     */
    Orders selectById(Integer id);

    /**
     * Select all pet
     */
    List<Orders> selectAll(Orders orders);

}