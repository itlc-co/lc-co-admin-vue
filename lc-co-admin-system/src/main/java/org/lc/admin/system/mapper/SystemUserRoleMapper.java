package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.entity.SystemUserRole;

import java.util.List;

/**
 * Description: 系统用户角色关联mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 19:28
 */
public interface SystemUserRoleMapper {

    /**
     * Description: 添加用户角色列表关联数据
     *
     * @param userRoles 用户角色列表关联数据
     * @return {@link Integer } 记录数
     * @date 2022-09-13 19:29
     */
    Integer batchUserRole(@Param("userRoles") List<SystemUserRole> userRoles);

    /**
     * Description: 根据用户id删除用户角色关联数据
     *
     * @param userId 用户id
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:18
     */
    Integer deleteUserRolesByUserId(@Param("userId") Long userId);


    /**
     * Description: 根据用户id列表删除用户角色关联数据
     *
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:55
     */
    Integer deleteUserRolesByUserIds(@Param("userIds") List<Long> userIds);

    /**
     * Description: 根据角色id与用户id删除角色用户关联数据
     *
     * @param userRole 角色用户关联数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 22:49
     */
    Integer deleteUserRole(SystemUserRole userRole);

    /**
     * Description: 根据角色id与用户id列表删除角色用户关联数据
     *
     * @param roleId  角色id
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-23 22:50
     */
    Integer deleteUserRoles(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);

    /**
     * Description: 根据角色id查询用户数量
     *
     * @param roleId 角色id
     * @return {@link Integer } 用户数量
     * @date 2022-09-23 22:52
     */
    Integer selectCntUserRoleByRoleId(@Param("roleId") Long roleId);
}
