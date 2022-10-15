package org.lc.admin.system.service;

import org.lc.admin.common.entities.entity.SystemRole;
import org.lc.admin.system.entities.bo.SystemRoleBo;
import org.lc.admin.system.entities.request.SystemRoleRequest;
import org.lc.admin.system.entities.response.SystemRoleResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Description: 系统角色service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-07 22:00
 */
public interface SystemRoleService {
    /**
     * Description:  根据用户id查询角色权限集合数据
     *
     * @param userId 用户id
     * @return {@link Set }<{@link String }> 角色权限集合
     * @date 2022-09-07 22:01
     */
    Set<String> selectRolePermissionsByUserId(Long userId);

    /**
     * Description: 根据用户id查询所有角色列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemRole }> 角色列表数据
     * @date 2022-09-13 09:22
     */
    List<SystemRole> selectRolesAll(Long userId);

    /**
     * Description: 查询所有角色列表数据
     *
     * @return {@link List }<{@link SystemRole }> 角色列表数据
     * @date 2022-09-13 09:26
     */
    List<SystemRole> selectRolesAll();

    /**
     * Description: 根据用户id查询角色id列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link Long }> 角色id列表数据
     * @date 2022-09-13 10:48
     */
    List<Long> selectRoleIdsByUserId(Long userId);

    /**
     * Description: 根据用户id查询所有角色数据
     * （已有角色isChoose为true反之为false）
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemRoleResponse }> 所有角色列表数据
     * @date 2022-09-14 15:09
     */
    List<SystemRoleResponse> selectAuthRoles(Long userId);


    /**
     * Description: 根据角色id查询角色数据
     *
     * @param roleId 角色id
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 13:35
     */
    SystemRole selectRoleById(Long roleId);

    /**
     * Description: 查询角色列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemRole }> 角色列表数据
     * @date 2022-09-20 13:36
     */
    List<SystemRole> selectRoleList(SystemRoleRequest requestParam);

    /**
     * Description: 取消分配角色用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:36
     */
    Integer cancelAuthUser(SystemRoleRequest requestParam);

    /**
     * Description: 删除分配角色用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:36
     */
    Integer deleteAuthUsers(SystemRoleRequest requestParam);

    /**
     * Description: 添加分配角色用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:36
     */
    Integer addAuthUserAll(SystemRoleRequest requestParam);

    /**
     * Description: 根据角色id删除角色数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:06
     */
    Integer deleteRoleById(Long roleId);

    /**
     * Description: 根据角色id列表删除角色数据
     *
     * @param roleIds 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:37
     */
    Integer deleteRoleByRoleIds(List<Long> roleIds);

    /**
     * Description: 根据角色id查询分配角色用户数量
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:37
     */
    Integer selectCntUserRoleByRoleId(Long roleId);

    /**
     * Description: 改变角色状态
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 15:39
     */
    Integer changeStatus(SystemRoleRequest requestParam);

    /**
     * Description: 更新角色数据
     *
     * @param role 角色数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 15:42
     */
    Integer updateRole(SystemRole role);

    /**
     * Description: 编辑角色信息
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:08
     */
    Integer edit(SystemRoleRequest requestParam);

    /**
     * Description: 更新角色数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:41
     */
    Integer updateRole(SystemRoleRequest requestParam);

    /**
     * Description: 根据角色名称查询角色数据
     *
     * @param roleName 角色名称
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 17:11
     */
    SystemRole selectRoleByRoleName(String roleName);

    /**
     * Description: 根据角色键查询角色数据
     *
     * @param roleKey 角色键
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 18:00
     */
    SystemRole selectRoleByRoleKey(String roleKey);

    /**
     * Description: 授权角色数据权限
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 18:45
     */
    Integer authDataScope(SystemRoleRequest requestParam);

    /**
     * Description: 添加角色数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:17
     */
    Integer addRole(SystemRoleRequest requestParam);

    /**
     * Description: 插入角色数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:20
     */
    Integer insertRole(SystemRoleRequest requestParam);

    /**
     * Description: 根据请求参数导出角色列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-07 17:45
     */
    void export(SystemRoleRequest requestParam, HttpServletResponse response) throws IOException;

    /**
     * Description: 查询系统角色Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemRoleBo }> 角色bo实体列表
     * @date 2022-09-20 21:10
     */
    List<SystemRoleBo> selectRoleBoList(SystemRoleRequest requestParam);

    /**
     * Description: 根据用户名称查询角色名称集合
     *
     * @param userName 用户名
     * @return {@link Set }<{@link String }> 角色名称集合
     * @date 2022-09-23 16:38
     */
    Set<String> selectRoleNamesByUserName(String userName);

    /**
     * Description: 插入角色数据
     *
     * @param role 角色
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:39
     */
    Integer insertRole(SystemRole role);

    /**
     * Description: 校验角色id是否允许访问
     *
     * @param roleId 角色id
     * @return boolean true 允许 false 不允许
     * @date 2022-10-04 10:32
     */
    boolean checkRoleAllowed(Long roleId);

    /**
     * Description: 检查角色id是否有数据权限
     *
     * @param roleId 角色id
     * @return boolean true 具有 false 不具有
     * @date 2022-10-04 10:32
     */
    boolean checkRoleDataScope(Long roleId);
}
