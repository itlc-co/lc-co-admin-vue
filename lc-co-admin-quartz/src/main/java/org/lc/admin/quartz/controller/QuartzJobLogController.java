package org.lc.admin.quartz.controller;

import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.quartz.entities.request.QuartzJobLogRequest;
import org.lc.admin.quartz.service.QuartzJobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 定时任务日志controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 13:37
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class QuartzJobLogController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(QuartzJobLogController.class);

    public static final String MODULE = "quartz_job_log";

    @Resource
    private QuartzJobLogService quartzJobLogService;

    /**
     * Description: 根据请求参数查询任务日志列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 日志表格数据
     * @date 2022-10-12 13:21
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('monitor:job:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(QuartzJobLogRequest requestParam) {
        return getDataTable(quartzJobLogService.selectQuartzJobLogList(requestParam));
    }

    /**
     * Description: 根据任务日志id查询任务日志数据接口
     *
     * @param jobLogId 任务日志id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-12 13:24
     */
    @GetMapping(value = "/{jobLogId}")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse jobLog(@PathVariable("jobLogId") Long jobLogId) {
        return toResponse(quartzJobLogService.selectQuartzJobLogById(jobLogId));
    }

    /**
     * Description: 根据任务日志id列表删除任务日志列表数据接口
     *
     * @param jobLogIds 工作日志id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-12 13:28
     */
    @DeleteMapping("/{jobLogIds}")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("jobLogIds") List<Long> jobLogIds) {
        return toResponse(quartzJobLogService.deleteQuartzJobLogByIds(jobLogIds));
    }

    /**
     * Description: 根据任务日志id删除任务日志数据
     *
     * @param jobLogId 任务日志id
     * @return {@link ResultResponse } 响应结果集
     * @date 2022-10-12 13:31
     */
    @DeleteMapping("/jobLog/{jobLogId}")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("jobLogId") Long jobLogId) {
        return toResponse(quartzJobLogService.deleteQuartzJobLogById(jobLogId));
    }

    /**
     * Description: 清空任务日志数据接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-12 13:32
     */
    @DeleteMapping("/clean")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:remove')")
    @Log(module = MODULE, businessType = BusinessType.CLEAN)
    public ResultResponse clean() {
        quartzJobLogService.cleanQuartJobLog();
        return success();
    }

    /**
     * Description: 根据请求参数导出任务日志列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-10-12 13:32
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('monitor:job:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(QuartzJobLogRequest requestParam, HttpServletResponse response) {
        try {
            // 响应导出定时任务日志列表信息excel
            quartzJobLogService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }

}
