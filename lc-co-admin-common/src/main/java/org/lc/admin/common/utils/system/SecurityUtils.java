package org.lc.admin.common.utils.system;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.lc.admin.common.base.pool.StrConstantsPool;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.pool.SecurityConstantsPool;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;

/**
 * Description: 安全服务工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 16:38
 */
public class SecurityUtils {


    /**
     * Description: 获取Authentication
     *
     * @return {@link Authentication } Authentication认证实例
     * @date 2022-09-06 11:35
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * Description: 获取用户详细
     *
     * @return {@link UserDetail } 用户详情实例
     * @date 2022-09-07 21:37
     */
    public static UserDetail getUserDetail() {
        // security获取用户认证凭证
        return ObjectUtil.isNotNull(getAuthentication()) && getAuthentication().getPrincipal() instanceof UserDetail ? (UserDetail) getAuthentication().getPrincipal() : null;
    }

    /**
     * Description: 获取用户id
     *
     * @return {@link Long } 用户id
     * @date 2022-09-08 12:20
     */
    public static Long getUserId() {
        UserDetail userDetail = getUserDetail();
        Long userId = ObjectUtil.isNotNull(userDetail) ? userDetail.getUserId() : null;
        if (ObjectUtil.isNotNull(userId) && userId < 0) {
            // 用户id为负数返回null
            userId = null;
        }
        return userId;
    }

    /**
     * Description: 获取用户名
     *
     * @return {@link String } 用户名
     * @date 2022-09-13 16:41
     */
    public static String getUserName() {
        return ObjectUtil.isNotNull(getUserDetail()) ? getUserDetail().getUsername() : StrConstantsPool.EMPTY_STR;
    }


    /**
     * Description: 产生随机盐
     *
     * @return {@link String } 随机盐字符串
     * @date 2022-09-13 17:30
     */
    public static String prodSalt() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SecurityConstantsPool.SALT_STR_LEN; i++) {
            sb.append(StrConstantsPool.LETTER_STR.charAt(RandomUtil.randomInt(StrConstantsPool.LETTER_STR.length())));
        }
        sb.append(StrConstantsPool.SYMBOL_STR.charAt(RandomUtil.randomInt(StrConstantsPool.SYMBOL_STR.length())));
        sb.append(StrConstantsPool.NUMBER_STR.charAt(RandomUtil.randomInt(StrConstantsPool.NUMBER_STR.length())));
        return sb.toString();
    }


    /**
     * Description: 对明文密码加密
     *
     * @param password 密码
     * @param salt     随机盐
     * @return {@link String } 加密密码字符串
     * @date 2022-09-13 17:32
     */
    public static String encryptPassword(String password, String salt) {
        return SecureUtil.md5().setSalt(salt.getBytes(StandardCharsets.UTF_8)).setDigestCount(1024).digestHex(StrUtil.toString(password), StandardCharsets.UTF_8);
    }

    /**
     * Description: 获取用户密码
     *
     * @return {@link String }
     * @date 2022-09-21 22:19
     */
    public static String getPassword() {
        return getUserDetail().getPassword();
    }

    /**
     * Description: 匹配密码
     *
     * @param oldPassword 旧密码
     * @param salt        随机盐
     * @param password    密码
     * @return boolean true 匹配成功 false 匹配失败
     * @date 2022-09-24 10:35
     */
    public static boolean matchesPassword(String oldPassword, String salt, String password) {
        return StrUtil.equalsIgnoreCase(encryptPassword(oldPassword, salt), password);
    }

}
