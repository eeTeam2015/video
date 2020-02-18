package com.edu.zzti.management.home.web.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单树结构
 */
@Data
@Api(value = "菜单模板")
public class SysPermissionMenuTreeVo {

    @ApiModelProperty(value = "菜单id")
    private String id;

    @ApiModelProperty(value = "父菜单id")
    private String pid;

    @ApiModelProperty(value = "菜单路径")
    private String url;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "子菜单")
    private List<SysPermissionMenuTreeVo> child;
}
