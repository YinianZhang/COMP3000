package com.example.mapper;

import com.example.entity.Adopt;

import java.util.List;

/**
 * Interface for operations related to the Adopt data
 */
public interface AdoptMapper {

    /**
     * Add a Adopt
     */
    int insert(Adopt adopt);

    /**
     * Delete a Adopt by aID
     */
    int deleteById(Integer aid);

    /**
     * Update a Adopt by aID
     */
    int updateById(Adopt adopt);

    /**
     * Select a Adopt by aID
     */
    Adopt selectById(Integer aid);

    /**
     * Select all Adopts
     */
    List<Adopt> selectAll(Adopt adopt);

    int updateById1(Adopt adopt);
}
