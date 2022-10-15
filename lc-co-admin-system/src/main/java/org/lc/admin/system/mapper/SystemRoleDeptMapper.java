package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.entity.SystemRoleDept;

import java.util.List;

/**
 * Description: 系统角色部门关联mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-20 13:22
 */
public interface SystemRoleDeptMapper {
    /**
     * Description: 根据角色id列表删除角色部门关联数据
     *
     * @param roleIds 角色id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:28
     */
    Integer deleteRoleDepts(@Param("roleIds") List<Long> roleIds);

    /**
     * Description: 根据角色id删除角色部门角色关联数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:04
     */
    Integer deleteRoleDeptByRoleId(@Param("roleId") Long roleId);

    /**
     * Description: 插入角色部门关联列表数据
     *
     * @param roleDepts 角色部门关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:09
     */
    Integer insertRoleDept(@Param("roleDepts") List<SystemRoleDept> roleDepts);
}
