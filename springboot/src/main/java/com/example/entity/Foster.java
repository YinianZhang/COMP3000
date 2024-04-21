package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Foster Data Table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Foster implements Serializable {
    private static final long serialVersionUID = 1L;

    /** FID */
    private Integer fid;
    /** User ID */
    private Integer userId;
    /** Pet name */
    private String pet_name;
    /** Pet photo */
    private String photo;
    /** Foster time */
    private String ftime;
    /** Foster days */
    private Integer days;
    /** Foster service */
    private String service;
    /** Foster status */
    private String status;
    /** Foster room id */
    private Integer room_id;
    /** Foster spend */
    private BigDecimal spend=new BigDecimal(0);

    /** Foster user name */
    private String userName;

}
