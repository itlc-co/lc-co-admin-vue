package org.lc.admin.system.service;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.entity.SystemRoleMenu;

import java.util.List;

/**
 * Description: 系统角色菜单关联service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-20 13:17
 */
public interface SystemRoleMenuService {
    /**
     * Description: 根据角色id列表删除角色菜单关联数据
     *
     * @param roleIds 角色id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:17
     */
    Integer deleteRoleMenus(List<Long> roleIds);

    /**
     * Description: 根据角色id与菜单id列表更新角色菜单关联数据
     *
     * @param roleId  角色id
     * @param menuIds 菜单id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:49
     */
    Integer updateRoleMenuByRoleId(Long roleId,List<Long> menuIds);

    /**
     * Description: 插入角色菜单关联数据
     *
     * @param roleId  角色id
     * @param menuIds 菜单id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:52
     */
    Integer insertRoleMenu(Long roleId, List<Long> menuIds);

    /**
     * Description: 插入菜单角色关联列表数据
     *
     * @param roleMenus 菜单角色关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:55
     */
    Integer insertRoleMenu(List<SystemRoleMenu> roleMenus);

    /**
     * Description: 根据角色id删除角色菜单关联数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 17:00
     */
    Integer deleteRoleMenusByRoleId(@Param("roleId") Long roleId);

    /**
     * Description: 插入角色菜单关联数据
     *
     * @param roleId      角色id
     * @param menuIds 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:33
     */
    Integer batchRoleMenu(Long roleId, List<Long> menuIds);

    /**
     * Description: 根据菜单id查询角色数量
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:12
     */
    Integer selectCntRoleByMenuId(Long menuId);
}
