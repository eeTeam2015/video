package com.edu.zzti.management.home.web;

import com.edu.zzti.common.enums.PermissionTypeEnums;
import com.edu.zzti.common.model.SysPermission;
import com.edu.zzti.common.util.EnvUtils;
import com.edu.zzti.management.home.web.vo.SysPermissionMenuTreeVo;
import com.edu.zzti.management.user.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 主页
 */
@Controller
@Api(tags = "首页管理")
@RequestMapping(value = "/manager")
public class HomeController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value = "首页")
    @GetMapping("")
    @ApiIgnore
    public String index(Model model) {

        //查询菜单列表
        List<SysPermission> menus = sysPermissionService.getByUserId(EnvUtils.getCurrentUserId());
        Map<String, SysPermissionMenuTreeVo> sysPermissionMenuTreeVoMap = menus.stream()
                .filter(sysPermission -> {
                    if (PermissionTypeEnums.MENU.getStringValue().equals(sysPermission.getType())) {
                        return true;
                    }
                    return false;
                })
                .map(sysPermission -> {
                    SysPermissionMenuTreeVo sysPermissionMenuTreeVo = new SysPermissionMenuTreeVo();
                    sysPermissionMenuTreeVo.setId(sysPermission.getId());
                    sysPermissionMenuTreeVo.setName(sysPermission.getName());
                    sysPermissionMenuTreeVo.setUrl(sysPermission.getUrl());
                    sysPermissionMenuTreeVo.setPid(sysPermission.getPid());
                    sysPermissionMenuTreeVo.setChild(new ArrayList<>());
                    return sysPermissionMenuTreeVo;
                }).collect(Collectors.toMap(SysPermissionMenuTreeVo::getId, a -> a));
        for (Map.Entry<String, SysPermissionMenuTreeVo> entry : sysPermissionMenuTreeVoMap.entrySet()) {
            SysPermissionMenuTreeVo value = entry.getValue();
            if (!"0".equals(value.getPid())) {
                sysPermissionMenuTreeVoMap.get(value.getPid()).getChild().add(value);
            }
        }
        List<SysPermissionMenuTreeVo> collect = sysPermissionMenuTreeVoMap.values().stream().filter(sysPermissionMenuTreeVo -> {
            if (!"0".equals(sysPermissionMenuTreeVo.getPid())) {
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());
        model.addAttribute("menus", collect);
        model.addAttribute("userName", EnvUtils.getCurrentUserName());
        return "home/index";
    }

}
