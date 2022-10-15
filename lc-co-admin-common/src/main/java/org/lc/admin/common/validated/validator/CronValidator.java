package org.lc.admin.common.validated.validator;

import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronException;
import cn.hutool.cron.pattern.parser.PatternParser;
import org.lc.admin.common.validated.annotation.Cron;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: cron定时表达式验证器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 19:01
 */
public class CronValidator implements ConstraintValidator<Cron, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean flag = true;
        try {
            if (StrUtil.isNotBlank(s)) {
                PatternParser.parse(s);
            }
        } catch (CronException e) {
            flag = false;
        }
        return flag;
    }
}
