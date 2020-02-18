package com.edu.zzti.management.user.web.vo;

import com.edu.zzti.common.model.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息列表Vo
 */
@Data
@ApiModel(value = "用户信息请求参数")
@EqualsAndHashCode(callSuper = false)
public class SysUserInfoQueryVo extends SysUser {

    @ApiModelProperty(value = "选中的角色id")
    private String role;
}
