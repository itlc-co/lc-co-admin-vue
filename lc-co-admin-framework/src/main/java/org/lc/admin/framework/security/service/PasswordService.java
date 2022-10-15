package org.lc.admin.framework.security.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.exec.AuthException;
import org.lc.admin.common.pool.AuthConstantsPool;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.framework.config.properties.UserProperties;
import org.lc.admin.framework.security.context.AuthenticationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description: 密码service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-14 11:22
 */
@Service
public class PasswordService {


    @Resource
    private RedisCacheService redisCacheService;


    /**
     * Description: 校验认证用户密码
     *
     * @param user 认证用户实例
     * @throws AuthException 认证异常（密码错误）
     * @date 2022-09-01 12:57
     */
    public void validatePassword(AuthUser user) {
        // auth认证上下文中获取auth实例
        Authentication authentication = AuthenticationContext.getAuthentication();
        // 获取登录用户输入的登录用户名密码
        String password = authentication.getCredentials().toString();
        // 认证用户真实密码以及加密盐
        String realPassword = user.getPassword();
        String salt = user.getSalt();
        String userName = user.getUserName();
        // 密码错误次数
        Long retryCount = ObjectUtil.defaultIfNull(getRetryCount(userName), 0L);
        // 最大密码错误次数
        Long maxRetryCount = ObjectUtil.defaultIfNull(UserProperties.getMaxRetryCount(), AuthConstantsPool.DEFAULT_AUTH_MAX_PWD_RETRY_CNT);
        if (retryCount >= maxRetryCount) {
            throw new AuthException(StatusMsg.AUTH_PASSWORD_EXCEEDED_MAX_RETRY_COUNT);
        }
        // 密码匹配验证
        if (!checkPassword(password, realPassword, salt)) {
            setAndIncrementRetryCount(userName);
            throw new AuthException(StatusMsg.AUTH_PASSWORD_ERROR);
        }
        clearRetryCount(userName);
    }

    /**
     * Description: 根据用户名称清除密码重试次数缓存
     *
     * @param userName 用户名称
     * @return boolean true 存在清除成功 false 不存在清除失败
     * @date 2022-10-02 15:28
     */
    private boolean clearRetryCount(String userName) {
        return this.redisCacheService.clearPasswordRetryCount(userName);
    }

    /**
     * Description: 根据用户名称设置并且增加密码重试次数
     *
     * @param userName 用户名称
     * @return {@link Long } 增加后的次数
     * @date 2022-10-02 15:28
     */
    private Long setAndIncrementRetryCount(String userName) {
        // 如果配置了ttl即lockTime锁定时间则使用配置项
        Long ttl = ObjectUtil.defaultIfNull(UserProperties.getLockTime(), AuthConstantsPool.DEFAULT_AUTH_PWD_RETRY_CNT_TTL);
        return this.redisCacheService.setAndIncrementPasswordRetryCount(userName, ttl, AuthConstantsPool.DEFAULT_TIMEUNIT);
    }

    /**
     * Description: 根据用户名称查询密码错误次数缓存
     *
     * @param userName 用户名
     * @return {@link Long } 密码错误次数
     * @date 2022-10-02 15:29
     */
    private Long getRetryCount(String userName) {
        return this.redisCacheService.getPasswordRetryCount(userName);
    }

    /**
     * Description: 匹配验证密码
     *
     * @param password     用户密码
     * @param realPassword 真实密码
     * @param salt         加密盐
     * @return boolean true 匹配验证通过 false 匹配验证不通过
     * @date 2022-08-29 17:04
     */
    public boolean checkPassword(String password, String realPassword, String salt) {
        return matchPassword(password, realPassword, salt);
    }

    /**
     * Description: 匹配密码
     *
     * @param password     密码
     * @param realPassword 真正密码
     * @param salt         盐
     * @return boolean true 匹配成功 false 匹配失败
     * @date 2022-09-14 11:03
     */
    public boolean matchPassword(String password, String realPassword, String salt) {
        return StrUtil.equals(SecurityUtils.encryptPassword(password, salt), realPassword);
    }

}
