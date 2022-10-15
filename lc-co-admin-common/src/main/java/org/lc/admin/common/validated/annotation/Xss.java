package org.lc.admin.common.validated.annotation;


import org.lc.admin.common.validated.validator.XssValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: xss注入检验注解
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 15:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {XssValidator.class})
public @interface Xss {

    String message() default "不允许运行任何脚本";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
