package org.lc.admin.web.controller.api.system;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.common.exec.UserException;
import org.lc.admin.common.validated.group.Insert;
import org.lc.admin.common.validated.group.ResetPwd;
import org.lc.admin.common.validated.group.Update;
import org.lc.admin.system.entities.bo.SystemUserBo;
import org.lc.admin.system.entities.request.SystemUserRequest;
import org.lc.admin.system.service.SystemPostService;
import org.lc.admin.system.service.SystemRoleService;
import org.lc.admin.system.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 系统用户controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-10 17:09
 */
@RestController
@RequestMapping("/system/user")
@Validated
public class SystemUserController extends BaseController {

    public static final String MODULE = "system_user";

    public static final Logger log = LoggerFactory.getLogger(SystemUserController.class);

    @Resource
    private SystemUserService userService;

    @Resource
    private SystemRoleService roleService;

    @Resource
    private SystemPostService postService;


    /**
     * Description: 查询用户列表数据接口
     *
     * @param requestParam 用户请求
     * @return {@link TableData }  用户表格数据
     * @date 2022-09-10 18:24
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('system:user:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(SystemUserRequest requestParam) {
        return getDataTable(this.userService.selectUserList(requestParam));
    }

    /**
     * Description: 查询用户详情数据接口
     *
     * @param userId 用户id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-13 09:00
     */
    @GetMapping(value = {"/", "/{userId}"})
    @PreAuthorize("@permissionService.hasPermission('system:user:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse user(@PathVariable(value = "userId", required = false) Long userId) {

        // 校验当前认证用户是否具有参数用户id的数据权限
        if (!this.userService.checkUserDataScope(userId)) {
            throw new UserException(StatusMsg.USER_NOT_ACCESS_DATA);
        }

        // 默认成功响应
        ResultResponse response = success();

        // 获取角色列表以及岗位列表（前端需要下拉框选择）
        response.put("roles", this.roleService.selectRolesAll(userId));
        response.put("posts", this.postService.selectPostsAll());

        // 获取用户详情信息
        Map<String, Object> userDetailMap = this.userService.userDetail(userId);
        if (MapUtil.isNotEmpty(userDetailMap)) {
            response.put(userDetailMap);
        }

        return response;
    }

    /**
     * Description: 添加用户数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-13 15:40
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('system:user:add')")
    @Log(module = MODULE, businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated(Insert.class) @RequestBody SystemUserRequest requestParam) {
        return toResponse(this.userService.addUser(requestParam));
    }

    /**
     * Description: 编辑用户数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-13 15:40
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('system:user:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated(Update.class) @RequestBody SystemUserRequest requestParam) {
        // 检查用户数据权限
        this.checkUserDataPermission(requestParam);
        return toResponse(this.userService.editUser(requestParam));
    }


    /**
     * Description: 重置用户密码接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-13 15:40
     */
    @PutMapping("/resetPwd")
    @PreAuthorize("@permissionService.hasPermission('system:user:resetPwd')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse resetPwd(@Validated(ResetPwd.class) @RequestBody SystemUserRequest requestParam) {
        // 检查用户数据权限
        this.checkUserDataPermission(requestParam);
        return toResponse(this.userService.resetPwd(requestParam));
    }

    /**
     * Description: 改变用户状态接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-13 15:40
     */
    @PutMapping("/changeStatus")
    @PreAuthorize("@permissionService.hasPermission('system:user:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse changeStatus(@RequestBody SystemUserRequest requestParam) {
        // 检查用户数据权限
        this.checkUserDataPermission(requestParam);
        return toResponse(this.userService.changeStatus(requestParam));
    }

    /**
     * Description: 删除用户数据接口
     *
     * @param userIds 用户id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-14 09:32
     */
    @DeleteMapping("/{userIds}")
    @PreAuthorize("@permissionService.hasPermission('system:user:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable(value = "userIds") List<Long> userIds) {
        // 当前登录用户无法被删除
        if (CollUtil.contains(userIds, super.getUserId())) {
            return super.fail(StatusMsg.LOGIN_USER_NOT_DELETE);
        }

        // 参数用户id列表校验
        // 检查是否允许以及是否具有数据权限
        userIds = userIds.stream().peek((userId) -> {
            this.userService.checkUserAllowed(userId);
            this.userService.checkUserDataScope(userId);
        }).collect(Collectors.toList());

        this.userService.deleteUserByIds(userIds);
        return success();
    }

    /**
     * Description: 查询用户角色授权数据接口
     *
     * @param userId 用户id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-14 13:05
     */
    @GetMapping("/authRole/{userId}")
    @PreAuthorize("@permissionService.hasPermission('system:user:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse authRole(@PathVariable("userId") Long userId) {
        return toResponse(this.userService.getAuthRoles(userId));
    }

