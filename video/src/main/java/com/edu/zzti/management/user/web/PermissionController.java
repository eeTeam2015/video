package com.edu.zzti.management.user.web;

import com.edu.zzti.management.user.service.SysPermissionService;
import com.edu.zzti.management.user.service.SysRolePermissionRelationService;
import com.edu.zzti.management.user.web.vo.SysPermissionInfoQueryVo;
import com.edu.zzti.management.user.web.vo.SysPermissionInfoVo;
import com.edu.zzti.management.user.web.vo.SysPermissionTreeVo;
import com.github.pagehelper.PageInfo;
import com.edu.zzti.common.constants.ReturnStatusConstant;
import com.edu.zzti.common.enums.OperateStatusEnums;
import com.edu.zzti.common.model.SysPermission;
import com.edu.zzti.common.model.SysRolePermissionRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 权限信息管理
 */
@Slf4j
@Controller
@Api(tags = "权限信息管理{用户管理:/user/manager}")
@RequestMapping("/user/permission")
public class PermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRolePermissionRelationService sysRolePermissionRelationService;

    @ApiOperation(value = "获取权限列表")
    @GetMapping
    public String sysPermissionInfo(){
        return "user/sys-permission-info";
    }

    @ApiOperation(value = "查询指定权限信息")
    @GetMapping("/get-sys-permission-info/{id}")
    @ResponseBody
    public SysPermission getSysPermissionInfo(@PathVariable("id") @ApiParam(name = "id",value = "主键id") String id){
        return sysPermissionService.getSysPermissionInfo(id);
    }

    @ApiOperation(value = "查询所有的权限信息")
    @GetMapping("/get-all-sys-permission-info")
    @ResponseBody
    public SysPermissionTreeVo getAllSysPermissionInfo(String roleId){
        SysPermissionTreeVo sysPermissionTreeVo = new SysPermissionTreeVo();
        sysPermissionTreeVo.setList(sysPermissionService.selectSysPermissionInfo(new SysPermission()));
        List<SysRolePermissionRelation> sysRolePermissionRelations = sysRolePermissionRelationService.selectSysPermissionRelationRoleInfo(roleId);
        sysPermissionTreeVo.setCheckedId(sysRolePermissionRelations.stream().map(SysRolePermissionRelation::getPermissionId).collect(Collectors.toList()));
        return sysPermissionTreeVo;
    }

    @ApiOperation(value = "查询所有权限角色信息")
    @GetMapping("/select-sys-permission-info")
    @ResponseBody
    public SysPermissionInfoVo selectSysPermissionInfo(SysPermission sysPermission, int page, int limit){
        PageInfo<SysPermission> pageInfo = sysPermissionService.selectSysPermissions(sysPermission,page,limit);
        SysPermissionInfoVo sysPermissionInfoVo = new SysPermissionInfoVo();
        sysPermissionInfoVo.setCode(OperateStatusEnums.SUCCESS.getStringValue());
        sysPermissionInfoVo.setCount(Long.valueOf(pageInfo.getTotal()).intValue());
        sysPermissionInfoVo.setData(pageInfo.getList());
        return sysPermissionInfoVo;
    }

    @ApiOperation(value = "添加/编辑权限权限信息")
    @PostMapping("/add-or-update-sys-permission-info")
    @ResponseBody
    public ReturnStatusConstant addOrUpdateSysPermissionInfo(@RequestBody SysPermissionInfoQueryVo sysPermissionInfoQueryVo){
        ReturnStatusConstant ReturnStatusConstant = new ReturnStatusConstant();
        try {

            if(StringUtils.hasText(sysPermissionInfoQueryVo.getId())){
                sysPermissionInfoQueryVo.beforeUpdate();
                sysPermissionService.update(sysPermissionInfoQueryVo);
            }else{
                sysPermissionInfoQueryVo.beforeInsert();
                sysPermissionService.insert(sysPermissionInfoQueryVo);
            }

            ReturnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            ReturnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            log.error("添加/编辑权限权限信息：",e);
            ReturnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            ReturnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return ReturnStatusConstant;
    }

    @ApiOperation(value = "删除权限权限信息")
    @PostMapping("/delete-sys-permission-info")
    @ResponseBody
    public ReturnStatusConstant deleteSysPermissionInfo(@RequestParam("id") @ApiParam(name = "id",value = "主键id") String id){
        ReturnStatusConstant ReturnStatusConstant = new ReturnStatusConstant();
        try {
            sysPermissionService.deleteByPrimaryKey(id);
            ReturnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            ReturnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            log.error("删除信息出错：",e);
            ReturnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            ReturnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return ReturnStatusConstant;
    }

    @ApiOperation(value = "根据swagger扫描更新权限列表")
    @GetMapping("/update-permission-by-swagger")
    @ResponseBody
    @ApiIgnore
    public Boolean updatePermissionBySwagger(){
        return sysPermissionService.updatePermissionBySwagger();
    }
}
