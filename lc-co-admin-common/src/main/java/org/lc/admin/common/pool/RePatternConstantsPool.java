package org.lc.admin.common.pool;

/**
 * Description: 正则匹配模式常量池
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-04 17:49
 */
public class RePatternConstantsPool {

    /**
     * 手机正则模式
     */
    public static final String PHONE_PATTERN = "^(?:(?:\\+|00)86)?1[3-9]\\d{9}$";

    /**
     * 手机正则模式
     */
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_\\.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]{2,4}$";

    /**
     * 字典类型正则
     */
    public static final String DICT_TYPE_PATTERN = "^[a-z][a-z0-9_]*$";


}
