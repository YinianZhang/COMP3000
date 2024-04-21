package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Notice Data Table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Notice NID */
    private Integer nid;
    /** Title */
    private String title;
    /** Content */
    private String content;
    /** Creation Time */
    private String time;
    /** Creator */
    private String user;



}
