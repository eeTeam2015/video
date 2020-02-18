package com.edu.zzti.management.user.web.vo;


import com.edu.zzti.common.model.SysPermission;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限信息Vo
 */
@Data
@ApiModel(value = "权限信息")
@EqualsAndHashCode(callSuper = true)
public class SysPermissionVo extends SysPermission {
}
