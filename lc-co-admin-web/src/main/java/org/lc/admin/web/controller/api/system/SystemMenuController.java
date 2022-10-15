package org.lc.admin.web.controller.api.system;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.exec.MenuException;
import org.lc.admin.system.entities.request.SystemMenuRequest;
import org.lc.admin.system.service.SystemMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Description: 系统菜单模块controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-23 13:29
 */
@RequestMapping("/system/menu")
@RestController
@Validated
public class SystemMenuController extends BaseController {

    public static final String MODULE = "system_menu";

    public static final Logger log = LoggerFactory.getLogger(SystemMenuController.class);

    @Resource
    private SystemMenuService menuService;

    /**
     * Description: 查询菜单选择树数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:29
     */
    @GetMapping("/treeSelect")
    @PreAuthorize("@permissionService.hasPermission('system:menu:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse treeSelect(SystemMenuRequest requestParam) {
        return success(this.menuService.treeSelect(requestParam));
    }

    /**
     * Description: 查询菜单详情数据接口
     *
     * @param menuId 菜单id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:30
     */
    @GetMapping(value = "/{menuId}")
    @PreAuthorize("@permissionService.hasPermission('system:menu:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse menu(@PathVariable("menuId") Long menuId) {
        return success(menuService.selectMenuById(menuId));
    }

    /**
     * Description: 查询菜单列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:30
     */
    @GetMapping(value = "/list")
    @PreAuthorize("@permissionService.hasPermission('system:menu:list')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse list(SystemMenuRequest requestParam) {
        return success(menuService.selectMenuList(requestParam));
    }

    /**
     * Description: 查询角色菜单选择树数据接口
     *
     * @param roleId 角色id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:30
     */
    @GetMapping(value = "/roleMenuTreeSelect/{roleId}")
    @PreAuthorize("@permissionService.hasPermission('system:menu:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        ResultResponse response = ResultResponse.fail();
        // 调用service角色菜单树获取结果集map{checkedKeys:角色存在菜单,menus:所有菜单树}
        Map<String, Object> roleMenuTreeSelect = this.menuService.roleMenuTreeSelect(roleId);
        if (MapUtil.isNotEmpty(roleMenuTreeSelect)) {
            response = success().put(roleMenuTreeSelect);
        }
        return response;
    }

    /**
     * Description: 添加菜单数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:31
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('system:menu:add')")
    @Log(module = MODULE,businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated @RequestBody SystemMenuRequest requestParam) {
        return toResponse(this.menuService.addMenu(requestParam));
    }

    /**
     * Description: 编辑菜单数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:31
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('system:menu:edit')")
    @Log(module = MODULE,businessType = BusinessType.UPDATE)
    public ResultResponse edit(@RequestBody SystemMenuRequest requestParam) {
        // 父级菜单不能为自身
        if (ObjectUtil.equals(requestParam.getMenuId(), requestParam.getParentId())) {
            throw new MenuException(StatusMsg.MENU_PARENT_MENU_ERROR);
        }
        return toResponse(this.menuService.editMenu(requestParam));
    }


    /**
     * Description: 删除菜单数据接口
     *
     * @param menuId 菜单id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:31
     */
    @DeleteMapping("/{menuId}")
    @PreAuthorize("@permissionService.hasPermission('system:menu:remove')")
    @Log(module = MODULE,businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("menuId") Long menuId) {
        this.menuService.deleteMenu(menuId);
        return success();
    }


}
