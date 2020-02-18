package com.edu.zzti.config.security;

import com.edu.zzti.management.user.service.SysPermissionService;
import com.edu.zzti.management.user.service.SysUserService;
import com.edu.zzti.common.model.SysPermission;
import com.edu.zzti.common.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpLyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setAccount(userName);
        sysUser = sysUserService.select(sysUser);
        if (null == sysUser) {
            throw new UsernameNotFoundException(userName);
        }
        /**
         * 获取到用户按关联的所有角色对应的所有权限(资源)
         */
        List<SysPermission> sysPermissionList = sysPermissionService.getByUserId(sysUser.getId());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissionList) {
            authorities.add(new SimpleGrantedAuthority(sysPermission.getId()));
        }
        return new SecurityUser(
                sysUser.getId(),
                sysUser.getName(),
                sysUser.getOrgId(),
                sysUser.getPassword(),
                sysUser.getAccount(),
                authorities);
    }
}
