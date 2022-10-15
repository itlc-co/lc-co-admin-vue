package org.lc.admin.common.validated.validator;

import cn.hutool.core.util.ReUtil;
import org.lc.admin.common.validated.annotation.Xss;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: xss注入参数检验器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 11:11
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

    /**
     * html正则匹配表达式
     */
    private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

    /**
     * Description: 是否检验通过
     *
     * @param value                      值
     * @param constraintValidatorContext 约束验证器上下文
     * @return boolean true 校验通过 false 校验不通过
     * @date 2022-10-07 11:10
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !containsHtml(value);
    }

    /**
     * Description: 是否包含html文本
     *
     * @param value 值
     * @return boolean true 包含 false 不包含
     * @date 2022-10-07 11:09
     */
    public boolean containsHtml(String value) {
        return ReUtil.isMatch(HTML_PATTERN, value);
    }


}