    /**
     * Description: 授权用户角色接口
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-14 13:07
     */
    @PutMapping("/authRole")
    @PreAuthorize("@permissionService.hasPermission('system:user:edit')")
    @Log(module = MODULE, businessType = BusinessType.GRANT)
    public ResultResponse authRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") List<Long> roleIds) {
        // 校验参数用户id是否允许访问
        if (!this.userService.checkUserAllowed(userId)) {
            throw new UserException(StatusMsg.USER_NOT_ACCESS_ADMIN);
        }

        // 校验当前认证用户是否具有参数用户id的数据权限
        if (!this.userService.checkUserDataScope(userId)) {
            throw new UserException(StatusMsg.USER_NOT_ACCESS_DATA);
        }

        this.userService.authRole(userId, roleIds);
        return success();
    }

    /**
     * Description: 下载用户列表导入数据模板接口
     *
     * @param response 响应
     * @param request  请求
     * @date 2022-09-14 22:05
     */
    @PostMapping("/importTemplate")
    @PreAuthorize("@permissionService.hasPermission('system:user:import')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void importTemplate(HttpServletResponse response, HttpServletRequest request) {
        try {
            // 用户列表数据excel导入模板下载
            this.userService.importTemplate(request, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_IMPORT_TEMPLATE_ERROR);
        }
    }

    /**
     * Description: 导出用户列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-09-19 14:37
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('system:user:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(SystemUserRequest requestParam, HttpServletResponse response) {
        try {
            // 响应导出用户列表信息excel
            this.userService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }

    /**
     * Description: 导入用户列表数据接口
     *
     * @param file          导入文件
     * @param updateSupport 是否开启更新支持
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 11:14
     */
    @PostMapping("/importData")
    @PreAuthorize("@permissionService.hasPermission('system:user:import')")
    @Log(module = MODULE, businessType = BusinessType.IMPORT)
    public ResultResponse importData(MultipartFile file, boolean updateSupport) {

        // 结果集消息
        StringBuilder msg = new StringBuilder(StrUtil.EMPTY);
        ExcelImportResult<SystemUserBo> importResult;

        try {
            // excel导入用户列表数据返回导入结果集
            importResult = ExcelUtils.importMore(file, SystemUserBo.class);
        } catch (Exception e) {
            throw new FileException(StatusMsg.EXCEL_IMPORT_FILE_EMPTY);
        }

        // 校验未通过导入失败用户名称列表
        Set<String> failUserNames = importResult.getFailList().stream().map((SystemUserBo::getUserName)).collect(Collectors.toSet());

        // 校验通过后需要导入的用户实体列表
        List<SystemUserBo> userBos = importResult.getList();

        // 调用service接口实现用户列表导入
        Map<String, Set<String>> userNameMap = this.userService.importUser(userBos, updateSupport);

        // 导入成功用户名称列表
        Set<String> successUserNames = userNameMap.getOrDefault("successUserNames", null);

        // 校验通过后导入失败用户名称列表追加至失败用户名称列表
        Set<String> tmpFailUserNames = userNameMap.getOrDefault("failUserNames", null);
        if (CollUtil.isNotEmpty(tmpFailUserNames)) {
            failUserNames.addAll(tmpFailUserNames);
        }

        // 导入成功用户名称列表消息
        if (CollUtil.isNotEmpty(successUserNames)) {
            msg.append(StrUtil.format("如下:[{}]用户列表,共{}条用户信息导入成功", StrUtil.join(",", successUserNames), successUserNames.size()));
        }

        // 导入失败用户名称列表消息
        if (CollUtil.isNotEmpty(failUserNames)) {
            msg.append(StrUtil.format("如下:[{}]用户列表导入失败,共{}条用户信息导入失败", StrUtil.join(",", failUserNames), failUserNames.size()));
        }
        return success(msg.toString());
    }


    /**
     * Description: 检查用户数据权限
     *
     * @param requestParam 请求参数
     * @date 2022-10-04 10:21
     */
    private void checkUserDataPermission(SystemUserRequest requestParam) {
        // 请求参数中用户id
        Long userId = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getUserId());

        // 校验参数用户id是否允许访问
        if (!this.userService.checkUserAllowed(userId)) {
            throw new UserException(StatusMsg.USER_NOT_ACCESS_ADMIN);
        }

        // 校验是否具有参数用户id的数据权限
        if (!this.userService.checkUserDataScope(userId)) {
            throw new UserException(StatusMsg.USER_NOT_ACCESS_DATA);
        }
    }

}
