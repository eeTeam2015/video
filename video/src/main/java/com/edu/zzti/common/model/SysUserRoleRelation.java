package com.edu.zzti.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 用户角色关联表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_sys_user_role_relation")
public class SysUserRoleRelation extends BaseModel {
    /**
     * 用户角色
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;
}