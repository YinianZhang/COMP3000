package com.example.mapper;

import com.example.entity.Notice;
import java.util.List;

/**
 * Interface for operations related to the Notice data
 */
public interface NoticeMapper {

    /**
     * Add a notice
     */
    int insert(Notice notice);

    /**
     * Delete a notice by ID
     */
    int deleteById(Integer id);

    /**
     * Update a notice by ID
     */
    int updateById(Notice notice);

    /**
     * Select a notice by ID
     */
    Notice selectById(Integer id);

    /**
     * Select all notices
     */
    List<Notice> selectAll(Notice notice);

}
