package org.lc.admin.common.utils.system;

import cn.hutool.core.util.ObjectUtil;

/**
 * Description: 身份验证用户工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-08 14:12
 */
public class AuthUserUtils {

    /**
     * Description: 根据用户id判断是否管理
     *
     * @param userId 用户id
     * @return boolean true 管理admin false 非管理
     * @date 2022-09-08 14:12
     */
    public static boolean isAdmin(Long userId) {
        return ObjectUtil.isNotNull(userId) && userId == 1L;
    }

}
