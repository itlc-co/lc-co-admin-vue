package org.lc.admin.common.entities.model;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.lc.admin.common.entities.entity.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * Description: 用户详情
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-31  22:15
 */
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class UserDetail extends User {

    private static final long serialVersionUID = 630201200405107887L;


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户唯一标识 uuid
     */
    private String uuid;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * token过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户信息实例
     */
    private AuthUser user;

    public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getSalt() {
        return ObjectUtil.isNotNull(user) ? user.getSalt() : null;
    }

    public String getDeptName() {
        return ObjectUtil.isNotNull(user) && ObjectUtil.isNotNull(user.getDept()) ? user.getDept().getDeptName() : null;
    }

    @Override
    public String getPassword() {
        return ObjectUtil.isNotNull(user) ? user.getPassword() : null;

    }

    @Override
    public String getUsername() {
        return ObjectUtil.isNotNull(user) ? user.getUserName() : null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
