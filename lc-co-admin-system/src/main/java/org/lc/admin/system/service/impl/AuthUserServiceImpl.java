package org.lc.admin.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.pool.StatusCode;
import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.common.exec.ServiceException;
import org.lc.admin.common.pool.UserConstantsPool;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.request.RegisterRequest;
import org.lc.admin.system.mapper.AuthUserMapper;
import org.lc.admin.system.service.AuthUserService;
import org.lc.admin.system.service.SystemUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description: 认证用户service服务实现
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  21:22
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Resource
    private AuthUserMapper authUserMapper;

    @Resource
    private SystemUserService userService;


    /**
     * Description: 根据用户名查询认证用户数据
     *
     * @param userName 用户名
     * @return {@link AuthUser } 认证用户数据
     * @date 2022-09-01 21:26
     */
    @Override
    public AuthUser selectAuthUserByUsername(String userName) {

        return authUserMapper.selectAuthUserByUsername(userName);
    }

    /**
     * Description: 根据用户数据记录用户登录信息
     *
     * @param systemUser 用户数据
     * @date 2022-09-05 22:40
     */
    @Override
    public void recordAuthUserLoginInfo(SystemUser systemUser) {
        try {
            // 根据用户数据记录登录信息
            userService.recordLoginInfo(systemUser);
        } catch (RuntimeException runtimeException) {
            throw new ServiceException(StatusCode.SERVICE_ERROR_CODE, runtimeException.getMessage()).setDetailMessage(runtimeException);
        }
    }

    /**
     * Description: 根据用户名称查询用户数据
     *
     * @param userName 用户名
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 22:34
     */
    @Override
    public SystemUser selectUserByUserName(String userName) {
        return this.userService.selectUserByUserName(userName);
    }

    /**
     * Description: 根据用户名称查询用户数量
     *
     * @param userName 用户名称
     * @return {@link Integer } 记录数
     * @date 2022-09-23 22:34
     */
    @Override
    public Integer selectCntUserByUserName(String userName) {
        return this.userService.selectCntUserByUserName(userName);
    }

    /**
     * Description: 注册
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 22:34
     */
    @Override
    public Integer register(RegisterRequest requestParam) {

        // 获取用户名称密码参数
        String username = requestParam.getUsername();
        String password = requestParam.getPassword();
        String salt = SecurityUtils.prodSalt();
        password = SecurityUtils.encryptPassword(password, salt);

        // 构建系统用户实体
        SystemUser user = SystemUser.builder()
                .userName(username).salt(salt).password(password)
                .nickName(username).createBy(username).updateBy(username)
                .build();
        return this.userService.insertUser(user);
    }


}
