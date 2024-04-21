package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User service layer
 **/
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * Add a user
     */
    public void add(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setRole(RoleEnum.USER.name());
        userMapper.insert(user);
    }

    /**
     * Delete a user by UID
     */
    public void deleteById(Integer uid) {
        userMapper.deleteById(uid);
    }

    /**
     * Batch delete users
     */
    public void deleteBatch(List<Integer> uids) {
        for (Integer uid : uids) {
            userMapper.deleteById(uid);
        }
    }

    /**
     * Update a user by UID
     */
    public void updateById(User user) {
        userMapper.updateById(user);
    }

    /**
     * Select a user by UID
     */
    public User selectById(Integer uid) {
        User user = userMapper.selectById(uid);
        String tokenData = user.getUid() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, user.getPassword());
        user.setToken(token);
        return user;
    }

    /**
     * Select all users
     */
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    /**
     * Page query users
     */
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    /**
     * User login
     */
    public Account login(Account account) {
        Account dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // Generate token
        String tokenData = dbUser.getUid() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    /**
     * User registration
     *
     * @return
     */
    public Account register(Account account) {
        User user = new User();
        BeanUtils.copyProperties(account, user);
        add(user);
        return account;
    }


    /**
     * Update password
     */
    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }

}
