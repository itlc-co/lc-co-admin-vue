package org.lc.admin.system.service.impl;

import org.lc.admin.system.entities.entity.SystemRoleMenu;
import org.lc.admin.system.mapper.SystemRoleMenuMapper;
import org.lc.admin.system.service.SystemRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 系统角色菜单关联service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-20 13:18
 */
@Service
public class SystemRoleMenuServiceImpl implements SystemRoleMenuService {

    @Resource
    private SystemRoleMenuMapper roleMenuMapper;


    /**
     * Description: 插入角色菜单关联数据
     *
     * @param roleId      角色id
     * @param menuIds 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:34
     */
    @Override
    public Integer batchRoleMenu(Long roleId, List<Long> menuIds) {
        return this.insertRoleMenu(roleId,menuIds);
    }

    /**
     * Description: 根据菜单id查询角色数量
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 23:00
     */
    @Override
    public Integer selectCntRoleByMenuId(Long menuId) {
        return this.roleMenuMapper.selectCntRoleByMenuId(menuId);
    }

    /**
     * Description: 根据角色id删除角色菜单关联数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 17:01
     */
    @Override
    public Integer deleteRoleMenusByRoleId(Long roleId) {
        return this.roleMenuMapper.deleteRoleMenusByRoleId(roleId);
    }

    /**
     * Description: 插入菜单角色关联列表数据
     *
     * @param roleMenus 菜单角色关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:55
     */
    @Override
    public Integer insertRoleMenu(List<SystemRoleMenu> roleMenus) {
        return this.roleMenuMapper.insertRoleMenu(roleMenus);
    }

    /**
     * Description: 插入角色菜单关联数据
     *
     * @param roleId  角色id
     * @param menuIds 菜单id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:52
     */
    @Override
    public Integer insertRoleMenu(Long roleId, List<Long> menuIds) {
        // 映射为角色菜单关联实体
        List<SystemRoleMenu> roleMenus = menuIds.stream().map((menuId) -> SystemRoleMenu.builder().menuId(menuId).roleId(roleId).build()).collect(Collectors.toList());
        return this.insertRoleMenu(roleMenus);
    }

    /**
     * Description: 根据角色id与菜单id列表更新角色菜单关联数据
     *
     * @param roleId  角色id
     * @param menuIds 菜单id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:48
     */
    @Override
    public Integer updateRoleMenuByRoleId(Long roleId,List<Long> menuIds) {
        // 删除更新前角色与菜单关联信息
        this.deleteRoleMenusByRoleId(roleId);

        // 插入更新后角色与菜单关联信息
        return this.insertRoleMenu(roleId,menuIds);
    }

    /**
     * Description: 根据角色id列表删除角色菜单关联数据
     *
     * @param roleIds 角色id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:19
     */
    @Override
    public Integer deleteRoleMenus(List<Long> roleIds) {
        return this.roleMenuMapper.deleteRoleMenus(roleIds);
    }
}
