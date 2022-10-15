package org.lc.admin.web.controller.api.monitor;

import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.system.entities.request.OperationLogRequest;
import org.lc.admin.system.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 操作日志controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 17:46
 */
@RestController
@RequestMapping("/monitor/operationLog")
public class OperationLogController extends BaseController {

    public static final String MODULE = "operation_log";

    public static final Logger log = LoggerFactory.getLogger(OperationLogController.class);

    @Resource
    private OperationLogService operationLogService;


    /**
     * Description: 查询操作日志列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-23 13:49
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('monitor:operlog:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(OperationLogRequest requestParam) {
        return getDataTable(this.operationLogService.selectOperationLogList(requestParam));
    }

    /**
     * Description: 删除操作日志列表数据接口
     *
     * @param operationIds 操作id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:49
     */
    @DeleteMapping("/{operationIds}")
    @PreAuthorize("@permissionService.hasPermission('monitor:operlog:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("operationIds") List<Long> operationIds) {
        return toResponse(this.operationLogService.deleteOperationLogByIds(operationIds));
    }

    /**
     * Description: 清空操作日志数据接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:50
     */
    @DeleteMapping("/clean")
    @PreAuthorize("@permissionService.hasPermission('monitor:operlog:remove')")
    @Log(module = MODULE, businessType = BusinessType.CLEAN)
    public ResultResponse clean() {
        this.operationLogService.cleanOperationLog();
        return success();
    }

    /**
     * Description: 导出操作日志列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-09-23 13:50
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('monitor:operlog:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(OperationLogRequest requestParam, HttpServletResponse response) {
        try {
            // 响应导出日志记录列表信息excel
            operationLogService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }


}
