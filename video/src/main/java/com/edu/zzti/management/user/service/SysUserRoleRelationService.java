package com.edu.zzti.management.user.service;


import com.edu.zzti.management.user.web.vo.SysUserInfoQueryVo;
import com.edu.zzti.common.model.SysUserRoleRelation;

import java.util.List;

/**
 * 用户角色关联服务层
 */
public interface SysUserRoleRelationService {


    /**
     * 删除角色关联信息
     * @param id
     */
    void deleteByUserId(String id);

    /**
     * 更新用户角色信息关联
     * @param sysUserInfoQueryVo
     */
    void updateUserRoleRelation(SysUserInfoQueryVo sysUserInfoQueryVo);

    /**
     * 查询用户关联的角色信息
     * @param userId
     * @return
     */
    List<SysUserRoleRelation> selectSysUserRelationRoleInfo(String userId);

}
