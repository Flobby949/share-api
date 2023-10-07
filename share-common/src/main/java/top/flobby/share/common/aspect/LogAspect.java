package top.flobby.share.common.aspect;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 请求日志切面
 * @create : 2023-10-07 10:22
 **/

@Aspect
@Slf4j
@Component
public class LogAspect {
    public LogAspect() {
        log.info("logAspect");
    }

    /**
     * 定义一个切点
     */
    @Pointcut("execution(public * top.flobby..*Controller.*(..))")
    public void controllerPointcut() {}

    /**
     * 前置增强
     *
     * @param joinPoint joinPoint
     */
    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 新增日志流水号
        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));

        // 请求日志准备
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        // 打印请求日志
        log.info("------------------ 开始 -------------------");
        log.info("请求地址：{} {}", request.getRequestURL().toString(), request.getMethod());
        log.info("类名方法：{}.{}", signature.getDeclaringTypeName(), name);
        log.info("远程地址：{}", request.getRemoteAddr());

        // 打印请求参数
        Object[] args = joinPoint.getArgs();
        // 排除特殊类型参数，如文件类型等
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        // 排除敏感字段，密码，身份证等
        String[] excludeProperties = {"phone", "password"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        log.info("请求参数：{}", JSON.toJSONString(arguments, excludeFilter));
    }


    /**
     * 环绕增强
     *
     * @param joinPoint joinPoint
     * @return {@link Object}
     */
    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        // 排除字段
        String[] excludeProperties = {"phone", "password"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        log.info("返回结果：{}", JSON.toJSONString(result, excludeFilter));
        log.info("------------------ 结束 耗时：{} ms -------------------", System.currentTimeMillis() - startTime);
        return result;
    }

}

