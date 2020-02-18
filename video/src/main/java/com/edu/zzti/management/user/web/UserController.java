package com.edu.zzti.management.user.web;

import com.edu.zzti.common.constants.ReturnStatusConstant;
import com.edu.zzti.common.model.SysUser;
import com.edu.zzti.common.model.SysUserRoleRelation;
import com.edu.zzti.management.user.service.SysUserRoleRelationService;
import com.edu.zzti.management.user.service.SysUserService;
import com.edu.zzti.management.user.web.vo.SysUserInfoQueryVo;
import com.edu.zzti.management.user.web.vo.SysUserInfoVo;
import com.edu.zzti.management.user.web.vo.SysUserVo;
import com.github.pagehelper.PageInfo;
import com.edu.zzti.common.enums.OperateStatusEnums;
import com.edu.zzti.common.util.EnvUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理界面
 */
@Slf4j
@Controller
@Api(tags = "用户信息管理{用户管理:/user/manager}")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ApiOperation(value = "获取用户列表")
    @GetMapping
    public String sysUserInfo(){
        return "user/sys-user-info";
    }

    @ApiOperation(value = "查询指定用户信息")
    @GetMapping("/get-sys-user-info/{id}")
    @ResponseBody
    @ApiImplicitParam(name = "id",value = "主键id",type = "path")
    public SysUser getSysUserInfo(@PathVariable("id") String id){
        return sysUserService.getSysUserInfo(id);
    }

    @ApiOperation(value = "查询所有用户角色信息")
    @GetMapping("/select-sys-user-info")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页数",defaultValue = "1",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "limit",value = "每页数量",defaultValue = "10",dataType = "int",paramType = "query",
                    example ="1,10")
    })
    public SysUserInfoVo selectSysUserInfo(SysUser sysUser, int page, int limit){

        PageInfo<SysUserVo> pageInfo = sysUserService.selectSysUserInfo(sysUser,page,limit);
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        sysUserInfoVo.setCode(OperateStatusEnums.SUCCESS.getStringValue());
        sysUserInfoVo.setCount(Long.valueOf(pageInfo.getTotal()).intValue());
        sysUserInfoVo.setData(pageInfo.getList());
        return sysUserInfoVo;
    }

    @ApiOperation(value = "查询用户关联角色信息")
    @GetMapping("/select-sys-user-relation-role-info")
    @ResponseBody
    public List<SysUserRoleRelation> selectSysUserRelationRoleInfo(String userId){
        return sysUserRoleRelationService.selectSysUserRelationRoleInfo(userId);
    }

    @ApiOperation(value = "添加/编辑用户用户信息")
    @PostMapping("/add-or-update-sys-user-info")
    @ResponseBody
    public ReturnStatusConstant addOrUpdateSysUserInfo(@RequestBody SysUserInfoQueryVo sysUserInfoQueryVo){
        ReturnStatusConstant ReturnStatusConstant = new ReturnStatusConstant();
        try {

            if(StringUtils.hasText(sysUserInfoQueryVo.getId())){
                SysUser sysUserInfo = sysUserService.getSysUserInfo(sysUserInfoQueryVo.getId());
                if (sysUserInfo != null && !sysUserInfo.getPassword().equals(sysUserInfoQueryVo.getPassword())) {
                    //密码加密
                    sysUserInfoQueryVo.setPassword(passwordEncoder.encode(sysUserInfoQueryVo.getPassword()));
                }
                sysUserInfoQueryVo.beforeUpdate();
                sysUserInfoQueryVo.setUpdateBy(EnvUtils.getCurrentUserId());
                sysUserService.update(sysUserInfoQueryVo);
            }else{
                //密码加密
                sysUserInfoQueryVo.setPassword(passwordEncoder.encode(sysUserInfoQueryVo.getPassword()));
                sysUserInfoQueryVo.beforeInsert();
                sysUserInfoQueryVo.setCreateBy(EnvUtils.getCurrentUserId());
                sysUserService.insert(sysUserInfoQueryVo);
            }

            /**更新角色信息 1.删除原有的角色关联 2.添加新的角色关联*/
            sysUserRoleRelationService.updateUserRoleRelation(sysUserInfoQueryVo);

            ReturnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            ReturnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            log.error("添加/编辑用户用户信息：",e);
            ReturnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            ReturnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return ReturnStatusConstant;
    }

    @ApiOperation(value = "删除用户用户信息")
    @PostMapping("/delete-sys-user-info")
    @ResponseBody
    public ReturnStatusConstant deleteSysUserInfo(@RequestParam("id") @ApiParam(name = "id",value = "主键id") String id){
        ReturnStatusConstant ReturnStatusConstant = new ReturnStatusConstant();
        try {
            sysUserService.deleteByPrimaryKey(id);
            sysUserRoleRelationService.deleteByUserId(id);
            ReturnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            ReturnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            log.error("删除信息出错：",e);
            ReturnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            ReturnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return ReturnStatusConstant;
    }
}
