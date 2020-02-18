package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.model.SysUserRoleRelation;
import com.edu.zzti.common.dao.BaseDao;

/**
 * 用户角色关联表
 */
public interface SysUserRoleRelationMapper extends BaseDao<SysUserRoleRelation> {

    /**
     * 更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysUserRoleRelation record);
}