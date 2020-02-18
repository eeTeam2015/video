package com.edu.zzti.management.user.service;

import com.edu.zzti.management.user.web.vo.SysPermissionInfoQueryVo;
import com.edu.zzti.management.user.web.vo.SysPermissionVo;
import com.github.pagehelper.PageInfo;
import com.edu.zzti.common.model.SysPermission;

import java.util.List;

/**
 * 权限服务层接口
 */
public interface SysPermissionService {

    /**
     * 获取指定id的权限信息
     * @param id
     * @return
     */
    SysPermission getSysPermissionInfo(String id);

    /**
     * 条件查询权限信息
     * @param sysPermission
     * @return
     */
    List<SysPermissionVo> selectSysPermissionInfo(SysPermission sysPermission);

    /**
     * 更新权限信息
     * @param sysPermissionInfoQueryVo
     */
    void update(SysPermissionInfoQueryVo sysPermissionInfoQueryVo);

    /**
     * 添加权限信息
     * @param sysPermissionInfoQueryVo
     */
    void insert(SysPermissionInfoQueryVo sysPermissionInfoQueryVo);

    /**
     * 删除权限信息
     * @param id
     */
    void deleteByPrimaryKey(String id);

    /**
     * 查询菜单信息
     * @return
     */
    List<SysPermission> getMenus();

    /**
     * 根据permissionIds查询权限信息
     * @param permissionIds
     * @return
     */
    List<SysPermission> selectSysPermission(List<String> permissionIds);

    /**
     * 根据swagger扫描更新权限列表
     * @return
     */
    Boolean updatePermissionBySwagger();

    /**
     * 分页查询角色信息
     * @param sysPermission
     * @param page
     * @param limit
     * @return
     */
    PageInfo<SysPermission> selectSysPermissions(SysPermission sysPermission, int page, int limit);

    /**
     * 根据用户id获取对应的权限(资源)
     * @param userId
     * @return
     */
    List<SysPermission> getByUserId(String userId);
}
