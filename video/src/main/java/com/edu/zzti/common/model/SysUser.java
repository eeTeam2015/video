package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 数据展示层
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_sys_user")
public class SysUser extends BaseModel {
    /**
     * 用户名称
     */
    private String name;

    /**
     * 所属组织id
     */
    private String orgId;

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态, 0：离线 1：在线
     */
    private Integer status;

    /**
     * 是否启用，0：禁用 1：启用
     */
    private Integer enable;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录ip地址
     */
    private String lastLoginIpAddress;

    /**
     * 登录次数
     */
    private Integer loginCount;

}