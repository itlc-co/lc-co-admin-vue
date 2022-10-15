package org.lc.admin.common.validated.validator;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.validated.annotation.InvokeTarget;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: 调用目标字符串验证器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 19:02
 */
public class InvokeTargetValidator implements ConstraintValidator<InvokeTarget, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StrUtil.isBlank(s) || (StrUtil.isNotBlank(s) && StrUtil.containsIgnoreCase(s, "()") && StrUtil.count(s, ".") >= 1);
    }
}
