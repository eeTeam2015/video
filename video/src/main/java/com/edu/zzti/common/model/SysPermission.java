package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_sys_permission")
public class SysPermission extends BaseModel {
    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限路径
     */
    private String url;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 父权限id
     */
    private String pid;

    /**
     * 权限类型：1:菜单 2:接口
     */
    private String type;

    /**
     * 接口请求方法类型
     */
    private String method;
}