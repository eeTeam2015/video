package com.edu.zzti.management.user.service;

import com.github.pagehelper.PageInfo;
import com.edu.zzti.common.model.SysUser;
import com.edu.zzti.common.service.BaseService;
import com.edu.zzti.management.user.web.vo.SysUserVo;


/**
 * service层接口
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    SysUser getSysUserInfo(String id);

    /**
     * 获取所有信息
     * @param sysUser
     * @param page
     * @param limit
     * @return
     */
    PageInfo<SysUserVo> selectSysUserInfo(SysUser sysUser, int page, int limit);

    /**
     * 修改信息
     * @param sysUser
     */
    void update(SysUser sysUser);

    /**
     * 查询用户信息
     * @param sysUser
     * @return
     */
    SysUser select(SysUser sysUser);
}
