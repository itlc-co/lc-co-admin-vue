package org.lc.admin.web.controller.api.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.system.entities.entity.SystemConfig;
import org.lc.admin.system.entities.request.SystemConfigRequest;
import org.lc.admin.system.service.SystemConfigService;
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
 * Description: 系统配置controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 21:47
 */
@RestController
@RequestMapping("/system/config")
@Validated
public class SystemConfigController extends BaseController {

    public static final String MODULE = "system_config";

    public static final Logger log = LoggerFactory.getLogger(SystemConfigController.class);

    @Resource
    private SystemConfigService configService;

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 查询配置详情数据接口
     *
     * @param config 配置
     * @return {@link ResultResponse }
     * @date 2022-09-23 13:42
     */
    @GetMapping("")
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse config(SystemConfig config) {
        return success(this.configService.selectConfig(config));
    }

    /**
     * Description: 查询配置列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-23 13:42
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(SystemConfigRequest requestParam) {
        return getDataTable(this.configService.selectConfigList(requestParam));
    }

    /**
     * Description: 查询配置详情数据接口
     *
     * @param configId 配置id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:43
     */
    @GetMapping("/{configId}")
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse config(@PathVariable("configId") Long configId) {
        return toResponse(this.configService.selectConfigById(configId));
    }

    /**
     * Description: 查询配置数据接口
     *
     * @param configKey 配置键
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:44
     */
    @GetMapping(value = "/configKey/{configKey}")
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse getConfigKey(@PathVariable("configKey") String configKey) {
        SystemConfig config = this.redisCacheService.getSystemConfigByKey(configKey);
        if (ObjectUtil.isNotNull(config)) {
            // 保存配置缓存
            config = this.configService.selectConfigByKey(configKey);
            if (ObjectUtil.isNotNull(config)) {
                this.redisCacheService.saveSystemConfig(config);
            }
        }
        return toResponse(config);
    }

    /**
     * Description: 添加配置数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:44
     */
    @PostMapping()
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:add')")
    @Log(module = MODULE, businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated @RequestBody SystemConfigRequest requestParam) {
        Integer row = this.configService.addConfig(requestParam);
        if (row > 0) {
            // 保存配置缓存
            this.redisCacheService.saveSystemConfig(requestParam.getConfigKey());
        }
        return toResponse(row);
    }

    /**
     * Description: 编辑配置数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:46
     */
    @PutMapping()
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated @RequestBody SystemConfigRequest requestParam) {
        Integer row = this.configService.editConfig(requestParam);
        if (row > 0) {
            // 保存配置缓存
            this.redisCacheService.saveSystemConfig(requestParam.getConfigKey());
        }
        return toResponse(row);
    }

    /**
     * Description: 删除配置列表数据接口
     *
     * @param configIds 配置id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:46
     */
    @DeleteMapping("/{configIds}")
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("configIds") List<Long> configIds) {
        List<SystemConfig> configs = this.configService.deleteConfigByIds(configIds);
        if (CollUtil.isNotEmpty(configs)) {
            // 删除配置缓存
            this.redisCacheService.deleteSystemConfigs(configs);
        }
        return success(CollUtil.isNotEmpty(configs) ? configs.size() : 0);
    }

    /**
     * Description: 导出配置列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-09-23 13:46
     */
    @PostMapping("/export")
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(SystemConfigRequest requestParam, HttpServletResponse response) {
        try {
            // 根据请求参数导出配置列表数据
            this.configService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }

    /**
     * Description: 刷新配置缓存接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-02 11:38
     */
    @DeleteMapping("/refreshCache")
    @PreAuthorize(value = "@permissionService.hasPermission('system:config:remove')")
    @Log(module = MODULE, businessType = BusinessType.CLEAN)
    public ResultResponse refreshCache() {
        redisCacheService.refreshSystemConfig();
        return success();
    }

}
