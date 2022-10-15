package org.lc.admin.system.service;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.entity.SystemUserRole;

import java.util.List;

/**
 * Description: 系统用户角色关联service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 19:25
 */
public interface SystemUserRoleService {
    /**
     * Description: 添加用户角色列表关联数据
     *
     * @param userRoles 用户角色关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-13 19:26
     */
    Integer batchUserRole(List<SystemUserRole> userRoles);

    /**
     * Description: 根据用户id删除用户角色关联数据
     *
     * @param userId 用户id
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:16
     */
    Integer deleteUserRolesByUserId(@Param("userId") Long userId);

    /**
     * Description: 根据用户id列表删除用户角色关联数据
     *
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:53
     */
    Integer deleteUserRolesByUserIds(List<Long> userIds);

    /**
     * Description: 根据角色id与用户id删除角色用户关联数据
     *
     * @param userRole 角色用户关联数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:42
     */
    Integer deleteUserRole(SystemUserRole userRole);

    /**
     * Description: 根据角色id与用户id列表删除角色用户关联数据
     *
     * @param roleId  角色id
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:42
     */
    Integer deleteUserRoles(Long roleId, List<Long> userIds);

    /**
     * Description: 根据角色id查询用户数量
     *
     * @param roleId 角色id
     * @return {@link Integer } 用户数量
     * @date 2022-09-20 13:42
     */
    Integer selectCntUserRoleByRoleId(Long roleId);
}
