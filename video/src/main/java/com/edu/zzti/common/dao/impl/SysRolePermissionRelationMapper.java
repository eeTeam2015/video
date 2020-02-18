package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.model.SysRolePermissionRelation;
import com.edu.zzti.common.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRolePermissionRelationMapper extends BaseDao<SysRolePermissionRelation> {

    /**
     * 查询角色权限信息
     * @param roleIds
     * @return
     */
    List<SysRolePermissionRelation> selectByRoleIds(@Param("roleIds") List<String> roleIds);
}