package org.lc.admin.framework.security.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.pool.MenuConstantsPool;
import org.lc.admin.common.utils.server.HttpUtils;
import org.lc.admin.system.entities.entity.SystemMenu;
import org.lc.admin.system.entities.response.Router;
import org.lc.admin.system.service.SystemMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 路由service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-08 12:19
 */
@Service
public class RouterService {

    @Resource
    private SystemMenuService menuService;

    /**
     * Description: 构建路由列表
     *
     * @param userId 用户id
     * @return {@link List }<{@link Router }> 路由列表
     * @date 2022-09-08 14:03
     */
    public List<Router> buildRouters(Long userId) {
        return buildRouters(this.menuService.selectRouterTreeByUserId(userId));
    }

    /**
     * Description: 构建路由列表
     *
     * @param menuTrees 菜单树
     * @return {@link List }<{@link Router }> 路由树列表
     * @date 2022-09-08 16:41
     */
    private List<Router> buildRouters(List<SystemMenu> menuTrees) {
        return menuTrees.stream().map(this::buildRouter).collect(Collectors.toList());
    }

    /**
     * Description: 获取子路由
     *
     * @param menu 菜单
     * @return {@link List }<{@link Router }> 子路由树
     * @date 2022-09-09 11:52
     */
    private List<Router> getRouterChildren(SystemMenu menu) {
        List<Router> childrenRoutes = ListUtil.list(false);
        if (!isMenuFrame(menu)) {
            // 非菜单内部跳转
            // 递归构建路由树
            childrenRoutes = buildRouters(menu.getChildren());
        } else {
            // 菜单内部跳转
            childrenRoutes.add(buildRouter(menu));
        }
        return childrenRoutes;
    }

    /**
     * Description: 构建路由
     *
     * @param menu 菜单
     * @return {@link Router } 路由实体
     * @date 2022-09-09 11:37
     */
    private Router buildRouter(SystemMenu menu) {
        return Router.builder()
                // 是否显示
                .hidden(ObjectUtil.equals(menu.getVisible(), 1))
                // 路由名
                .name(getRouterName(menu))
                // 路由路径
                .path(getRouterPath(menu))
                // 路由组件
                .component(getComponent(menu))
                // 路由参数
                .query(menu.getQuery())
                // 元数据信息
                .meta(getRouterMeta(menu))
                // 是否总是显示
                .isAlwaysShow(getRouterAlwaysShow(menu))
                // 重定向地址
                .redirect(getRouterRedirect(menu))
                // 子路由
                .children(getRouterChildren(menu))
                .build();
    }

    /**
     * Description: 获取路由重定向地址字符串
     *
     * @param menu 菜单
     * @return {@link String } 重定向地址字符串
     * @date 2022-09-09 11:13
     */
    private String getRouterRedirect(SystemMenu menu) {
        String redirect = null;
        // 一级目录设置noRedirect字符串
        if (isDirRouter(menu, menu.getChildren())) {
            redirect = "noRedirect";
        }
        return redirect;
    }

    /**
     * Description: 获取路由是否总是显示
     *
     * @param menu 菜单
     * @return {@link Boolean } true 是 false 否
     * @date 2022-09-09 10:53
     */
    private Boolean getRouterAlwaysShow(SystemMenu menu) {
        Boolean flag = null;
        // 目录路由的总是显示
        List<SystemMenu> children = menu.getChildren();
        if (isDirRouter(menu, children)) {
            flag = Boolean.TRUE;
        }
        return flag;
    }

    /**
     * Description: 是否为目录路由
     *
     * @param menu     菜单
     * @param children 子菜单
     * @return boolean true 是 false 否
     * @date 2022-09-09 11:06
     */
    private boolean isDirRouter(SystemMenu menu, List<SystemMenu> children) {
        return CollUtil.isNotEmpty(children) && children.size() > 0 && MenuConstantsPool.TYPE_DIR.equals(menu.getMenuType());
    }


    /**
     * Description: 获取路由元数据信息
     *
     * @param menu 菜单
     * @return {@link Router.Meta } 元数据信息
     * @date 2022-09-09 10:44
     */
    private Router.Meta getRouterMeta(SystemMenu menu) {
        String link = menu.getPath();
        Router.Meta meta = null;
        if (!isMenuFrame(menu)) {
            // 非菜单内部跳转
            meta = Router.Meta.builder()
                    // 路由标题 ==> 菜单名
                    .title(menu.getMenuName())
                    .icon(menu.getIcon())
                    .isCache(menu.getIsCache())
                    // link ==> path
                    .build();
            if (HttpUtils.isTcpLink(link)) {

                meta.setLink(link);
            }
        }
        return meta;
    }

