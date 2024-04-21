package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Pet
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet extends Account implements Serializable {
    private static final long serialVersionUID = 1L;

    /** PID */
    private Integer pid;

    /** Img */
    private String img;

    /** Name */
    private String name;

    /** Sex */
    private String sex;

    /** Age */
    private Integer age;

    /** Type */
    private String type;

    /** Status */
    private String status;

    /** Introduction */
    private String intro;


}