package org.lc.admin.system.service;

import org.lc.admin.common.entities.TreeSelect;
import org.lc.admin.system.entities.entity.SystemMenu;
import org.lc.admin.system.entities.request.SystemMenuRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: 系统菜单service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-05 10:09
 */
public interface SystemMenuService {


    /**
     * Description: 根据用户id查询菜单权限集合数据
     *
     * @param userId 用户id
     * @return {@link Set }<{@link String }> 菜单权限集合数据
     * @date 2022-09-05 10:07
     */
    Set<String> selectMenuPermissionsByUserId(Long userId);

    /**
     * Description: 根据用户id查询菜单树数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemMenu }> 菜单树数据
     * @date 2022-09-08 14:05
     */
    List<SystemMenu> selectMenuTreeByUserId(Long userId);

    /**
     * Description: 查询所有菜单树数据
     *
     * @return {@link List }<{@link SystemMenu }> 菜单树数据
     * @date 2022-09-08 14:05
     */
    List<SystemMenu> selectMenuTreeAll();

    /**
     * Description: 查询菜单选择树数据接口
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link TreeSelect }> 菜单选择树数据
     * @date 2022-09-21 08:40
     */
    List<TreeSelect> treeSelect(SystemMenuRequest requestParam);

    /**
     * Description: 查询菜单列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemMenu }> 菜单列表数据
     * @date 2022-09-23 19:55
     */
    List<SystemMenu> selectMenuList(SystemMenuRequest requestParam);

    /**
     * Description: 构建菜单选择树数据
     *
     * @param menus 菜单列表
     * @return {@link List }<{@link TreeSelect }> 菜单选择树数据
     * @date 2022-09-23 19:55
     */
    List<TreeSelect> buildMenuTreeSelect(List<SystemMenu> menus);

    /**
     * Description: 根据用户id查询菜单列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemMenu }> 菜单列表数据
     * @date 2022-09-23 19:55
     */
    List<SystemMenu> selectMenuListByUserId(SystemMenuRequest requestParam);

    /**
     * Description: 根据id查询菜单数据
     *
     * @param menuId 菜单id
     * @return {@link SystemMenu }  菜单数据
     * @date 2022-09-23 19:56
     */
    SystemMenu selectMenuById(Long menuId);

    /**
     * Description: 根据角色id查询角色菜单选择树数据
     *
     * @param roleId 角色id
     * @return {@link Map }<{@link String }, {@link Object }> 角色菜单选择树数据
     * @date 2022-09-23 19:56
     */
    Map<String, Object> roleMenuTreeSelect(Long roleId);

    /**
     * Description: 根据角色id列表查询菜单id列表数据
     *
     * @param roleId 角色id列表
     * @return {@link List }<{@link Long }> 菜单id列表数据
     * @date 2022-09-23 19:57
     */
    List<Long> selectMenuIdsByRoleId(Long roleId);

    /**
     * Description: 构建菜单选择树
     *
     * @return {@link List }<{@link TreeSelect }> 菜单选择树
     * @date 2022-09-23 19:57
     */
    List<TreeSelect> buildMenuTreeSelect();

    /**
     * Description: 插入菜单数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:58
     */
    Integer insertMenu(SystemMenuRequest requestParam);

    /**
     * Description: 插入菜单数据
     *
     * @param menu 菜单数据
     * @return {@link Integer } 记录树
     * @date 2022-09-23 19:58
     */
    Integer insertMenu(SystemMenu menu);

    /**
     * Description: 根据父菜单id与菜单名称查询菜单数据
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单id
     * @return {@link SystemMenu } 菜单数据
     * @date 2022-09-23 19:58
     */
    SystemMenu selectUniqueMenu(String menuName, Long parentId);

    /**
     * Description: 更新菜单数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:59
     */
    Integer updateMenu(SystemMenuRequest requestParam);

    /**
     * Description: 更新菜单数据
     *
     * @param menu 菜单
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:59
     */
    Integer updateMenu(SystemMenu menu);

    /**
     * Description: 根据菜单id删除菜单数据
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:59
     */
    Integer deleteMenu(Long menuId);

    /**
     * Description: 根据菜单id查询子菜单数量
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:00
     */
    Integer selectCntChildMenuById(Long menuId);

    /**
     * Description: 根据菜单id删除菜单数据
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:00
     */
    Integer deleteMenuById(Long menuId);


    /**
     * Description: 根据菜单id列表删除菜单数据
     *
     * @param menuIds 菜单id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:00
     */
    Integer deleteMenuByIds(List<Long> menuIds);


    /**
     * Description: 添加菜单数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:00
     */
    Integer addMenu(SystemMenuRequest requestParam);

    /**
     * Description: 编辑菜单数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:01
     */
    Integer editMenu(SystemMenuRequest requestParam);

    /**
     * Description: 查询所有菜单id列表
     *
     * @param menuCheckStrictly 菜单检查严格
     * @return {@link List }<{@link Long }> 所有菜单id列表
     * @date 2022-09-24 16:18
     */
    List<Long> selectMenuIdsAll(Boolean menuCheckStrictly);

    /**
     * Description: 根据用户id查询路由树数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemMenu }> 路由菜单树数据
     * @date 2022-09-24 17:29
     */
    List<SystemMenu> selectRouterTreeByUserId(Long userId);

    /**
     * Description: 查询所有路由菜单树
     *
     * @return {@link List }<{@link SystemMenu }> 所有路由菜单树数据
     * @date 2022-09-24 17:31
     */
    List<SystemMenu> selectRouterTreeAll();

}
