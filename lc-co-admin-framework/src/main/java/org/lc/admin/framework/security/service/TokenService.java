package org.lc.admin.framework.security.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.pool.TokenConstantsPool;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.framework.config.properties.TokenProperties;
import org.lc.admin.framework.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Description: 令牌token service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-05 15:18
 */
@Service()
public class TokenService {

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 创建令牌token字符串
     *
     * @param user 用户
     * @return {@link String } token字符串
     * @date 2022-09-05 15:18
     */
    public String createToken(UserDetail user) {
        // 刷新用户时效性
        refreshAuthUserTimeliness(user);
        // 创建token字符串
        return TokenUtils.createToken(user);
    }

    /**
     * Description: 刷新认证用户时效性
     *
     * @param user 用户实体
     * @date 2022-09-07 16:57
     */
    private void refreshAuthUserTimeliness(UserDetail user) {
        // token有效时间如果配置了则使用
        Long expiresTime = ObjectUtil.defaultIfNull(TokenProperties.getExpiresTime(), TokenConstantsPool.TOKEN_EXPIRES_TIME);
        // 设置登录时间与登录过期时间
        user.setLoginTime(System.currentTimeMillis());
        user.setExpireTime(user.getLoginTime() + expiresTime * TokenConstantsPool.TOKEN_MILLIS_MINUTE);
        // 根据uuid将userDetail保存到redis缓存中
        saveUserDetailRedis(user);
    }

    /**
     * Description: 保存用户详情redis缓存数据
     *
     * @param user 用户详情
     * @date 2022-10-05 19:22
     */
    private void saveUserDetailRedis(UserDetail user) {
        this.redisCacheService.saveUserDetail(user);
    }

    /**
     * Description: 获取用户详情数据
     *
     * @param request 请求
     * @return {@link UserDetail } 用户详情实例
     * @date 2022-09-06 11:43
     */
    public UserDetail getUserDetail(HttpServletRequest request) {
        UserDetail userDetail = null;
        // 获取请求头携带的令牌token
        String token = TokenUtils.getToken(request);
        if (StrUtil.isNotBlank(token)) {
            // 令牌token不为空白字符串则解析token获取用户详情信息
            userDetail = this.getUserDetail(token);
        }
        return userDetail;
    }

    /**
     * Description: 获取用户详情
     *
     * @param token token字符串
     * @return {@link UserDetail } 用户详情
     * @date 2022-10-05 19:22
     */
    public UserDetail getUserDetail(String token) {
        String uuid = StrUtil.toString(parseToken(token).getOrDefault("user_uuid", StrUtil.EMPTY));
        return StrUtil.isNotBlank(uuid) ? this.redisCacheService.getUserDetail(uuid) : null;
    }

    /**
     * Description: 解析token令牌
     *
     * @param token token令牌
     * @return {@link Map }<{@link String }, {@link Object }> playPlod信息
     * @date 2022-10-05 19:23
     */
    public Map<String, Object> parseToken(String token) {
        return TokenUtils.parseToken(token);
    }

    /**
     * Description: 验证身份验证用户时效性并自动刷新时效性
     * （过期时间与当前时间相差超过20分钟时自动刷新）
     *
     * @param user 用户
     * @date 2022-09-07 17:05
     */
    public void verifyAuthUserTimeliness(UserDetail user) {
        Long tokenRefreshTime = ObjectUtil.defaultIfNull(TokenProperties.getRefreshTime(), TokenConstantsPool.TOKEN_REFRESH_TIME) * TokenConstantsPool.TOKEN_MILLIS_MINUTE;
        Long expireTime = user.getExpireTime();
        Long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= tokenRefreshTime) {
            refreshAuthUserTimeliness(user);
        }
    }

    /**
     * Description: 设置用户详细数据
     *
     * @param userDetail 用户详细数据
     * @date 2022-09-20 16:33
     */
    public void setUserDetail(UserDetail userDetail) {
        if (ObjectUtil.isNotNull(userDetail) && StrUtil.isNotBlank(userDetail.getUuid())) {
            refreshAuthUserTimeliness(userDetail);
        }
    }

}
