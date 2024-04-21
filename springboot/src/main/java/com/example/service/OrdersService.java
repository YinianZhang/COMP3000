package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.entity.Orders;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.GoodsMapper;
import com.example.mapper.OrdersMapper;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Business logic for Order Information Table
 **/
@Service
public class OrdersService {

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    GoodsMapper goodsMapper;

    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final int RANDOM_PART_LENGTH = 6;
    private static final Random RANDOM = new Random();
    /**
     * Add
     */
    @Transactional
    public void add(Orders orders) {
        // Check if the balance is sufficient
        User user = userMapper.selectById(orders.getUserId());
        if (user.getWallet() < orders.getPrice()) {
            throw new CustomException(ResultCodeEnum.ACCOUNT_LOW_ERROR);
        }
        orders.setTime(DateUtil.now());
        // Generate order number
        orders.setOrderId(generateOrderNumber());
        ordersMapper.insert(orders);
        // Reduce user balance
        user.setWallet(user.getWallet() - orders.getPrice());
        userMapper.updateById(user);
        // Reduce inventory
        Goods goods = goodsMapper.selectById(Integer.parseInt(orders.getGoodsId()));
        goods.setNumb(goods.getNumb() - orders.getNum());
        goodsMapper.updateById(goods);
    }

    /**
     * Delete
     */
    public void deleteById(Integer id) {
        ordersMapper.deleteById(id);
    }

    /**
     * Batch delete
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            ordersMapper.deleteById(id);
        }
    }

    /**
     * Update
     */
    public void updateById(Orders orders) {
        ordersMapper.updateById(orders);
    }

    /**
     * Query by ID
     */
    public Orders selectById(Integer id) {
        return ordersMapper.selectById(id);
    }

    /**
     * Query all
     */
    public List<Orders> selectAll(Orders orders) {
        return ordersMapper.selectAll(orders);
    }

    /**
     * Paging query
     */
    public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            orders.setUserId(currentUser.getUid());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Orders> list = ordersMapper.selectAll(orders);
        return PageInfo.of(list);
    }

    /**
     * Generate order number
     */
    public static String generateOrderNumber() {
        // Get current timestamp and format it as a string
        String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());

        // Generate a random number string of specified length
        StringBuilder randomPart = new StringBuilder();
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            randomPart.append(RANDOM.nextInt(10));
        }

        // Concatenate the order number
        return timestamp + randomPart.toString();
    }
}
