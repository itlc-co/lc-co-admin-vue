package org.lc.admin.common.base.pool;

/**
 * Description: 消息常量
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:52
 */
public class Message {
    /**
     * not found
     */
    public static final String NOT_FOUND = "url为{}的请求找不到";
    /**
     * method not allowed
     */
    public static final String METHOD_NOT_ALLOWED = "url为{}的{}方法的请求不被允许";
    /**
     * access denied
     */
    public static final String ACCESS_DENIED = "url为{}的请求访问被拒绝";
    /**
     * 演示模式消息
     */
    public static final String DEMO_MODE_MESSAGE = "演示模式下,无法访问请求地址:[{}]";
    /**
     * 注销成功
     */
    public static final String LOGOUT_SUCCESS = "logout success";
    public static final String QUARTZ_JOB_SUCCESS = "quartz job status is success";
}
