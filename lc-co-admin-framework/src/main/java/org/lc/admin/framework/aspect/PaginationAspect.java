package org.lc.admin.framework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.utils.system.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Description: 分页aop切面处理
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-10 19:41
 */
@Aspect
@Component
public class PaginationAspect {

    public static final Logger log = LoggerFactory.getLogger(PaginationAspect.class);


    /**
     * Description: 切入点
     *
     * @date 2022-09-10 19:49
     */
    @Pointcut("@annotation(org.lc.admin.common.annotation.Pagination)")
    public void pointCut() {

    }

    /**
     * Description: 织入面前切入
     *
     * @param point 切入点
     * @throws Throwable 异常
     * @date 2022-09-10 19:49
     */
    @Before("pointCut()")
    public void before(JoinPoint point) throws Throwable {
        // 参数数组
        Object[] args = point.getArgs();
        // 分页注解实例
        Pagination pagination = getPaginationAnnotation(point);
        // 开启分页
        startPage(pagination);
    }

    /**
     * Description: 开始分页
     *
     * @param pagination 分页注解
     * @date 2022-09-10 20:03
     */
    private void startPage(Pagination pagination) {
        PageUtils.startPage(pagination);
    }

    /**
     * Description: 根据切入点获取分页注释
     *
     * @param point 切入点
     * @return {@link Pagination } 分页注释
     * @date 2022-09-24 10:55
     */
    private Pagination getPaginationAnnotation(JoinPoint point) {
        // 获取方法签名器从而获取Method实例进而获取分页注解
        return ((MethodSignature) point.getSignature()).getMethod().getAnnotation(Pagination.class);
    }


}
