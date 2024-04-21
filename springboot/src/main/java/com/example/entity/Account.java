package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Role User Parent Class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    /** Uid */
    private Integer uid;

    /** Username */
    private String username;

    /** Name */
    private String name;

    /** Password */
    private String password;

    /** Role */
    private String role;

    /** NewPassword */
    private String newPassword;

    /** Avatar */
    private String avatar;

    /** Phone */
    private String phone;

    /** Email */
    private String email;

    /** Token */
    private String token;

}
