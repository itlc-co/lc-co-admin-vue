package org.lc.admin.common.base.pool;


import cn.hutool.core.util.StrUtil;

/**
 * Description: str常量池
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 17:24
 */
public class StrConstantsPool {

    /**
     * 26个大小字母字符串
     */
    public static final String LETTER_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 数学符号字符串
     */
    public static final String SYMBOL_STR = "!@#$%^&*()~{[}]|,./?+-_=";

    /**
     * 0-9数字字符串
     */
    public static final String NUMBER_STR = "0123456789";

    /**
     * 点分隔符
     */
    public static final String POINT_SEPARATOR = ".";

    /**
     * 下划线分隔符
     */
    public static final String UNDERLINE_SEPARATOR = "_";

    /**
     * 左右括号
     */
    public static final String PARENTHESES_AROUND = "()";

    /**
     * @ 分隔符
     */
    public static final String AT_SEPARATOR = "@";

    /**
     * 逗号分隔符
     */
    public static final String COMMA_SEPARATOR = ",";
    /**
     * 空字符串
     */
    public static final String EMPTY_STR = StrUtil.EMPTY;
    public static final String PARENTHESES_LEFT = "(";
    public static final String PARENTHESES_RIGHT = ")";
}
