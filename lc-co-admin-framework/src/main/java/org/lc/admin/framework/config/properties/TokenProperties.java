package org.lc.admin.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: token令牌属性
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 21:48
 */
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    /**
     * token令牌自定义标识
     */
    private static String header;

    /**
     * token令牌秘钥
     */
    private static String secret;

    /**
     * token令牌有效期（默认30分钟）
     */
    private static Long expiresTime;

    /**
     * token刷新时间
     */
    private static Long refreshTime;

    public static String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        TokenProperties.header = header;
    }

    public static String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        TokenProperties.secret = secret;
    }

    public static Long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Long expiresTime) {
        TokenProperties.expiresTime = expiresTime;
    }

    public static Long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Long refreshTime) {
        TokenProperties.refreshTime = refreshTime;
    }
}
