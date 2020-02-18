package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_bus_user")
public class User extends BaseModel {
    /**
     * 是否vip
     */
    private Integer isVip;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPasswd;

    /**
     * 是否在使用
     */
    private Integer isUse;
}