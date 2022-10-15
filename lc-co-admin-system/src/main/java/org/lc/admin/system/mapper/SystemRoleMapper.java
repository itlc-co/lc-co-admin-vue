package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.common.annotation.DataSource;
import org.lc.admin.common.entities.entity.SystemRole;
import org.lc.admin.common.entities.enums.DataSourceName;
import org.lc.admin.system.entities.bo.SystemRoleBo;
import org.lc.admin.system.entities.request.SystemRoleRequest;
import org.lc.admin.system.entities.response.SystemRoleResponse;

import java.util.List;
import java.util.Set;

/**
 * Description: 系统角色mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-07 22:07
 */
public interface SystemRoleMapper {
    /**
     * Description: 根据用户id查询角色权限集合数据
     *
     * @param userId 用户id
     * @return {@link Set }<{@link String }> 角色权限集合
     * @date 2022-09-07 22:05
     */
    Set<String> selectRolePermissionsByUserId(@Param("userId") Long userId);

    /**
     * Description: 查询所有角色列表数据
     *
     * @return {@link List }<{@link SystemRole }> 角色列表数据
     * @date 2022-09-13 09:27
     */
    List<SystemRole> selectRolesAll();

    /**
     * Description: 根据用户id查询角色id列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link Long }> 角色id列表数据
     * @date 2022-09-13 10:49
     */
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * Description: 根据用户id查询角色列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemRoleResponse }> 角色列表数据
     * @date 2022-09-14 11:55
     */
    List<SystemRoleResponse> selectRolesResponseByUserId(@Param("userId") Long userId);

    /**
     * Description: 根据用户id查询所有角色数据
     * （已有角色isChoose为true反之为false）
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemRoleResponse }> 所有角色列表数据
     * @date 2022-09-14 15:11
     */
    List<SystemRoleResponse> selectAuthRoles(@Param("userId") Long userId);

    /**
     * Description: 根据角色id查询角色数据
     *
     * @param roleId 角色id
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 13:40
     */
    SystemRole selectRoleById(@Param("roleId") Long roleId);

    /**
     * Description: 查询角色列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemRole }> 角色列表数据
     * @date 2022-09-20 13:40
     */
    List<SystemRole> selectRoleList(SystemRoleRequest requestParam);

    /**
     * Description: 根据角色id列表删除角色数据
     *
     * @param roleIds 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:40
     */
    Integer deleteRoleByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * Description: 更新角色数据
     *
     * @param role 角色数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 15:43
     */
    Integer updateRole(SystemRole role);

    /**
     * Description: 根据角色名称查询角色数据
     *
     * @param roleName 角色名称
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 17:12
     */
    SystemRole selectRoleByRoleName(@Param("roleName") String roleName);

    /**
     * Description: 根据角色键查询角色数据
     *
     * @param roleKey 角色键
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 18:01
     */
    SystemRole selectRoleByRoleKey(@Param("roleKey") String roleKey);

    /**
     * Description: 插入角色数据
     *
     * @param role 角色
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:22
     */
    Integer insertRole(SystemRole role);

    /**
     * Description: 查询系统角色Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemRoleBo }> 角色bo实体列表
     * @date 2022-09-20 21:11
     */
    List<SystemRoleBo> selectRoleBoList(SystemRoleRequest requestParam);

    /**
     * Description: 根据用户名称查询角色名称集合
     *
     * @param userName 用户名
     * @return {@link Set }<{@link String }> 角色名称集合
     * @date 2022-09-23 16:47
     */
    Set<String> selectRoleNamesByUserName(@Param("userName") String userName);

    /**
     * Description: 根据角色id删除角色数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:06
     */
    Integer deleteRoleByRoleId(@Param("roleId") Long roleId);
}
