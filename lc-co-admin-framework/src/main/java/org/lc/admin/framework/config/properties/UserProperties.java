package org.lc.admin.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Description: 用户属性
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 22:15
 */
@Configuration
@ConfigurationProperties(prefix = "user")
@Data
public class UserProperties {

    /**
     * 锁定时间
     */
    private static Long lockTime;

    /**
     * 密码属性
     */
    @Resource
    private PasswordProperties passwordProperties;

    /**
     * Description: 锁定时间
     *
     * @return {@link Long } 锁定时间
     * @date 2022-10-07 22:27
     */
    public static Long getLockTime() {
        return lockTime;
    }

    public void setLockTime(Long lockTime) {
        UserProperties.lockTime = lockTime;
    }

    /**
     * Description: 获取最大密码错误次数
     *
     * @return {@link Long } 最大密码错误次数
     * @date 2022-10-07 22:27
     */
    public static Long getMaxRetryCount() {
        return PasswordProperties.getMaxRetryCount();
    }

    public PasswordProperties getPasswordProperties() {
        return passwordProperties;
    }

    public void setPasswordProperties(PasswordProperties passwordProperties) {
        this.passwordProperties = passwordProperties;
    }

    @ConfigurationProperties(prefix = "user.password")
    @Configuration
    @Data
    static class PasswordProperties {
        /**
         * 最大密码错误次数
         */
        private static Long maxRetryCount;

        public static Long getMaxRetryCount() {
            return maxRetryCount;
        }

        public void setMaxRetryCount(Long maxRetryCount) {
            PasswordProperties.maxRetryCount = maxRetryCount;
        }
    }

}
