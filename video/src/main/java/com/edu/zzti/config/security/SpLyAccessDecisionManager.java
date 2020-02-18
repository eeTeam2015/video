package com.edu.zzti.config.security;

import com.edu.zzti.management.user.service.SysUserRoleRelationService;
import com.edu.zzti.common.model.SysUserRoleRelation;
import com.edu.zzti.common.util.EnvUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限控制器
 *
 */
@Service
public class SpLyAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;

    private List<String> roleIdList = null;

    @Value("${admin.role.id}")
    private String adminRoleId;


    //decide 方法是判定是否拥有权限的决策方法，
    //authentication 是SpLyUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
    //object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
    //configAttributes 为FilterISMSourceService的getAttributes(Object object)这个方法返回的结果，
    // 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(CollectionUtils.isEmpty(roleIdList)){
            List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationService.selectSysUserRelationRoleInfo(EnvUtils.getCurrentUserId());
            roleIdList = sysUserRoleRelations.stream().map(sysUserRoleRelation -> sysUserRoleRelation.getRoleId()).collect(Collectors.toList());
        }

        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }
        ConfigAttribute c;
        String permissionId;
        for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            c = iter.next();
            permissionId = c.getAttribute();
            //超级管理员全部权限
            if(!CollectionUtils.isEmpty(roleIdList)){
                if (roleIdList.contains(adminRoleId)) {
                    return;
                }
            }

            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (permissionId.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no permission");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
