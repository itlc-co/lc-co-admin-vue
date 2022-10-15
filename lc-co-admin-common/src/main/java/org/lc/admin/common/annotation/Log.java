package org.lc.admin.common.annotation;

import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.enums.OperatorType;

import java.lang.annotation.*;

/**
 * Description: 日志注解
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 17:29
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块 默认空字符串
     */
    String module() default "";

    /**
     * 操作类型 默认其他
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别 默认后台用户
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存操作请求的参数 默认保存
     */
    boolean isSaveOperationParam() default true;

    /**
     * 是否保存响应结果集 默认保存
     */
    boolean isSaveResultResponse() default true;


}
