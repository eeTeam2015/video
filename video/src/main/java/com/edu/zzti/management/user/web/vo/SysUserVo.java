package com.edu.zzti.management.user.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import lombok.Data;
import java.util.Date;

/**
 * 用户列表信息VO
 */
@Data
@Api(value = "用户列表信息VO")
public class SysUserVo {

    /**
     * 主键id
     */
    private String id;

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
    private String status;

    /**
     * 是否启用，0：禁用 1：启用
     */
    private String enable;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastLoginTime;

    /**
     * 最后登录ip地址
     */
    private String lastLoginIpAddress;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 备注
     */
    private String remark;
}
