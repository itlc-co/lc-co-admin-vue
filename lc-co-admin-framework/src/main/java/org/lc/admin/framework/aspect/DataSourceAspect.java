package org.lc.admin.framework.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.lc.admin.common.annotation.DataSource;
import org.lc.admin.common.datasource.DynamicMultipleDataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Description: 数据源aop切面处理
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-06 11:51
 */
@Component
@Aspect
@Order(-1)
public class DataSourceAspect {

    public static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * Description: 织入点
     *
     * @date 2022-10-06 11:54
     */
    @Pointcut(value = "@annotation(org.lc.admin.common.annotation.DataSource) || @within(org.lc.admin.common.annotation.DataSource)")
    public void pointCut() {
    }

    /**
     * Description: 织入点执行前切入
     *
     * @param joinPoint 织入点
     * @date 2022-10-06 11:42
     */
    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        // 方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 数据源注解
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if (ObjectUtil.isNotNull(dataSource)) {
            // 配置数据源名称
            String dataSourceName = StrUtil.swapCase(dataSource.name().name());
            DynamicMultipleDataSourceContextHolder.setDataSourceName(dataSourceName);
            log.info(StrUtil.format("数据源已成功切换到{}", dataSourceName));
        }
    }

    /**
     * Description: 织入点执行后切入
     *
     * @date 2022-10-06 11:47
     */
    @After(value = "pointCut()")
    public void after() {
        // 清除当前上下文数据源名称
        String dataSourceName = DynamicMultipleDataSourceContextHolder.clearDataSourceName();
        log.info(StrUtil.format("数据源{}已成功关闭", dataSourceName));
    }

}
