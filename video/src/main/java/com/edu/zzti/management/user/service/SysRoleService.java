package com.edu.zzti.management.user.service;

import com.edu.zzti.common.model.SysRole;
import com.edu.zzti.common.service.BaseService;

import java.util.List;

/**
 * 角色service层接口
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 获取用户角色信息
     * @param id
     * @return
     */
    SysRole getSysRoleInfo(String id);

    /**
     * 获取所有角色信息
     * @param sysRole
     * @return
     */
    List<SysRole> selectSysRoleInfo(SysRole sysRole);

    /**
     * 修改角色信息
     * @param sysRole
     */
    void update(SysRole sysRole);
}
