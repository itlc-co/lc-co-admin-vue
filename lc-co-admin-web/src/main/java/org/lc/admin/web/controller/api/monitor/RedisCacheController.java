package org.lc.admin.web.controller.api.monitor;

import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.system.entities.request.RedisCacheRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description: redis缓存controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 14:51
 */
@RestController
@RequestMapping("/monitor/redis")
public class RedisCacheController extends BaseController {

    public static final String MODULE = "redis_cache";

    public static final Logger log = LoggerFactory.getLogger(RedisCacheController.class);


    @Resource
    private RedisCacheService redisCacheService;


    /**
     * Description: 查询redis服务信息数据
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-26 19:17
     */
    @GetMapping()
    @PreAuthorize("@permissionService.hasPermission('monitor:cache:list')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse redis() {
        return success(this.redisCacheService.detailInfo());
    }


    /**
     * Description: 查询缓存模块列表数据接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-26 22:30
     */
    @GetMapping("/getModules")
    @PreAuthorize("@permissionService.hasPermission('monitor:cache:list')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse modules() {
        return success(this.redisCacheService.getModules());
    }


    /**
     * Description: 根据缓存模块名称查询缓存名称列表数据
     *
     * @param module 缓存模块名称
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-02 11:45
     */
    @GetMapping("/getNames/{module}")
    @PreAuthorize("@permissionService.hasPermission('monitor:cache:list')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse names(@PathVariable("module") String module) {
        return success(this.redisCacheService.getNamesByModule(module));
    }

    /**
     * Description: 根据请求参数查询缓存键列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-02 11:46
     */
    @GetMapping("/getKeys")
    @PreAuthorize("@permissionService.hasPermission('monitor:cache:list')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse keys(RedisCacheRequest requestParam) {
        return success(this.redisCacheService.getKeysByParam(requestParam));
    }

    /**
     * Description: 根据请求参数查询缓存数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-02 11:47
     */
    @GetMapping("/getValue")
    @PreAuthorize("@permissionService.hasPermission('monitor:cache:query')")
    @Log(module = MODULE,businessType = BusinessType.SELECT)
    public ResultResponse values(RedisCacheRequest requestParam) {
        return success(this.redisCacheService.getValuesByParam(requestParam));
    }

    /**
     * Description: 清除所有缓存数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-02 11:47
     */
    @DeleteMapping("/clearCache")
    @PreAuthorize("@permissionService.hasPermission('monitor:cache:remove')")
    @Log(module = MODULE,businessType = BusinessType.CLEAN)
    public ResultResponse clearCache(@RequestBody RedisCacheRequest requestParam) {
        return toResponse(this.redisCacheService.clearCache(requestParam));
    }


}
