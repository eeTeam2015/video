package com.edu.zzti.common.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xgp
 * @description:
 * @date 2019-11-18 20:38
 */
@Aspect
@Component
public class WebLogAspect {


    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    // ..表示包及子包 该方法代表controller层的所有方法
    @Pointcut("execution(* com.edu.zzti.foreign ..*.*(..))")
    public void controllerMethod() {
    }


    @Before("controllerMethod()")
    public void LogRequestInfo(JoinPoint joinPoint) throws Exception {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuffer requestLog = new StringBuffer();
        requestLog.append("请求信息：")
                .append("URL = {" + request.getRequestURI() + "},\t")
                .append("HTTP_METHOD = {" + request.getMethod() + "},\t")
                .append("IP = {" + request.getRemoteAddr() + "},\t")
                .append("CLASS_METHOD = {" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "},\t");

        if(joinPoint.getArgs().length == 0) {
            requestLog.append("ARGS = {} ");
        } else {
            requestLog.append("ARGS = " + new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(joinPoint.getArgs()[0]) + "");
        }

        logger.info(requestLog.toString());
    }
}