    /**
     * Description: 获取菜单组件字符串
     *
     * @param menu 菜单
     * @return {@link String } 菜单组件字符串
     * @date 2022-09-08 23:37
     */
    private String getComponent(SystemMenu menu) {
        // 默认Layout组件
        String component = MenuConstantsPool.LAYOUT_COMPONENT;
        if (StrUtil.isNotBlank(menu.getComponent()) && !isMenuFrame(menu)) {
            // 非菜单内部跳转（即菜单类型组件）
            component = menu.getComponent();
        } else if (StrUtil.isBlank(menu.getComponent()) && isLevelMenuInnerLink(menu)) {
            // 非一级菜单内链组件
            component = MenuConstantsPool.INNER_LINK_COMPONENT;
        } else if (StrUtil.isBlank(menu.getComponent()) && isParentView(menu)) {
            // 父视图组件ParentView
            component = MenuConstantsPool.PARENT_VIEW_COMPONENT;
        }
        return component;
    }

    /**
     * Description: 是否为一级菜单内链方式
     *
     * @param menu 菜单
     * @return boolean
     * @date 2022-09-09 10:35
     */
    private boolean isLevelMenuInnerLink(SystemMenu menu) {
        return menu.getParentId() != 0L && isInnerLink(menu);
    }

    /**
     * Description: 是否为parent_view组件
     *
     * @param menu 菜单
     * @return boolean true 是 false 否
     * @date 2022-09-08 23:32
     */
    private boolean isParentView(SystemMenu menu) {
        return menu.getParentId().intValue() != 0 && MenuConstantsPool.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * Description: 获取路由地址path
     *
     * @param menu 菜单
     * @return {@link String } 路由地址path
     * @date 2022-09-08 17:49
     */
    private String getRouterPath(SystemMenu menu) {
        String path = menu.getPath();
        // 菜单是否为内链打开外链方式
        if (isMenuInnerLink(menu)) {
            path = replaceInnerLink(path);
        }

        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && MenuConstantsPool.TYPE_DIR.equals(menu.getMenuType())
                && !isFrame(menu)) {
            path = "/" + menu.getPath();
        }

        // 一级菜单是否为内链菜单方式
        if (isMenuFrame(menu)) {
            path = "/";
        }

        return path;
    }

    /**
     * Description: 菜单是否为内链
     *
     * @param menu 菜单
     * @return boolean true:是 false:否
     * @date 2022-09-09 09:58
     */
    private boolean isMenuInnerLink(SystemMenu menu) {
        return menu.getParentId() != 0L && isInnerLink(menu);
    }

    /**
     * Description: 替换内链路径 删除http https前缀
     *
     * @param path 内链路径
     * @return {@link String } 替换后的路径
     * @date 2022-09-08 18:15
     */
    private String replaceInnerLink(String path) {
        return path.replace("http", "").replace("https", "");
    }

    /**
     * Description: 是否为内链
     *
     * @param menu 菜单
     * @return boolean
     * @date 2022-09-08 17:53
     */
    private boolean isInnerLink(SystemMenu menu) {
        return !isFrame(menu) && HttpUtils.isTcpLink(menu.getPath());
    }

    /**
     * Description: 获取路由名 (首字母大写)
     *
     * @param menu 菜单
     * @return {@link String } 路由名 (首字母大写)
     * @date 2022-09-08 16:55
     */
    private String getRouterName(SystemMenu menu) {
        return StrUtil.upperFirst(menu.getPath());
    }

    /**
     * Description: 是否为菜单内部跳转
     *
     * @param menu 菜单
     * @return boolean
     * @date 2022-09-08 17:24
     */
    private boolean isMenuFrame(SystemMenu menu) {
        return menu.getParentId() == 0L &&
                MenuConstantsPool.TYPE_MENU.equals(menu.getMenuType()) &&
                !isFrame(menu);
    }

    /**
     * Description: 是否为外链
     *
     * @param menu 菜单
     * @return boolean
     * @date 2022-09-08 17:35
     */
    private boolean isFrame(SystemMenu menu) {
        return menu.getIsFrame();
    }

}
