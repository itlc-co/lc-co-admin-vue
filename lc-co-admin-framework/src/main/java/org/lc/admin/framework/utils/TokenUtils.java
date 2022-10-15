package org.lc.admin.framework.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.pool.TokenConstantsPool;
import org.lc.admin.framework.config.properties.TokenProperties;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: json web token 工具类
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-02  21:08
 */
public class TokenUtils {

    /**
     * Description: 创建令牌token
     *
     * @param user 用户详情实体
     * @return {@link String } token字符串
     * @date 2022-09-05 14:04
     */
    public static String createToken(UserDetail user) {
        return createToken(prodPlayLoad(user));
    }

    /**
     * Description: 创建令牌token
     *
     * @param playLoad playLoad用户信息
     * @return {@link String } token字符串
     * @date 2022-09-05 14:05
     */
    private static String createToken(Map<String, Object> playLoad) {
        // token令牌密钥 如果配置了则使用配置的密钥
        byte[] key = StrUtil.blankToDefault(TokenProperties.getSecret(), TokenConstantsPool.TOKEN_KEY).getBytes(StandardCharsets.UTF_8);
        return JWTUtil.createToken(TokenConstantsPool.TOKEN_HEADER, playLoad, key);
    }

    /**
     * Description: 生成playLoad信息
     *
     * @param user 用户详情实体
     * @return {@link Map }<{@link String }, {@link Object }> playLoad信息Map
     * @date 2022-09-05 14:05
     */
    private static Map<String, Object> prodPlayLoad(UserDetail user) {
        Map<String, Object> playLoad = new HashMap<>(10);
        // 配置基本用户信息(用户名，用户id，用户uuid等等)
        playLoad.put("user_id", user.getUserId());
        playLoad.put("user_name", user.getUsername());
        playLoad.put("user_uuid", user.getUuid());
        return playLoad;
    }

    /**
     * Description: 解析token获取playLoad信息
     *
     * @param token token字符串
     * @return {@link Map }<{@link String }, {@link Object }> playLoad信息Map
     * @date 2022-09-07 10:38
     */
    public static Map<String, Object> parseToken(String token) {
        return JWTUtil.parseToken(token).getPayloads().getRaw();
    }

    /**
     * Description: 通过token字符串获取认证用户uuid
     *
     * @param token token字符串
     * @return {@link String } 认证用户uuid
     * @date 2022-09-07 10:40
     */
    public static String getUuidByToken(String token) {
        return StrUtil.toString(JWTUtil.parseToken(token).getPayload("user_uuid"));
    }

    /**
     * Description: 验证令牌token有效性
     *
     * @param token 令牌token
     * @return boolean
     * @date 2022-09-07 11:15
     */
    public static boolean validateToken(String token) {
        // token令牌密钥 如果配置了则使用配置的密钥
        byte[] key = StrUtil.blankToDefault(TokenProperties.getSecret(), TokenConstantsPool.TOKEN_KEY).getBytes(StandardCharsets.UTF_8);
        if (!StrUtil.startWith(token, TokenConstantsPool.TOKEN_PRE)) {
            // 非Bearer 开头为非法token
            return false;
        }
        return JWTUtil.verify(token.replace(TokenConstantsPool.TOKEN_PRE, ""), key);
    }

    /**
     * Description: 请求中获取令牌token
     *
     * @param request 请求
     * @return {@link String } token字符串
     * @date 2022-09-06 11:49
     */
    public static String getToken(HttpServletRequest request) {
        // 如何配置了请求头名称则使用配置了的
        String header = StrUtil.blankToDefault(TokenProperties.getHeader(), TokenConstantsPool.TOKEN_HEADER_FLAG);
        // 请求头获取token令牌
        String token = request.getHeader(header);
        if (StrUtil.isNotBlank(token)) {
            // 如果验证通过token令牌前缀替换
            token = validateToken(token) ? StrUtil.replace(token, TokenConstantsPool.TOKEN_PRE, "") : StrUtil.EMPTY;
        }
        return token;
    }

}
