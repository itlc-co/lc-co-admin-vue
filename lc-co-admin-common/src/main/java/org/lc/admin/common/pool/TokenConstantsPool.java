package org.lc.admin.common.pool;

import cn.hutool.jwt.JWTHeader;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description: token常量池
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:36
 */
public class TokenConstantsPool {

    /**
     * token头信息
     */
    public static final Map<String, Object> TOKEN_HEADER = new HashMap<>(2);
    /**
     * token过期时间 30分钟
     */
    public static final Long TOKEN_EXPIRES_TIME = 30L;
    /**
     * 默认token过期时间 30分钟
     */
    public static final Long DEFAULT_AUTH_TOKEN_TTL = TOKEN_EXPIRES_TIME;
    /**
     * 默认token过期时间单位
     */
    public static final TimeUnit DEFAULT_AUTH_TOKEN_TIMEUNIT = TimeUnit.MINUTES;
    /**
     * 令牌毫秒数
     */
    public static final long TOKEN_MILLIS_SECOND = 1000;
    /**
     * 令牌毫秒分钟数
     */
    public static final long TOKEN_MILLIS_MINUTE = 60 * TOKEN_MILLIS_SECOND;
    /**
     * token头信息标记
     */
    public static final String TOKEN_HEADER_FLAG = "Authorization";
    /**
     * token前缀
     */
    public static final String TOKEN_PRE = "Bearer ";
    /**
     * token标识
     */
    public static final String TOKEN = "token";
    /**
     * token 密钥
     */
    public static final String TOKEN_KEY = "^&$s1`st";
    /**
     * token 密钥 bytes[]
     */
    public static final byte[] TOKEN_KEY_BYTE = TOKEN_KEY.getBytes(StandardCharsets.UTF_8);
    /**
     * token 过期时间与当前时间相差20分钟则刷新
     */
    public static final long TOKEN_REFRESH_TIME = 20L;

    static {
        //初始化token头信息
        TOKEN_HEADER.put(JWTHeader.TYPE, "JWT");
        TOKEN_HEADER.put(JWTHeader.ALGORITHM, "HS256");
    }

}
