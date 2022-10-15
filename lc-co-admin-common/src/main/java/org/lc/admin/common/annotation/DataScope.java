package org.lc.admin.common.annotation;

import java.lang.annotation.*;

/**
 * Description: 数据范围注解
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-03 16:27
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 部门表的别名
     */
    String deptAlias() default "sd";

    /**
     * 用户表的别名
     */
    String userAlias() default "su";

}
