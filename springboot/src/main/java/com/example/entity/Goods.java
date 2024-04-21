package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Goods Data Table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /** GID */
    private Integer gid;
    /** Goods name */
    private String name;
    /** Goods image */
    private String gimg;
    /** Goods price */
    private Double price;
    /** Goods numb */
    private Integer numb;








}
