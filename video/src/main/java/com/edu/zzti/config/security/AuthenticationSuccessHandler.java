package com.edu.zzti.config.security;

import com.edu.zzti.common.model.SysUser;
import com.edu.zzti.management.user.service.SysUserService;
import com.edu.zzti.common.util.EnvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 自定义认证成功后处理
 */
@Slf4j
@Service
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private SysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        String currentUserId = EnvUtils.getCurrentUserId();
        SysUser sysUser = sysUserService.getSysUserInfo(currentUserId);
        sysUser.setLastLoginIpAddress(request.getRemoteAddr());
        sysUser.setLastLoginTime(new Date());
        Integer loginCount = sysUser.getLoginCount();
        if (loginCount == null) {
            loginCount = 0;
        }
        sysUser.setLoginCount(loginCount + 1);
        sysUserService.update(sysUser);

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest) {
            String targetUrl = savedRequest.getRedirectUrl();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
            clearAuthenticationAttributes(request);
        }
    }
}


