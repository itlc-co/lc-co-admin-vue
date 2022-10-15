package org.lc.admin.common.pool;

import java.util.concurrent.TimeUnit;

/**
 * Description: auth常量池
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  22:03
 */
public class AuthConstantsPool {

    /**
     * 默认校验验证码消息
     */
    public static final String DEFAULT_VALIDATE_CAPTCHA_MSG = "success";
    /**
     * 默认校验验证码状态码
     */
    public static final Integer DEFAULT_VALIDATE_CAPTCHA_CODE = 2000;
    /**
     * 验证码图片宽度
     */
    public static final int CAPTCHA_WIDTH = 80;
    /**
     * 验证码图片高度
     */
    public static final int CAPTCHA_HEIGHT = 36;
    /**
     * 验证码图片code数量
     */
    public static final int CAPTCHA_CODE_COUNT = 5;
    /**
     * 验证码图片干扰线数量
     */
    public static final int CAPTCHA_LINE_COUNT = 150;
    /**
     * 模块名称
     */
    public static final String MODULE_NAME = "auth";
    /**
     * 默认身份验证验证码ttl
     */
    public static final Long DEFAULT_AUTH_CAPTCHA_TTL = 5L;
    /**
     * 默认身份验证pwd重试次数ttl
     */
    public static final Long DEFAULT_AUTH_PWD_RETRY_CNT_TTL = 10L;
    /**
     * 默认timeunit时间单位 分钟
     */
    public static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MINUTES;
    /**
     * 默认身份验证最大密码重试次数
     */
    public static final Long DEFAULT_AUTH_MAX_PWD_RETRY_CNT = 5L;
}
