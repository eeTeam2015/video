package com.edu.zzti.management.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.edu.zzti.common.dao.impl.SysUserMapper;
import com.edu.zzti.common.enums.EnableStatusEnums;
import com.edu.zzti.common.enums.OnlineStatusEnums;
import com.edu.zzti.common.model.SysUser;
import com.edu.zzti.common.service.impl.BaseServiceImpl;
import com.edu.zzti.management.user.service.SysUserService;
import com.edu.zzti.management.user.web.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户service层接口
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getSysUserInfo(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<SysUserVo> selectSysUserInfo(SysUser sysUser, int page, int limit) {
        PageHelper.startPage(page,limit);
        List<SysUser> select = sysUserMapper.select(sysUser);
        List<SysUserVo> sysUserVos = new ArrayList<>();
        for (SysUser sysUser1:select) {
            SysUserVo sysUserVo = new SysUserVo();
            sysUserVo.setId(sysUser1.getId());
            sysUserVo.setAccount(sysUser1.getAccount());
            EnableStatusEnums enableStatusEnums = EnableStatusEnums.ofValue(sysUser1.getEnable());
            sysUserVo.setEnable(enableStatusEnums == null ? "" : enableStatusEnums.getName());
            sysUserVo.setLastLoginIpAddress(sysUser1.getLastLoginIpAddress());
            sysUserVo.setLastLoginTime(sysUser1.getLastLoginTime());
            sysUserVo.setLoginCount(sysUser1.getLoginCount());
            sysUserVo.setName(sysUser1.getName());
            sysUserVo.setOrgId(sysUser1.getOrgId());
            OnlineStatusEnums onlineStatusEnums = OnlineStatusEnums.ofValue(sysUser1.getStatus());
            sysUserVo.setStatus(onlineStatusEnums == null ? "" : onlineStatusEnums.getName());
            sysUserVo.setPassword(sysUser1.getPassword());
            sysUserVo.setRemark(sysUser1.getRemark());
            sysUserVos.add(sysUserVo);
        }
        return new PageInfo<>(sysUserVos);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public SysUser select(SysUser sysUser) {
        return sysUserMapper.selectOne(sysUser);
    }
}
