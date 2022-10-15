package org.lc.admin.common.annotation;

import java.lang.annotation.*;

/**
 * Description: 重复提交注解
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-05 17:32
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatRequest {

    /**
     * 间隔时间(ms)，小于此时间视为重复提交 默认5000ms
     */
    int interval() default 5000;

    /**
     * 提示消息
     */
    String message() default "存在重复提交请求";
}
