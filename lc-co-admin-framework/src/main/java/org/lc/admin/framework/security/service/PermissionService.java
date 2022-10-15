package org.lc.admin.framework.security.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.pool.PermissionConstantsPool;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.service.SystemMenuService;
import org.lc.admin.system.service.SystemRoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * Description: 数据权限service服务实现
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  12:48
 */
@Service
public class PermissionService {

    @Resource
    private SystemMenuService menuService;

    @Resource
    private SystemRoleService roleService;


    /**
     * Description: 通过用户实体获取菜单数据权限
     *
     * @param user 用户信息实体
     * @return java.util.Set<java.lang.String> 数据权限信息集合
     * @date 2022-09-01 12:49
     */
    public Set<String> getMenuPermissionByUser(SystemUser user) {
        // 默认权限为admin *:*:*
        Set<String> permissions = CollUtil.newHashSet("*:*:*");

        if (!user.isAdmin()) {
            // 如果用户不为admin，则调用菜单服务获取用户菜单权限
            permissions = CollUtil.newHashSet(this.menuService.selectMenuPermissionsByUserId(user.getId()));
        }

        return permissions;
    }

    /**
     * Description: 通过用户实体获取角色数据权限
     *
     * @param user 用户信息实体
     * @return java.util.Set<java.lang.String> 数据权限信息集合
     * @date 2022-09-01 12:49
     */
    public Set<String> getRolePermissionByUser(SystemUser user) {
        // 默认角色权限为admin
        Set<String> roles = CollUtil.newHashSet(PermissionConstantsPool.SUPER_ADMIN_ROLE);

        if (!user.isAdmin()) {
            // 如果用户不为admin，则调用角色服务获取用户角色权限
            roles = CollUtil.newHashSet(this.roleService.selectRolePermissionsByUserId(user.getId()));
        }

        return roles;
    }


    /**
     * Description: 判断是否有指定的权限
     *
     * @param permission 权限
     * @return boolean true 包含  false 不包含
     * @date 2022-10-03 11:09
     */
    public boolean hasPermission(String permission) {
        boolean flag = false;
        if (StrUtil.isNotBlank(permission)) {
            // 权限不为空白字符串
            UserDetail userDetail = SecurityUtils.getUserDetail();
            if (ObjectUtil.isNotNull(userDetail) && CollUtil.isNotEmpty(userDetail.getPermissions())) {
                // 用户详情以及权限集合不为空则判断是否含有权限
                flag = hasPermission(userDetail.getPermissions(), permission);
            }
        }
        return flag;
    }

    /**
     * Description: 判断是否不具备某权限，与 hasPermission逻辑相反
     *
     * @param permission 权限字符串
     * @return boolean true 不具备 false 具备
     * @date 2022-10-03 15:19
     */
    public boolean lacksPermission(String permission) {
        return !hasPermission(permission);
    }

    /**
     * Description:  判断权限集合中是否有包含有指定的权限 （或者有所有权限）
     *
     * @param permissions 权限集合
     * @param permission  权限
     * @return boolean true 权限集合中包含所有权限或者指定权限 false 不包含
     * @date 2022-10-03 11:07
     */
    private boolean hasPermission(Set<String> permissions, String permission) {
        // 权限集合中是否包含所有权限或者指定的权限字符串
        return CollUtil.contains(permissions, PermissionConstantsPool.ALL_PERMISSION) || CollUtil.contains(permissions, StrUtil.trim(permission));
    }


    /**
     * Description: 判断是否不具备某角色，与 hasRole逻辑相反
     *
     * @param role 角色
     * @return boolean true 不具备 false 具备
     * @date 2022-10-03 15:41
     */
    public boolean lacksRole(String role) {
        return !hasRole(role);
    }

    /**
     * Description: 判断是否具有指定的角色
     *
     * @param role 角色字符串
     * @return boolean true 具有 false 不具有
     * @date 2022-10-03 15:21
     */
    public boolean hasRole(String role) {
        boolean flag = false;
        if (StrUtil.isNotBlank(role)) {
            // 角色字符串不为空白字符串
            UserDetail userDetail = SecurityUtils.getUserDetail();
            if (ObjectUtil.isNotNull(userDetail) && CollUtil.isNotEmpty(userDetail.getAuthorities())) {
                // 用户详情以及角色集合不为空则判断是否含有角色
                flag = hasRole(userDetail.getAuthorities(), role);
            }
        }
        return flag;
    }

