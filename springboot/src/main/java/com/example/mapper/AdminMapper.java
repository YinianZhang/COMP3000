package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Interface for operations related to Admin data
 */
public interface AdminMapper {

    /**
     * Insert a new Admin
     */
    int insert(Admin admin);

    /**
     * Delete an Admin by ID
     */
    int deleteById(Integer id);

    /**
     * Update an Admin by ID
     */
    int updateById(Admin admin);

    /**
     * Select an Admin by ID
     */
    Admin selectById(Integer id);

    /**
     * Select all Admins
     */
    List<Admin> selectAll(Admin admin);

    /**
     * Select an Admin by username
     */
    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);
}
