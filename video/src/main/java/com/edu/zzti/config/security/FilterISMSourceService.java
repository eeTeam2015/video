package com.edu.zzti.config.security;

import com.edu.zzti.management.user.service.SysPermissionService;
import com.edu.zzti.management.user.web.vo.SysPermissionVo;
import com.edu.zzti.common.model.SysPermission;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 权限信息提供
 */
@Service
public class FilterISMSourceService implements FilterInvocationSecurityMetadataSource {


    private HashMap<String, Collection<ConfigAttribute>> map = null;

    @Autowired
    private SysPermissionService sysPermissionService;


    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，
     * 如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。
     * 如果不在权限表中则放行。
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    /**
     * 加载权限表所有权限(资源)
     * 按照key为资源url，value为资源id
     */
    public void loadResourceDefine() {
        map = new HashMap<>(16);
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<SysPermissionVo> permissions = sysPermissionService.selectSysPermissionInfo(new SysPermission());
        for (SysPermissionVo permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getId());
            //请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为AccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            if (StringUtils.isNotBlank(permission.getUrl())) {
                map.put(permission.getUrl(), array);
            }
        }
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
