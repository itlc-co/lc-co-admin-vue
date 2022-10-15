package org.lc.admin.system.service;

import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.system.entities.request.RegisterRequest;

/**
 * Description: 认证用户service服务接口
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  21:22
 */
public interface AuthUserService {

    /**
     * Description: 根据用户名查询认证用户数据
     *
     * @param userName 用户名
     * @return {@link AuthUser } 认证用户数据
     * @date 2022-09-23 22:00
     */
    AuthUser selectAuthUserByUsername(String userName);

    /**
     * Description: 根据用户数据记录用户登录信息
     *
     * @param systemUser 用户数据
     * @date 2022-09-23 22:00
     */
    void recordAuthUserLoginInfo(SystemUser systemUser);

    /**
     * Description: 根据用户名称查询用户数据
     *
     * @param userName 用户名
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 22:00
     */
    SystemUser selectUserByUserName(String userName);

    /**
     * Description: 根据用户名称查询用户数量
     *
     * @param userName 用户名称
     * @return {@link Integer } 记录数
     * @date 2022-09-23 22:31
     */
    Integer selectCntUserByUserName(String userName);

    /**
     * Description: 注册
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 22:00
     */
    Integer register(RegisterRequest requestParam);
}
