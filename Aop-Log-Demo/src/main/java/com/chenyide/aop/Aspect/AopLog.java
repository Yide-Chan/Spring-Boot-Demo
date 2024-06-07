package com.chenyide.aop.Aspect;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.chenyide.aop.entity.LogEntity;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenyide
 * @version v1.0
 * @className AopLog
 * @description
 * @date 2024/6/8 0:03
 **/
@Slf4j
@Aspect
@Component
public class AopLog {

    @Autowired
    public LogEntity entity;
    private static final String UNKNOWN = "unknown";

    @Pointcut("execution(public * com.chenyide.aop.controller.AopLogController.*(..))")
    public void logPointcut() {
    }

    // 前置通知
    @Before("logPointcut()")
    public void doBefore(JoinPoint point) {

    }

    // 后置通知
    @AfterReturning("logPointcut()")
    public void doAfterReturning(JoinPoint point) {

    }

    // 环绕通知 ProceedingJoinPoint是JoinPoint的子接口，并且只能在@Around中用
    @Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 打印请求相关参数
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        String header = request.getHeader("User-Agent");

        // 使用一些库来解析和验证User-Agent字符串，以确保它符合预期的格式和内容。
        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        final LogEntity l = LogEntity.builder()
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .ip(getIp(request))
                .url(request.getRequestURL().toString())
                .classMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName()))
                .httpMethod(request.getMethod())
                .requestParams(getNameAndValue(joinPoint))
                .result(result)
                .timeCost(System.currentTimeMillis() - startTime)
                .userAgent(header)
                .browser(userAgent.getBrowser().toString())
                .os(userAgent.getOperatingSystem().toString()).build();

        log.info("Request Log Info : {}", JSONUtil.toJsonStr(l));

        return result;
    }

    /**
     *  获取方法参数名和参数值
     * @param joinPoint
     * @return
     */
    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    // 异常通知
    @AfterThrowing("logPointcut()")
    public void doAfterThrowing(JoinPoint joinPoint) {

    }

    // 最终通知
    @After("logPointcut()")
    public void doAfter(JoinPoint joinPoint) {

    }

}
