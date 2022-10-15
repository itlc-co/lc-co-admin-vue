package org.lc.admin.web.controller.api.system;

import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.system.entities.request.SystemNoticeRequest;
import org.lc.admin.system.service.SystemNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 系统公告模块controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 19:36
 */
@RequestMapping("/system/notice")
@RestController
@Validated
public class SystemNoticeController extends BaseController {

    /**
     * 模块名称
     */
    public static final String MODULE = "system_notice";

    /**
     * 日志
     */
    public static final Logger log = LoggerFactory.getLogger(SystemNoticeController.class);

    /**
     * 系统通知service服务
     */
    @Resource
    private SystemNoticeService noticeService;

    /**
     * Description: 查询公告列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-23 13:22
     */
    @GetMapping("/list")
    @PreAuthorize("@permissionService.hasPermission('system:notice:list')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public TableData list(SystemNoticeRequest requestParam) {
        return getDataTable(this.noticeService.selectNoticeList(requestParam));
    }

    /**
     * Description: 查询公告详情数据接口
     *
     * @param noticeId 注意id
     * @return {@link ResultResponse }
     * @date 2022-09-23 13:23
     */
    @GetMapping(value = "/{noticeId}")
    @PreAuthorize("@permissionService.hasPermission('system:notice:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse notice(@PathVariable("noticeId") Long noticeId) {
        return success(this.noticeService.selectNoticeById(noticeId));
    }

    /**
     * Description: 添加公告数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:27
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('system:notice:add')")
    @Log(module = MODULE,businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated @RequestBody SystemNoticeRequest requestParam) {
        return toResponse(this.noticeService.addNotice(requestParam));
    }

    /**
     * Description: 编辑公告数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:27
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('system:notice:edit')")
    @Log(module = MODULE,businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated @RequestBody SystemNoticeRequest requestParam) {
        return toResponse(this.noticeService.editNotice(requestParam));
    }

    /**
     * Description: 删除公告列表数据接口
     *
     * @param noticeIds 公告id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:28
     */
    @DeleteMapping("/{noticeIds}")
    @PreAuthorize("@permissionService.hasPermission('system:notice:remove')")
    @Log(module = MODULE,businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("noticeIds") List<Long> noticeIds) {
        this.noticeService.deleteNoticeByIds(noticeIds);
        return success();
    }


}
