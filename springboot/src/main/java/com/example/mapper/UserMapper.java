package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Interface for operations related to User data
 */
public interface UserMapper {

    /**
     * Insert a new User
     */
    int insert(User user);

    /**
     * Delete a user by UID
     */
    int deleteById(Integer uid);

    /**
     * Update a user by ID
     */
    int updateById(User user);

    /**
     * Select a user by ID
     */
    User selectById(Integer uid);

    /**
     * Select all users
     */
    List<User> selectAll(User user);

    /**
     * Select a user by username
     */
    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);
}
