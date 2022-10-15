package org.lc.admin.framework.aspect;

import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.lc.admin.common.annotation.RequestRateLimiter;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.exec.ServiceException;
import org.lc.admin.common.pool.RedisConstantsPool;
import org.lc.admin.common.utils.servlet.ServletUtils;
import org.lc.admin.framework.cache.entity.RedisEntity;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.redisson.api.RRateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Description: 请求限流切面处理
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-05 21:46
 */
@Aspect
@Component
public class RequestRateLimiterAspect {

    public static final Logger log = LoggerFactory.getLogger(RequestRateLimiterAspect.class);

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 织入点执行前切入
     *
     * @param joinPoint   织入点
     * @param rateLimiter 请求限流注解
     * @date 2022-10-05 22:14
     */
    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint joinPoint, RequestRateLimiter rateLimiter) throws InterruptedException {
        // 获取请求限流缓存键
        String key = getKey(joinPoint);
        // 结合redisson配置限流控制器
        RRateLimiter rRateLimiter = redisCacheService.getAndSetRateLimiter(key, rateLimiter);
        if (!rRateLimiter.tryAcquire()) {
            // 如何未获取到则抛出服务异常
            throw new ServiceException("请求过于频繁，请稍候再试");
        }
    }

    /**
     * Description: 获取redis键
     *
     * @param joinPoint 织入点
     * @return {@link String } redis键
     * @date 2022-10-05 22:14
     */
    private String getKey(JoinPoint joinPoint) {
        // 获取请求uri以及方法
        String uri = ServletUtils.getUri();
        String requestMethod = ServletUtils.getRequestMethod();

        if (!StrUtil.isAllNotBlank(uri, requestMethod)) {
            // service 异常
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }

        // 获取方法名称以及类名称
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();

        if (!StrUtil.isAllNotBlank(methodName, className)) {
            // service 异常
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }

        return RedisEntity.builder()
                .module(RedisConstantsPool.REQUEST_MODULE)
                .name(RedisConstantsPool.REQUEST_RATE_LIMITER_NAME)
                .key(StrUtil.format("{}::{}::{}::{}", requestMethod, uri, className, methodName))
                .build()
                .getKey();

    }

}
