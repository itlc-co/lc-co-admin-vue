package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.entity.SystemRoleMenu;

import java.util.List;

/**
 * Description: 系统角色菜单关联mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-20 13:21
 */
public interface SystemRoleMenuMapper {

    /**
     * Description: 根据角色id列表删除角色菜单关联数据
     *
     * @param roleIds 角色id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:23
     */
    Integer deleteRoleMenus(@Param("roleIds") List<Long> roleIds);

    /**
     * Description: 插入菜单角色关联列表数据
     *
     * @param roleMenus 菜单角色关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:57
     */
    Integer insertRoleMenu(@Param("roleMenus") List<SystemRoleMenu> roleMenus);

    /**
     * Description: 根据角色id删除角色菜单关联数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 17:02
     */
    Integer deleteRoleMenusByRoleId(@Param("roleId") Long roleId);

    /**
     * Description: 根据菜单id查询角色数量
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 23:00
     */
    Integer selectCntRoleByMenuId(@Param("menuId") Long menuId);
}
