package com.example.mapper;

import com.example.entity.Foster;

import java.util.List;

/**
 * Interface for operations related to the Foster data
 */
public interface FosterMapper {
    List<Integer> selectRoomIds();
    /**
     * Add a foster
     */
    int insert1(Foster foster);
    /**
     * Delete a foster by FID
     */
    int deleteById(Integer fid);

    /**
     * Update a foster by ID
     */
    int updateById(Foster foster);

    /**
     * Select a foster by ID
     */
    Foster selectById(Integer fid);

    /**
     * Select all fosters
     */
    List<Foster> selectAll(Foster foster);

    List<Foster> selectAll1(Foster foster);
    List<Foster> selectAllByUserId(int userId);
    List<Foster> selectByNameAndUser(String fid, int userId);

    List<Foster> selectByUserId(String petName);
}
