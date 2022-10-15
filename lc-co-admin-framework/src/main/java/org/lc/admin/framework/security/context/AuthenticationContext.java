package org.lc.admin.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * Description: 身份验证信息线程上下文
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-29  16:50
 */
public class AuthenticationContext {

    /**
     * 身份验证上下文
     */
    private static final ThreadLocal<Authentication> AUTHENTICATION_CONTEXT = new ThreadLocal<>();


    /**
     * Description: 得到认证
     *
     * @return {@link Authentication }  Authentication
     * @date 2022-09-24 10:58
     */
    public static Authentication getAuthentication() {
        return AUTHENTICATION_CONTEXT.get();
    }

    /**
     * Description: 设置身份验证
     *
     * @param authentication 身份验证
     * @date 2022-09-24 10:58
     */
    public static void setAuthentication(Authentication authentication) {
        AUTHENTICATION_CONTEXT.set(authentication);
    }

    /**
     * Description: 清理身份验证
     *
     * @date 2022-09-24 10:58
     */
    public static void clearAuthentication() {
        AUTHENTICATION_CONTEXT.remove();
    }


}
