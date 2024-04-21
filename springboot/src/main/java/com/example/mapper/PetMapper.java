package com.example.mapper;

import com.example.entity.Pet;
import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Interface for operations related to Pet data
 */
public interface PetMapper {

    /**
     * Insert a new Pet
     */
    int insert(Pet pet);

    /**
     * Delete a pet by PID
     */
    int deleteById(Integer pid);

    /**
     * Update a pet by ID
     */
    int updateById(Pet pet);

    /**
     * Select a pet by pID
     */
    Pet selectById(Integer pid);

    /**
     * Select all pet
     */
    List<Pet> selectAll(Pet pet);

    /**
     * Select a pet by name
     */
    @Select("select * from pet where name = #{name}")
    Pet selectByName(String name);
}
