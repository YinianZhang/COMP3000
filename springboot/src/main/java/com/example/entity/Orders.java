package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Orders Data Table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** User ID */
    private Integer userId;
    /** Order ID */
    private String orderId;
    /** Order time */
    private String time;
    /** Order owner name */
    private String username;
    /** Order owner phone */
    private String phone;
    /** Order owner address */
    private String address;
    /** Order buy goods number */
    private Integer num;
    /** Order status */
    private String status;
    /** Order total price*/
    private Double price;
    /** Order goods name */
    private String buyName;
    /** Order owner name */
    private String userName;
    /** Order goods img */
    private String goodsImg;
    /** Order goods name */
    private String goodsName;
    /** Order goods id */
    private String goodsId;

}