    /**
     * Description: 判断authorities角色集合中是否有包含有指定的角色（或者有admin角色）
     *
     * @param authorities authorities角色集合
     * @param role        角色字符串
     * @return boolean true 包含 false 不包含
     * @date 2022-10-03 15:38
     */
    private boolean hasRole(Collection<GrantedAuthority> authorities, String role) {
        // 角色集合中是否包含超级管理员角色或者指定的角色字符串
        return CollUtil.contains(authorities, (authority) -> StrUtil.equalsAnyIgnoreCase(authority.getAuthority(), PermissionConstantsPool.SUPER_ADMIN_ROLE, role));
    }


    /**
     * Description: 判断是否有指定（以,分割）的多个权限之一
     *
     * @param permission 一个或者多个权限（以,分割）
     * @return boolean true 含有 false 不含有
     * @date 2022-10-03 15:42
     */
    public boolean hasAnyPermission(String permission) {
        boolean flag = false;
        if (StrUtil.isNotBlank(permission)) {
            // 权限不为空白字符串
            UserDetail userDetail = SecurityUtils.getUserDetail();
            if (ObjectUtil.isNotNull(userDetail) && CollUtil.isNotEmpty(userDetail.getPermissions())) {
                // 用户详情以及权限集合不为空则判断是否含有多个权限之一
                flag = hasAnyPermission(userDetail.getPermissions(), permission.split(PermissionConstantsPool.PERMISSION_SEPARATOR));
            }
        }
        return flag;
    }

    /**
     * Description: 判断权限集合是否含有指定的多个权限之一
     *
     * @param permissions     权限集合
     * @param permissionArray 权限数组
     * @return boolean true 含有 false 不含有
     * @date 2022-10-03 15:50
     */
    private boolean hasAnyPermission(Set<String> permissions, String... permissionArray) {
        // 权限集合是否含有指定的多个权限之一或者具有所有权限
        return CollUtil.contains(CollUtil.distinct(CollUtil.filter(permissions, StrUtil::isNotBlank)), (permission) -> StrUtil.equalsIgnoreCase(permission, PermissionConstantsPool.ALL_PERMISSION) || StrUtil.equalsAnyIgnoreCase(permission, permissionArray));
    }


    /**
     * Description: 判断是否有指定（以,分割）的多个角色之一 或者具有所有权限
     *
     * @param roles （以,分割）的多个角色
     * @return boolean true 含有 false 不含有
     * @date 2022-10-03 15:55
     */
    public boolean hasAnyRole(String roles) {
        boolean flag = false;
        if (StrUtil.isNotBlank(roles)) {
            // 角色字符串不为空白字符串
            UserDetail userDetail = SecurityUtils.getUserDetail();
            if (ObjectUtil.isNotNull(userDetail) && CollUtil.isNotEmpty(userDetail.getAuthorities())) {
                // 用户详情以及角色集合不为空则判断是否含有多个角色之一
                flag = hasAnyRole(userDetail.getAuthorities(), roles.split(PermissionConstantsPool.PERMISSION_SEPARATOR));
            }
        }
        return flag;
    }

    /**
     * Description: 判断authorities角色集合是否含有指定的多个角色之一 或者具有admin角色
     *
     * @param authorities authorities角色集合
     * @param roles       角色数组
     * @return boolean true 具有 false 不具有
     * @date 2022-10-03 16:01
     */
    private boolean hasAnyRole(Collection<GrantedAuthority> authorities, String[] roles) {
        // 角色集合是否含有指定的多个角色之一或者具有admin角色
        return CollUtil.contains(CollUtil.distinct(CollUtil.filter(authorities, ObjectUtil::isNotEmpty), GrantedAuthority::getAuthority, true), (authority) -> StrUtil.equalsIgnoreCase(authority.getAuthority(), PermissionConstantsPool.SUPER_ADMIN_ROLE) || StrUtil.equalsAnyIgnoreCase(authority.getAuthority(), roles));
    }

}
