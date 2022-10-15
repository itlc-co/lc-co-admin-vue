package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.common.entities.entity.SystemRole;
import org.lc.admin.system.entities.entity.SystemMenu;
import org.lc.admin.system.entities.request.SystemMenuRequest;

import java.util.List;
import java.util.Set;

/**
 * Description: 系统菜单mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-05 10:10
 */
public interface SystemMenuMapper {

    /**
     * Description: 根据用户id查询菜单权限集合数据
     *
     * @param userId 用户id
     * @return {@link Set }<{@link String }> 菜单权限集合数据
     * @date 2022-09-05 10:18
     */
    Set<String> selectMenuPermissionsByUserId(@Param("userId") Long userId);

    /**
     * Description: 查询所有菜单树数据
     *
     * @return {@link List }<{@link SystemMenu }> 菜单树数据
     * @date 2022-09-08 14:17
     */
    List<SystemMenu> selectMenuTreeAll();

    /**
     * Description: 根据用户id查询菜单树数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemMenu }> 菜单树数据
     * @date 2022-09-08 14:18
     */
    List<SystemMenu> selectMenuTreeByUserId(@Param("userId") Long userId);

    /**
     * Description: 查询菜单列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemMenu }> 菜单列表数据
     * @date 2022-09-23 20:18
     */
    List<SystemMenu> selectMenuList(SystemMenuRequest requestParam);

    /**
     * Description: 根据用户id查询菜单列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemMenu }> 菜单列表数据
     * @date 2022-09-23 20:03
     */
    List<SystemMenu> selectMenuListByUserId(SystemMenuRequest requestParam);

    /**
     * Description: 根据id查询菜单数据
     *
     * @param menuId 菜单id
     * @return {@link SystemMenu }  菜单数据
     * @date 2022-09-23 20:04
     */
    SystemMenu selectMenuById(@Param("menuId") Long menuId);

    /**
     * Description: 根据角色数据查询菜单id列表数据
     *
     * @param role 角色数据
     * @return {@link List }<{@link Long }> 菜单id列表数据
     * @date 2022-09-23 20:16
     */
    List<Long> selectMenuIdsByRole(SystemRole role);

    /**
     * Description: 插入菜单数据
     *
     * @param menu 菜单数据
     * @return {@link Integer } 记录树
     * @date 2022-09-23 20:16
     */
    Integer insertMenu(SystemMenu menu);

    /**
     * Description: 根据父菜单id与菜单名称查询菜单数据
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单id
     * @return {@link SystemMenu } 菜单数据
     * @date 2022-09-23 20:15
     */
    SystemMenu selectUniqueMenu(@Param("menuName") String menuName, @Param("parentId") Long parentId);

    /**
     * Description: 更新菜单数据
     *
     * @param menu 菜单
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:19
     */
    Integer updateMenu(SystemMenu menu);

    /**
     * Description: 根据菜单id查询子菜单数量
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:11
     */
    Integer selectCntChildMenuById(@Param("menuId") Long menuId);

    /**
     * Description: 根据菜单id删除菜单数据
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:07
     */
    Integer deleteMenuById(@Param("menuId") Long menuId);

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
     * @date 2022-09-24 17:31
     */
    List<SystemMenu> selectRouterTreeByUserId(Long userId);

    /**
     * Description: 查询所有路由菜单树数据
     *
     * @return {@link List }<{@link SystemMenu }> 所有路由菜单树数据
     * @date 2022-09-24 17:32
     */
    List<SystemMenu> selectRouterTreeAll();

    /**
     * Description: 根据菜单id列表删除菜单数据
     *
     * @param menuIds 菜单id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:19
     */
    Integer deleteMenuByIds(@Param("menuIds") List<Long> menuIds);
}
