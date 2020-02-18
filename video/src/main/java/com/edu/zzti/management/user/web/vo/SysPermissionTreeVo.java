package com.edu.zzti.management.user.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 权限数据树需要Vo
 */
@Data
@ApiModel(value = "权限数据树需要Vo")
public class SysPermissionTreeVo {

    @ApiModelProperty(value = "权限列表")
    private List<SysPermissionVo> list;

    @ApiModelProperty(value = "已选中的权限id")
    private List<String> checkedId;
}
