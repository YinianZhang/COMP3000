package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Account implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Username */
    private String username;
    /** Password */
    private String password;
    /** Name */
    private String name;
    /** Phone */
    private String phone;
    /** Email */
    private String email;
    /** Avatar */
    private String avatar;
    /** Role */
    private String role;
    /** Wallet */
    private Double wallet = 0.00;



}