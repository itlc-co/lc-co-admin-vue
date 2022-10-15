package org.lc.admin.framework.security.handler;

import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.exec.AuthException;
import org.lc.admin.framework.security.service.PasswordService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Description: 身份验证提供者实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-05 13:55
 */
public class AuthenticationProvider extends DaoAuthenticationProvider {

    private final PasswordService passwordService;

    public AuthenticationProvider(UserDetailsService userDetailsService, PasswordService passwordService) {
        setUserDetailsService(userDetailsService);
        this.passwordService = passwordService;
    }

    /**
     * Description: 进行身份验证
     *
     * @param authentication 身份验证
     * @return {@link Authentication }
     * @throws AuthenticationException 身份验证异常
     * @date 2022-09-05 13:56
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 可以在此处覆写整个登录认证逻辑
        return super.authenticate(authentication);
    }

    /**
     * Description: 身份验证检查
     *
     * @param userDetails    用户详细信息
     * @param authentication 身份验证
     * @throws AuthException 身份验证异常
     * @date 2022-09-05 13:56
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthException {
        // 可以在此处覆写密码验证逻辑
        if (authentication.getCredentials() == null) {
            // 不存在凭证则抛出认证异常(密码错误)
            throw new AuthException(StatusMsg.AUTH_PASSWORD_ERROR);
        } else {
            // 用户详情实体
            UserDetail user = (UserDetail) userDetails;
            AuthUser authUser = user.getUser();
            if (ObjectUtil.isEmpty(authUser)) {
                // 不存在凭证则抛出认证异常(密码错误)
                throw new AuthException(StatusMsg.USER_NOT_FOUND);
            }
            // 存在认证用户校验密码
            this.passwordService.validatePassword(authUser);
        }
    }
}
