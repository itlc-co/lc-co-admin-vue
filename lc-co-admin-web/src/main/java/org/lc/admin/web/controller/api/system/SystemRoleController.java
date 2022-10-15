package org.lc.admin.web.controller.api.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.common.exec.RoleException;
import org.lc.admin.framework.security.service.PermissionService;
import org.lc.admin.framework.security.service.TokenService;
import org.lc.admin.system.entities.request.SystemRoleRequest;
import org.lc.admin.system.entities.request.SystemUserRequest;
import org.lc.admin.system.service.AuthUserService;
import org.lc.admin.system.service.SystemRoleService;
import org.lc.admin.system.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 系统角色controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-19 15:24
 */
@RequestMapping("/system/role")
@RestController
@Validated
public class SystemRoleController extends BaseController {

    public static final String MODULE = "system_role";

    public static final Logger log = LoggerFactory.getLogger(SystemRoleController.class);

    @Resource
    private SystemRoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private SystemUserService userService;

    /**
     * Description: 查询角色列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-20 13:30
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('system:role:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(SystemRoleRequest requestParam) {
        return getDataTable(this.roleService.selectRoleList(requestParam));
    }

    /**
     * Description: 查询角色详情数据接口
     *
     * @param roleId 角色id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 13:31
     */
    @GetMapping("/{roleId}")
    @PreAuthorize("@permissionService.hasPermission('system:role:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse role(@PathVariable("roleId") Long roleId) {
        // 校验角色数据范围权限
        if (!this.roleService.checkRoleDataScope(roleId)) {
            throw new RoleException(StatusMsg.ROLE_NOT_ACCESS_DATA);
        }
        return toResponse(this.roleService.selectRoleById(roleId));
    }

    /**
     * Description: 删除角色列表数据接口
     *
     * @param roleIds 角色id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 13:31
     */
    @DeleteMapping("/{roleIds}")
    @PreAuthorize("@permissionService.hasPermission('system:role:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse deleteRoles(@PathVariable("roleIds") List<Long> roleIds) {

        // 参数角色id列表校验
        // 检查是否允许以及是否具有数据权限
        roleIds = roleIds.stream().peek((roleId) -> {
            this.roleService.checkRoleAllowed(roleId);
            this.roleService.checkRoleDataScope(roleId);
        }).collect(Collectors.toList());

        this.roleService.deleteRoleByRoleIds(roleIds);
        return success();
    }

    /**
     * Description: 查询角色选项选择列表数据接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 13:32
     */
    @GetMapping("/optionSelect")
    @PreAuthorize("@permissionService.hasPermission('system:role:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse optionSelect() {
        return toResponse(this.roleService.selectRolesAll());
    }

    /**
     * Description: 查询角色用户授权列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-20 13:32
     */
    @GetMapping("/authUser/allocatedList")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('system:role:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData allocatedUserList(SystemRoleRequest requestParam) {
        return getDataTable(this.userService.selectAllocatedUserList(BeanUtil.toBean(requestParam, SystemUserRequest.class)));
    }

    /**
     * Description: 查询角色用户未授权列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格结果集
     * @date 2022-09-20 13:33
     */
    @GetMapping("/authUser/unallocatedList")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('system:role:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData unallocatedUserList(SystemRoleRequest requestParam) {
        return getDataTable(this.userService.selectUnallocatedUserList(BeanUtil.toBean(requestParam, SystemUserRequest.class)));
    }

    /**
     * Description: 取消角色用户授权列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 13:34
     */
    @PutMapping("/authUser/cancel")
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @Log(module = MODULE, businessType = BusinessType.GRANT)
    public ResultResponse cancelAuthUser(@RequestBody SystemRoleRequest requestParam) {
        return toResponse(this.roleService.cancelAuthUser(requestParam));
    }

    /**
     * Description: 查询角色所有用户授权数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 13:34
     */
    @PutMapping("/authUser/cancelAll")
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @Log(module = MODULE, businessType = BusinessType.GRANT)
    public ResultResponse cancelAuthUserAll(SystemRoleRequest requestParam) {
        return toResponse(this.roleService.deleteAuthUsers(requestParam));
    }

    /**
     * Description: 添加角色所有用户授权列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 13:34
     */
    @PutMapping("/authUser/selectAll")
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @Log(module = MODULE, businessType = BusinessType.GRANT)
    public ResultResponse addAuthUserAll(SystemRoleRequest requestParam) {
        // 校验角色数据范围权限
        if (!this.roleService.checkRoleDataScope(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getRoleId()))) {
            throw new RoleException(StatusMsg.ROLE_NOT_ACCESS_DATA);
        }
        return toResponse(this.roleService.addAuthUserAll(requestParam));
    }

    /**
     * Description: 改变角色状态数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 15:38
     */
    @PutMapping("/changeStatus")
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse changeStatus(@RequestBody SystemRoleRequest requestParam) {
        this.checkRoleDataPermission(requestParam);
        return toResponse(this.roleService.changeStatus(requestParam));
    }

    /**
     * Description: 编辑系统角色数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 16:07
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated @RequestBody SystemRoleRequest requestParam) {
        // 校验角色数据权限
        this.checkRoleDataPermission(requestParam);
        // 调用角色service服务编辑信息
        Integer row = this.roleService.edit(requestParam);
        if (row > 0) {
            // 更新缓存登录用户权限
            UserDetail userDetail = super.getUserDetail();
            AuthUser authUser = userDetail.getUser();
            if (ObjectUtil.isNotNull(authUser) && !authUser.isAdmin()) {
                userDetail.setPermissions(this.permissionService.getMenuPermissionByUser(authUser));
                userDetail.setUser(this.authUserService.selectAuthUserByUsername(userDetail.getUsername()));
                this.tokenService.setUserDetail(userDetail);
            }
        }
        return toResponse(row);
    }

    /**
     * Description: 授权角色数据权限接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 16:07
     */
    @PutMapping("/dataScope")
    @PreAuthorize("@permissionService.hasPermission('system:role:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse dataScope(@RequestBody SystemRoleRequest requestParam) {
        // 校验角色数据权限
        this.checkRoleDataPermission(requestParam);
        return toResponse(this.roleService.authDataScope(requestParam));
    }


    /**
     * Description: 添加系统角色数据接口
     *
     * @param requestParam 角色要求
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-20 19:17
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('system:role:add')")
    @Log(module = MODULE, businessType = BusinessType.INSERT)
    public ResultResponse add(@RequestBody SystemRoleRequest requestParam) {
        return toResponse(this.roleService.addRole(requestParam));
    }

    /**
     * Description: 导出角色列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-09-23 11:17
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('system:role:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(SystemRoleRequest requestParam, HttpServletResponse response) {
        try {
            // 根据请求参数导出角色列表数据
            this.roleService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }

    /**
     * Description: 检查角色数据权限
     *
     * @param requestParam 请求参数
     * @date 2022-10-04 10:21
     */
    private void checkRoleDataPermission(SystemRoleRequest requestParam) {
        // 请求参数中角色id
        Long roleId = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getRoleId());

        // 校验参数角色id是否允许访问
        if (!this.roleService.checkRoleAllowed(roleId)) {
            throw new RoleException(StatusMsg.ROLE_NOT_ACCESS_ADMIN);
        }

        // 校验是否具有参数角色id的数据权限
        if (!this.roleService.checkRoleDataScope(roleId)) {
            throw new RoleException(StatusMsg.ROLE_NOT_ACCESS_DATA);
        }

    }


}
