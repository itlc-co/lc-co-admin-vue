package org.lc.admin.framework.security.service;

import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.entities.enums.UserStatus;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.exec.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 用户详情service服务实现
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-29  12:01
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Resource
    private PasswordService passwordService;

    @Resource
    private AuthService authService;

    @Resource
    private PermissionService permissionService;

    /**
     * Description: 通过用户名加载用户详情实例
     *
     * @param userName 用户名
     * @return org.springframework.security.core.userdetails.UserDetails 用户详情实例
     * @throws UsernameNotFoundException 用户不存在异常
     * @date 2022-09-01 12:55
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        // 根据登录用户名获取用户
        AuthUser user = authService.loadAuthUserByUsername(userName);

        // 是否存在认证用户
        if (ObjectUtil.isNull(user)) {
            log.info("认证用户:{}不存在.", userName);
            throw new AuthException(StatusMsg.USER_NOT_FOUND);
        }

        // 是否被删除
        if (ObjectUtil.equal(UserStatus.DELETED.getCode(), user.getDelFlag())) {
            log.info("认证用户:{}已被删除.", userName);
            throw new AuthException(StatusMsg.USER_DELETED);
        }

        // 是否被停用
        if (ObjectUtil.equal(UserStatus.DISABLE.getCode(), user.getStatus())) {
            log.info("认证用户:{}已被停用.", userName);
            throw new AuthException(StatusMsg.USER_DISABLE);
        }

        // 创建用户详情实例
        return createUserDetail(user);
    }


    /**
     * Description:  创建用户详情实例
     *
     * @param user 系统认证用户
     * @return org.springframework.security.core.userdetails.UserDetails 用户详情实例
     * @date 2022-08-29 16:33
     */
    private UserDetails createUserDetail(AuthUser user) {
        // 获取用户菜单权限
        Set<String> permissions = this.permissionService.getMenuPermissionByUser(user);
        // 获取用户角色权限
        Set<String> roles = this.permissionService.getRolePermissionByUser(user);
        // 封装用户详情实体
        return new UserDetail(user.getUserName(), user.getPassword(), grantedAuthorities(roles))
                .setUser(user)
                .setDeptId(user.getDeptId())
                .setUserId(user.getId())
                .setPermissions(permissions);
    }


    /**
     * Description: 授予角色集合
     *
     * @param roles 角色集合
     * @return {@link Collection }<{@link ? } {@link extends } {@link GrantedAuthority }> GrantedAuthority角色集合
     * @date 2022-09-05 13:56
     */
    private Collection<? extends GrantedAuthority> grantedAuthorities(Set<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }


}
