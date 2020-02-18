package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 用户角色表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_sys_role")
public class SysRole extends BaseModel {
    /**
     * 角色别名
     */
    private String alias;

    /**
     * 角色名
     */
    private String name;
}