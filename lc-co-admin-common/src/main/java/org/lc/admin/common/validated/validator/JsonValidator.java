package org.lc.admin.common.validated.validator;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONUtil;
import org.lc.admin.common.validated.annotation.Json;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: json格式字符串验证器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 19:02
 */
public class JsonValidator implements ConstraintValidator<Json, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean flag = true;
        try {
            if (StrUtil.isNotBlank(s)) {
                JSONUtil.parseObj(s);
            }
        } catch (JSONException jsonException) {
            flag = false;
        }
        return flag;
    }
}
