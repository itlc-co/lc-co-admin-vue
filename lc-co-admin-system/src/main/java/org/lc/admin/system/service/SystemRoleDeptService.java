package org.lc.admin.system.service;

import org.lc.admin.system.entities.entity.SystemRoleDept;

import java.util.List;

/**
 * Description: 系统角色部门关联service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-20 13:18
 */
public interface SystemRoleDeptService {

    /**
     * Description: 根据角色id列表删除角色部门关联数据
     *
     * @param roleIds 角色id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:17
     */
    Integer deleteRoleDepts(List<Long> roleIds);

    /**
     * Description: 根据角色id与部门id列表更新角色部门关联数据
     *
     * @param roleId  角色id
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 18:57
     */
    Integer updateRoleDeptByRoleId(Long roleId, List<Long> deptIds);

    /**
     * Description: 根据角色id删除角色部门角色关联数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 18:58
     */
    Integer deleteRoleDeptByRoleId(Long roleId);

    /**
     * Description: 插入角色部门关联数据
     *
     * @param roleId  角色id
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:05
     */
    Integer insertRoleDept(Long roleId, List<Long> deptIds);

    /**
     * Description: 插入角色部门关联列表数据
     *
     * @param roleDepts 角色部门关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:08
     */
    Integer insertRoleDept(List<SystemRoleDept> roleDepts);
}
