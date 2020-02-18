package com.edu.zzti.common.dao.impl;

import com.edu.zzti.common.dao.BaseDao;
import com.edu.zzti.common.model.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysPermissionMapper extends BaseDao<SysPermission> {

    /**
     * 根据permissionIds查询权限信息
     * @param permissionIds
     * @return
     */
    List<SysPermission> selectByIds(@Param("permissionIds") List<String> permissionIds);

    /**
     * 根据用户id获取权限(资源)
     * @param userId
     * @return
     */
    @Select(value = "select *\n" +
            "from t_sys_permission sp\n" +
            "left join t_sys_role_permission_relation srpr on srpr.permission_id = sp.id\n" +
            "left join t_sys_user_role_relation surr on srpr.role_id = surr.role_id\n" +
            "where surr.user_id = #{userId};")
    List<SysPermission> getByUserId(@Param(value = "userId") String userId);
}