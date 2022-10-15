package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.entities.TreeSelect;
import org.lc.admin.common.entities.entity.SystemRole;
import org.lc.admin.common.exec.MenuException;
import org.lc.admin.common.utils.system.AuthUserUtils;
import org.lc.admin.common.utils.server.HttpUtils;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.entity.SystemMenu;
import org.lc.admin.system.entities.request.SystemMenuRequest;
import org.lc.admin.system.mapper.SystemMenuMapper;
import org.lc.admin.system.service.SystemMenuService;
import org.lc.admin.system.service.SystemRoleMenuService;
import org.lc.admin.system.service.SystemRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 系统菜单service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-05 10:09
 */
@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @Resource
    private SystemMenuMapper menuMapper;

    @Resource
    private SystemRoleService roleService;

    @Resource
    private SystemRoleMenuService roleMenuService;


    /**
     * Description: 根据用户id查询菜单树数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemMenu }> 菜单树数据
     * @date 2022-09-08 14:05
     */
    @Override
    public List<SystemMenu> selectMenuTreeByUserId(Long userId) {
        // admin用户查询所有菜单树
        List<SystemMenu> menus = AuthUserUtils.isAdmin(userId) ? this.selectMenuTreeAll() : this.menuMapper.selectMenuTreeByUserId(userId);
        // 构建菜单树数据
        return buildMenuTree(menus);
    }

    /**
     * Description: 构建菜单树数据
     *
     * @param menus 菜单列表
     * @return {@link List }<{@link SystemMenu }> 菜单树数据
     * @date 2022-09-08 14:20
     */
    private List<SystemMenu> buildMenuTree(List<SystemMenu> menus) {
        // 按照菜单父节点id进行分组
        Map<Long, List<SystemMenu>> menusGroup = menus.stream().collect(Collectors.groupingBy(SystemMenu::getParentId));
        return menus.stream()
                // 设置一级菜单的子菜单列表
                .map((menu) -> menu.setChildren(menusGroup.getOrDefault(menu.getId(), ListUtil.list(false))))
                // 保留顶级菜单
                .filter((menu) -> menu.getParentId() == 0)
                .collect(Collectors.toList());
    }

    /**
     * Description: 更新菜单数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:06
     */
    @Override
    public Integer updateMenu(SystemMenuRequest requestParam) {
        // bean实体转换并设置id
        SystemMenu menu = BeanUtil.toBean(requestParam, SystemMenu.class);
        menu.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getMenuId()));
        return this.updateMenu(menu);
    }

    /**
     * Description: 更新菜单数据
     *
     * @param menu 菜单
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:06
     */
    @Override
    public Integer updateMenu(SystemMenu menu) {
        // 设置修改者
        menu.setUpdateBy(SecurityUtils.getUserName());
        return this.menuMapper.updateMenu(menu);
    }

    /**
     * Description: 根据菜单id删除菜单数据
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:07
     */
    @Override
    public Integer deleteMenu(Long menuId) {
        // 检查是否存在子菜单
        if (this.checkHasChildMenu(menuId)) {
            throw new MenuException(StatusMsg.MENU_EXIST_SUBMENU);
        }
        // 检查菜单是否已分配角色
        if (this.checkMenuExistRole(menuId)) {
            throw new MenuException(StatusMsg.MENU_EXIST_ROLE);
        }
        return this.deleteMenuById(menuId);
    }

    /**
     * Description: 根据菜单id查询子菜单数量
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-21 11:00
     */
    @Override
    public Integer selectCntChildMenuById(Long menuId) {
        return this.menuMapper.selectCntChildMenuById(menuId);
    }

    /**
     * Description: 添加菜单数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:08
     */
    @Override
    public Integer addMenu(SystemMenuRequest requestParam) {
        // 检查外链地址是否以http(s)://开头
        if (checkFramePath(requestParam)) {
            throw new MenuException(StatusMsg.MENU_FRAME_PATH_FAIL);
        }
        // 检查菜单名称唯一性
        if (checkMenuNameUnique(requestParam)) {
            throw new MenuException(StatusMsg.MENU_NAME_NOT_UNIQUE);
        }
        return this.insertMenu(requestParam);
    }

    /**
     * Description: 查询所有路由菜单树数据
     *
     * @return {@link List }<{@link SystemMenu }> 所有路由菜单树数据
     * @date 2022-09-24 17:32
     */
    @Override
    public List<SystemMenu> selectRouterTreeAll() {
        return this.menuMapper.selectRouterTreeAll();
    }

    /**
     * Description: 根据用户id查询路由树数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemMenu }> 路由菜单树数据
     * @date 2022-09-24 17:30
     */
    @Override
    public List<SystemMenu> selectRouterTreeByUserId(Long userId) {
        // admin用户查询所有菜单路由树
        List<SystemMenu> menus = AuthUserUtils.isAdmin(userId) ? this.selectRouterTreeAll() : this.menuMapper.selectRouterTreeByUserId(userId);
        // 构建菜单路由树数据
        return buildMenuTree(menus);
    }

    /**
     * Description: 查询所有菜单id列表
     *
     * @param menuCheckStrictly 菜单检查严格
     * @return {@link List }<{@link Long }> 所有菜单id列表
     * @date 2022-09-24 16:16
     */
    @Override
    public List<Long> selectMenuIdsAll(Boolean menuCheckStrictly) {
        return this.menuMapper.selectMenuIdsAll(menuCheckStrictly);
    }

    /**
     * Description: 编辑菜单数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:09
     */
    @Override
    public Integer editMenu(SystemMenuRequest requestParam) {
        // 检查外链地址是否以http(s)://开头
        if (checkFramePath(requestParam)) {
            throw new MenuException(StatusMsg.MENU_FRAME_PATH_FAIL);
        }
        // 检查菜单名称唯一性
        if (checkMenuNameUnique(requestParam)) {
            throw new MenuException(StatusMsg.MENU_NAME_NOT_UNIQUE);
        }
        return this.updateMenu(requestParam);
    }


    /**
     * Description: 根据菜单id删除菜单数据
     *
     * @param menuId 菜单id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:07
     */
    @Override
    public Integer deleteMenuById(Long menuId) {
        return this.menuMapper.deleteMenuById(menuId);
    }

    /**
     * Description: 根据菜单id列表删除菜单数据
     *
     * @param menuIds 菜单id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:19
     */
    @Override
    public Integer deleteMenuByIds(List<Long> menuIds) {
        return this.menuMapper.deleteMenuByIds(menuIds);
    }

    /**
     * Description: 根菜单id检查菜单是否存在角色
     *
     * @param menuId 菜单id
     * @return boolean true 存在 false 不存在
     * @date 2022-09-21 10:56
     */
    private boolean checkMenuExistRole(Long menuId) {
        return this.roleMenuService.selectCntRoleByMenuId(menuId) > 0;
    }

    /**
     * Description: 根据菜单id检查是否含有子菜单
     *
     * @param menuId 菜单id
     * @return boolean true 含有 false 不含有
     * @date 2022-09-21 10:56
     */
    private boolean checkHasChildMenu(Long menuId) {
        return this.selectCntChildMenuById(menuId) > 0;
    }

    /**
     * Description: 根据父菜单id与菜单名称查询菜单数据
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单id
     * @return {@link SystemMenu } 菜单数据
     * @date 2022-09-21 10:57
     */
    @Override
    public SystemMenu selectUniqueMenu(String menuName, Long parentId) {
        return this.menuMapper.selectUniqueMenu(menuName, parentId);
    }

    /**
     * Description: 插入菜单数据
     *
     * @param menu 菜单数据
     * @return {@link Integer } 记录树
     * @date 2022-09-23 20:05
     */
    @Override
    public Integer insertMenu(SystemMenu menu) {
        // 设置创建修改者
        String userName = SecurityUtils.getUserName();
        menu.setCreateBy(userName);
        menu.setUpdateBy(userName);
        return this.menuMapper.insertMenu(menu);
    }

    /**
     * Description: 插入菜单数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:05
     */
    @Override
    public Integer insertMenu(SystemMenuRequest requestParam) {
        return this.insertMenu(BeanUtil.toBean(requestParam, SystemMenu.class));
    }

    /**
     * Description: 检查外链路径是否以http(s)://开头
     *
     * @param requestParam 菜单请求参数
     * @return boolean true 否 false 是
     * @date 2022-09-21 10:24
     */
    private boolean checkFramePath(SystemMenuRequest requestParam) {
        return requestParam.getIsFrame() && !HttpUtils.isTcpLink(requestParam.getPath());
    }

    /**
     * Description: 检查菜单名称唯一性
     *
     * @param requestParam 菜单请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-21 10:25
     */
    private boolean checkMenuNameUnique(SystemMenuRequest requestParam) {
        // 防止空指针 前端可能传menuId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getMenuId());
        Long menuId = ObjectUtil.isNull(id) ? -1L : id;

        // 角色名称加载角色信息
        String menuName = requestParam.getMenuName();
        SystemMenu menu = this.selectUniqueMenu(menuName, requestParam.getParentId());

        // 是否唯一
        return ObjectUtil.isNotNull(menu) && !ObjectUtil.equals(menuId, menu.getId());

    }

    /**
     * Description: 构建菜单选择树
     *
     * @return {@link List }<{@link TreeSelect }> 菜单选择树
     * @date 2022-09-21 10:26
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect() {
        return this.buildMenuTreeSelect(this.selectMenuTreeByUserId(SecurityUtils.getUserId()));
    }

    /**
     * Description: 根据角色id列表查询菜单id列表数据
     *
     * @param roleId 角色id列表
     * @return {@link List }<{@link Long }> 菜单id列表数据
     * @date 2022-09-21 10:26
     */
    @Override
    public List<Long> selectMenuIdsByRoleId(Long roleId) {
        SystemRole role = roleService.selectRoleById(roleId);
        return SystemRole.isAdmin(roleId) ? this.selectMenuIdsAll(role.getMenuCheckStrictly()) : this.menuMapper.selectMenuIdsByRole(role);
    }

    /**
     * Description: 根据角色id查询角色菜单选择树数据
     *
     * @param roleId 角色id
     * @return {@link Map }<{@link String }, {@link Object }> 角色菜单选择树数据
     * @date 2022-09-21 10:26
     */
    @Override
    public Map<String, Object> roleMenuTreeSelect(Long roleId) {
        Map<String, Object> resultMap = MapUtil.newHashMap();
        // 已存在菜单id列表
        resultMap.put("checkedKeys", this.selectMenuIdsByRoleId(roleId));
        // 所有菜单选择树
        resultMap.put("menus", this.buildMenuTreeSelect());
        return resultMap;
    }

    /**
     * Description: 根据id查询菜单数据
     *
     * @param menuId 菜单id
     * @return {@link SystemMenu }  菜单数据
     * @date 2022-09-21 10:27
     */
    @Override
    public SystemMenu selectMenuById(Long menuId) {
        return this.menuMapper.selectMenuById(menuId);
    }

    /**
     * Description: 根据用户id查询菜单列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemMenu }> 菜单列表数据
     * @date 2022-09-21 10:28
     */
    @Override
    public List<SystemMenu> selectMenuListByUserId(SystemMenuRequest requestParam) {
        return this.menuMapper.selectMenuListByUserId(requestParam);
    }

    /**
     * Description: 构建菜单选择树数据
     *
     * @param menus 菜单列表
     * @return {@link List }<{@link TreeSelect }> 菜单选择树数据
     * @date 2022-09-23 20:03
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SystemMenu> menus) {
        return this.toMenuTreeSelect(menus);
    }

    /**
     * Description: 转换菜单列表为菜单选择树
     *
     * @param menus 菜单列表
     * @return {@link List }<{@link TreeSelect }> 菜单选择树
     * @date 2022-09-23 20:09
     */
    private List<TreeSelect> toMenuTreeSelect(List<SystemMenu> menus) {
        return menus.stream().map((menu) -> TreeSelect.builder().children(toMenuTreeSelect(menu.getChildren())).id(menu.getId()).label(menu.getMenuName()).build()).collect(Collectors.toList());
    }

    /**
     * Description: 查询菜单列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemMenu }> 菜单列表数据
     * @date 2022-09-23 20:03
     */
    @Override
    public List<SystemMenu> selectMenuList(SystemMenuRequest requestParam) {
        List<SystemMenu> list;
        Long userId = SecurityUtils.getUserId();
        if (AuthUserUtils.isAdmin(userId)) {
            // admin用户查询所有菜单列表
            list = this.menuMapper.selectMenuList(requestParam);
        } else {
            // 非admin用户查询指定用户id下的所有菜单列表
            requestParam.getParams().put("userId", userId);
            list = this.selectMenuListByUserId(requestParam);
        }
        return list;
    }

    /**
     * Description: 查询菜单选择树数据接口
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link TreeSelect }> 菜单选择树数据
     * @date 2022-09-23 20:03
     */
    @Override
    public List<TreeSelect> treeSelect(SystemMenuRequest requestParam) {
        return this.buildMenuTreeSelect(buildMenuTree(this.selectMenuList(requestParam)));
    }

    /**
     * Description: 查询所有菜单树数据
     *
     * @return {@link List }<{@link SystemMenu }> 菜单树数据
     * @date 2022-09-08 14:17
     */
    @Override
    public List<SystemMenu> selectMenuTreeAll() {
        return this.menuMapper.selectMenuTreeAll();
    }

    /**
     * Description: 根据用户id查询菜单权限集合数据
     *
     * @param userId 用户id
     * @return {@link Set }<{@link String }> 菜单权限集合数据
     * @date 2022-09-05 10:10
     */
    @Override
    public Set<String> selectMenuPermissionsByUserId(Long userId) {
        // 调用系统菜单mapper查询用户菜单权限集合
        return menuMapper.selectMenuPermissionsByUserId(userId)
                .stream()
                // 将菜单权限按照,分割后扁平化处理
                .map((permission) -> permission.trim().split(","))
                .flatMap(Arrays::stream)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
    }


}
