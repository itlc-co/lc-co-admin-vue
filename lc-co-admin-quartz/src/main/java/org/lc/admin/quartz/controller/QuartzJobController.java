package org.lc.admin.quartz.controller;

import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.common.exec.QuartzJobException;
import org.lc.admin.common.validated.group.Insert;
import org.lc.admin.common.validated.group.Update;
import org.lc.admin.quartz.entities.request.QuartzJobRequest;
import org.lc.admin.quartz.service.QuartzJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 定时任务controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 12:55
 */
@RestController
@RequestMapping("/monitor/job")
@Validated
public class QuartzJobController extends BaseController {

    public static final String MODULE = "quartz_job";

    public static final Logger log = LoggerFactory.getLogger(QuartzJobController.class);

    @Resource
    private QuartzJobService quartzJobService;

    /**
     * Description: 根据任务id查询任务数据接口
     *
     * @param jobId 任务id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-11 12:56
     */
    @GetMapping("/{jobId}")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse quartzJob(@PathVariable("jobId") Long jobId) {
        return toResponse(quartzJobService.selectQuartzJobById(jobId));
    }

    /**
     * Description: 根据任务请求参数查询任务数据列表接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-10-11 12:58
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('monitor:job:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(QuartzJobRequest requestParam) {
        return getDataTable(quartzJobService.selectQuartzJobList(requestParam));
    }

    /**
     * Description: 根据任务请求参数添加任务数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 响应结果集
     * @date 2022-10-11 12:59
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('monitor:job:add')")
    @Log(module = MODULE, businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated(Insert.class) @RequestBody QuartzJobRequest requestParam) {
        // 检查任务调用目标字符串是否合理
        StatusMsg statusMsg = quartzJobService.checkJobInvokeTarget(requestParam);
        if (ObjectUtil.isNotNull(statusMsg)) {
            // 状态消息不为null则说明目标字符串不合理则抛出任务异常
            throw new QuartzJobException(statusMsg);
        }
        return toResponse(quartzJobService.addQuartzJob(requestParam));
    }

    /**
     * Description: 根据任务请求参数编辑任务数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-11 13:29
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('monitor:job:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated(Update.class) @RequestBody QuartzJobRequest requestParam) {
        // 检查任务调用目标字符串是否合理
        StatusMsg statusMsg = quartzJobService.checkJobInvokeTarget(requestParam);
        if (ObjectUtil.isNotNull(statusMsg)) {
            // 状态消息不为null则说明目标字符串不合理则抛出任务异常
            throw new QuartzJobException(statusMsg);
        }
        return toResponse(quartzJobService.editQuartzJob(requestParam));
    }

    /**
     * Description: 根据任务请求参数修改任务状态接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 响应结果集
     * @date 2022-10-11 13:33
     */
    @PutMapping("/changeStatus")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:changeStatus')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse changeStatus(@RequestBody QuartzJobRequest requestParam) {
        return toResponse(quartzJobService.changeStatus(requestParam));
    }

    /**
     * Description: 根据任务请求参数运行任务接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-11 13:35
     */
    @PutMapping("/run")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:changeStatus')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse run(@RequestBody QuartzJobRequest requestParam) {
        return toResponse(quartzJobService.run(requestParam));
    }

    /**
     * Description: 根据任务id列表删除任务列表数据接口
     *
     * @param jobIds 任务id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-11 13:36
     */
    @DeleteMapping("/{jobIds}")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("jobIds") List<Long> jobIds) {
        quartzJobService.deleteQuartzJobByIds(jobIds);
        return success();
    }

    /**
     * Description: 根据任务id删除任务数据接口
     *
     * @param jobId 任务id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-11 13:44
     */
    @DeleteMapping("/delete/{jobId}")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("jobId") Long jobId) {
        return toResponse(quartzJobService.deleteQuartzJobById(jobId));
    }

    /**
     * Description: 导出定时任务列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-10-11 17:06
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(QuartzJobRequest requestParam, HttpServletResponse response) {
        try {
            // 响应导出定时任务列表信息excel
            quartzJobService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }

}
