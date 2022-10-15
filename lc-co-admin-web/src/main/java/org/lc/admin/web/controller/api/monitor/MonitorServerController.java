package org.lc.admin.web.controller.api.monitor;

import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.utils.server.ServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 监视服务器controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-26 15:14
 */
@RequestMapping("/monitor/server")
@RestController
public class MonitorServerController extends BaseController {

    public static final String MODULE = "monitor_server";

    public static final Logger log = LoggerFactory.getLogger(MonitorServerController.class);

    /**
     * Description: 服务器数据
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-26 15:15
     */
    @GetMapping()
    @PreAuthorize("@permissionService.hasPermission('monitor:server:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse server() {
        return success(ServerUtils.server());
    }

}
