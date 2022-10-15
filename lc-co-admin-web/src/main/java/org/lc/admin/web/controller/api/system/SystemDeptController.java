package org.lc.admin.web.controller.api.system;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.DeptException;
import org.lc.admin.common.exec.RoleException;
import org.lc.admin.system.entities.request.SystemDeptRequest;
import org.lc.admin.system.service.SystemDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Description: 系统部门controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 16:23
 */
@RestController
@RequestMapping("/system/dept")
@Validated
public class SystemDeptController extends BaseController {

    public static final String MODULE = "system_dept";

    public static final Logger log = LoggerFactory.getLogger(SystemDeptController.class);

    @Resource
    private SystemDeptService deptService;


    /**
     * Description: 查询部门列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-10 18:24
     */
    @GetMapping("/list")
    @PreAuthorize("@permissionService.hasPermission('system:dept:list')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse list(SystemDeptRequest requestParam) {
        return success(deptService.selectDeptList(requestParam));
    }


    /**
     * Description: 查询部门选择树列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-10 18:24
     */
    @GetMapping("/treeSelect")
    @PreAuthorize("@permissionService.hasPermission('system:dept:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse treeSelect(SystemDeptRequest requestParam) {
        return success(this.deptService.buildDeptTreeSelect(this.deptService.selectDeptList(requestParam)));
    }

    /**
     * Description: 查询部门详情数据接口
     *
     * @param deptId 部门id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-19 15:33
     */
    @GetMapping("/{deptId}")
    @PreAuthorize("@permissionService.hasPermission('system:dept:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse dept(@PathVariable("deptId") Long deptId) {
        // 检查部门数据范围权限
        if (!this.deptService.checkDeptDataScope(deptId)) {
            throw new DeptException(StatusMsg.DEPT_NOT_ACCESS_DATA);
        }
        return success(this.deptService.selectDeptById(deptId));
    }

    /**
     * Description: 排除部门以及父子部门数据接口
     *
     * @return {@link ResultResponse } 响应结果集
     * @date 2022-09-19 15:43
     */
    @GetMapping("/list/exclude/{deptId}")
    @PreAuthorize("@permissionService.hasPermission('system:dept:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse excludeDept(@PathVariable("deptId") Long deptId) {
        return success(deptService.selectDeptListExcludeById(deptId));
    }


    /**
     * Description: 查询角色部门列表树数据接口
     *
     * @param roleId 角色id
     * @return {@link ResultResponse } 响应结果集
     * @date 2022-09-20 08:44
     */
    @GetMapping("/roleDeptTreeSelect/{roleId}")
    @PreAuthorize("@permissionService.hasPermission('system:dept:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse roleDeptTreeSelect(@PathVariable("roleId") Long roleId) {
        ResultResponse response = ResultResponse.fail();
        // 调用service角色部门树获取结果集map{checkedKeys:角色存在部门,depts:所有部门}
        Map<String, Object> roleDeptTreeSelect = this.deptService.roleDeptTreeSelect(roleId);
        if (MapUtil.isNotEmpty(roleDeptTreeSelect)) {
            response = success().put(roleDeptTreeSelect);
        }
        return response;
    }


    /**
     * Description: 添加部门数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-19 16:35
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('system:dept:add')")
    @Log(module = MODULE,businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated @RequestBody SystemDeptRequest requestParam) {
        return toResponse(this.deptService.addDept(requestParam));
    }


    /**
     * Description: 编辑部门数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-19 16:35
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('system:dept:edit')")
    @Log(module = MODULE,businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated @RequestBody SystemDeptRequest requestParam) {
        // 校验是否具有部门数据范围权限
        this.checkDeptDataPermission(requestParam);
        // 检查父部门合理性
        if (ObjectUtil.equals(requestParam.getParentId(), requestParam.getDeptId())) {
            throw new DeptException("父部门不能为自身");
        }
        return toResponse(this.deptService.editDept(requestParam));
    }


    /**
     * Description: 删除部门数据接口
     *
     * @param deptId 部门id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-19 21:21
     */
    @DeleteMapping("/{deptId}")
    @PreAuthorize("@permissionService.hasPermission('system:dept:remove')")
    @Log(module = MODULE,businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("deptId") Long deptId) {
        // 校验是否具有部门数据范围权限
        if (!this.deptService.checkDeptDataScope(deptId)) {
            throw new DeptException(StatusMsg.DEPT_NOT_ACCESS_DATA);
        }
        return toResponse(this.deptService.deleteDeptById(deptId));
    }


    /**
     * Description: 检查部门数据权限
     *
     * @param requestParam 请求参数
     * @date 2022-10-04 10:21
     */
    private void checkDeptDataPermission(SystemDeptRequest requestParam) {
        // 请求参数中部门id
        Long deptId = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getDeptId());

        // 校验是否具有参数部门id的数据权限
        if (!this.deptService.checkDeptDataScope(deptId)) {
            throw new RoleException(StatusMsg.DEPT_NOT_ACCESS_DATA);
        }
    }


}
