package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Adopt Data Table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adopt implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Adopt AID */
    private Integer aid;
    /** User id */
    private Integer userId;
    /** Pet id */
    private Integer petId;
    /** Adopt time */
    private String atime;
    /** Adopt status */
    private String astatus;
    /** Adopt pet by username */
    private String userName;
    /** Adopt pet by petimg */
    private String petImg;
    /** Adopt pet by petname */
    private String petName;

}
