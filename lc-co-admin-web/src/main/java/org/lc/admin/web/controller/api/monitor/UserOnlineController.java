package org.lc.admin.web.controller.api.monitor;

import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.system.entities.request.UserOnlineRequest;
import org.lc.admin.framework.service.UserOnlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description: 用户会话controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 15:36
 */
@RestController
@RequestMapping("/monitor/online")
public class UserOnlineController extends BaseController {

    public static final String MODULE = "user_online";

    public static final Logger log = LoggerFactory.getLogger(UserOnlineController.class);

    @Resource
    private UserOnlineService userOnlineService;

    /**
     * Description: 根据请求参数查询用户会话列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-27 15:35
     */
    @GetMapping("/list")
    @PreAuthorize("@permissionService.hasPermission('monitor:online:list')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public TableData list(UserOnlineRequest requestParam) {
        return getDataTable(this.userOnlineService.list(requestParam));
    }

    /**
     * Description: 强制注销用户接口
     *
     * @param uuid uuid 用户token唯一标识
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-27 15:36
     */
    @DeleteMapping("/{uuid}")
    @PreAuthorize("@permissionService.hasPermission('monitor:online:forceLogout')")
    @Log(module = MODULE,businessType = BusinessType.FORCE)
    public ResultResponse forceLogout(@PathVariable("uuid") String uuid) {
        return toResponse(this.userOnlineService.deleteUserDetail(uuid));
    }

}
