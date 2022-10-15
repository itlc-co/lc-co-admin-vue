package org.lc.admin.web.controller.api.monitor;

import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.system.entities.request.AccessRecordRequest;
import org.lc.admin.system.service.AccessRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 访问记录controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 17:16
 */
@RestController
@RequestMapping("/monitor/accessRecord")
public class AccessRecordController extends BaseController {

    public static final String MODULE = "access_record";

    public static final Logger log = LoggerFactory.getLogger(AccessRecordController.class);

    @Resource
    private AccessRecordService accessRecordService;

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 查询访问记录列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-23 13:51
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('monitor:logininfor:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(AccessRecordRequest requestParam) {
        return getDataTable(this.accessRecordService.selectAccessRecordList(requestParam));
    }

    /**
     * Description: 删除访问记录列表数据接口
     *
     * @param accessIds 访问id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:51
     */
    @DeleteMapping("/{accessIds}")
    @PreAuthorize("@permissionService.hasPermission('monitor:logininfor:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("accessIds") List<Long> accessIds) {
        return toResponse(this.accessRecordService.deleteAccessRecordByIds(accessIds));
    }

    /**
     * Description: 清空访问记录数据接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:51
     */
    @DeleteMapping("/clean")
    @PreAuthorize("@permissionService.hasPermission('monitor:logininfor:remove')")
    @Log(module = MODULE, businessType = BusinessType.CLEAN)
    public ResultResponse clean() {
        accessRecordService.cleanAccessRecord();
        return success();
    }

    /**
     * Description: 导出访问记录列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-09-23 13:52
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('monitor:logininfor:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(AccessRecordRequest requestParam, HttpServletResponse response) {
        try {
            // 响应导出访问记录列表信息excel
            this.accessRecordService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }

    /**
     * Description: 根据用户名称解锁账号
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-28 21:29
     */
    @GetMapping("/unlock/{userName}")
    @PreAuthorize("@permissionService.hasPermission('monitor:loginfor:unlock')")
    @Log(module = MODULE)
    public ResultResponse unlock(@PathVariable("userName") String userName) {
        ResultResponse response = success();
        // 清理密码错误次数缓存
        boolean flag = this.redisCacheService.clearPasswordRetryCount(userName);
        if (!flag) {
            // 清理失败说明对当前用户无加锁
            response = fail("当前用户未加锁无法解锁");
        }
        return response;
    }


}
