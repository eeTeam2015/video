package com.edu.zzti.management.user.service;

import com.edu.zzti.management.user.web.vo.SysRolePermissionRelationQueryVo;
import com.edu.zzti.common.model.SysRolePermissionRelation;

import java.util.List;

/**
 * 角色权限关联服务
 */
public interface SysRolePermissionRelationService {

    /**
     * 
     * 查询角色权限信息
     * @param roleId
     * @return
     */
    List<SysRolePermissionRelation> selectSysPermissionRelationRoleInfo(String roleId);

    /**
     * 查询角色权限信息
     * @param roleIds
     * @return
     */
    List<SysRolePermissionRelation> selectSysPermissionRelationRoleInfo(List<String> roleIds);

    /**
     * 增加关联信息
     * @param sysRolePermissionRelationQueryVo
     */
    void addRolePerMissionRelation(SysRolePermissionRelationQueryVo sysRolePermissionRelationQueryVo);

}
