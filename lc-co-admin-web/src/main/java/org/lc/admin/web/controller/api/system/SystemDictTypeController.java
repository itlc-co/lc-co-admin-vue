package org.lc.admin.web.controller.api.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.system.entities.entity.SystemDictData;
import org.lc.admin.system.entities.entity.SystemDictType;
import org.lc.admin.system.entities.request.SystemDictTypeRequest;
import org.lc.admin.system.service.SystemDictTypeService;
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
 * Description: 系统字典类型controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 14:39
 */
@RequestMapping("/system/dict/type")
@RestController
@Validated
public class SystemDictTypeController extends BaseController {

    public static final String MODULE = "system_dict_type";

    public static final Logger log = LoggerFactory.getLogger(SystemDictTypeController.class);

    @Resource
    private SystemDictTypeService dictTypeService;

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 查询字典类型列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-23 13:32
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('system:dict:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(SystemDictTypeRequest requestParam) {
        return getDataTable(this.dictTypeService.selectDictTypeList(requestParam));
    }

    /**
     * Description: 查询字典类型详情数据接口
     *
     * @param dictId 字典id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:33
     */
    @GetMapping("/{dictId}")
    @PreAuthorize("@permissionService.hasPermission('system:dict:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse dictType(@PathVariable("dictId") Long dictId) {
        return toResponse(this.dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * Description: 添加字典类型数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:33
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('system:dict:add')")
    @Log(module = MODULE, businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated @RequestBody SystemDictTypeRequest requestParam) {
        Integer row = this.dictTypeService.addDictType(requestParam);
        if (row > 0) {
            // 保存字典数据缓存
            this.redisCacheService.saveDictData(BeanUtil.toBean(requestParam, SystemDictData.class));
        }
        return toResponse(row);
    }

    /**
     * Description: 编辑字典类型数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:34
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('system:dict:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated @RequestBody SystemDictTypeRequest requestParam) {
        Integer row = this.dictTypeService.editDictType(requestParam);
        if (row > 0) {
            // 保存字典数据缓存
            this.redisCacheService.saveDictData(BeanUtil.toBean(requestParam, SystemDictData.class));
        }
        return toResponse(row);
    }

    /**
     * Description: 删除字典类型列表数据接口
     *
     * @param dictIds 字典id类别
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:34
     */
    @DeleteMapping("/{dictIds}")
    @PreAuthorize("@permissionService.hasPermission('system:dict:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("dictIds") List<Long> dictIds) {
        List<SystemDictType> dictTypes = this.dictTypeService.deleteDictTypeByIds(dictIds);
        if (CollUtil.isNotEmpty(dictTypes)) {
            // 根据字典类型列表保存字典数据缓存
            this.redisCacheService.deleteDictDatasByDictTypes(dictTypes);
        }
        return success(CollUtil.isNotEmpty(dictTypes) ? dictTypes.size() : 0);
    }


    /**
     * Description: 查询字典类型选项选择树数据接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:34
     */
    @GetMapping("/optionSelect")
    @PreAuthorize("@permissionService.hasPermission('system:dict:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse optionSelect() {
        return toResponse(this.dictTypeService.selectDictTypeAll());
    }

    /**
     * Description: 导出字典类型列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-09-23 13:35
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('system:dict:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(SystemDictTypeRequest requestParam, HttpServletResponse response) {
        try {
            // 根据请求参数导出字典类型列表数据
            this.dictTypeService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }

    /**
     * Description: 刷新字典数据缓存接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-02 11:43
     */
    @DeleteMapping("/refreshCache")
    @PreAuthorize("@permissionService.hasPermission('system:dict:remove')")
    @Log(module = MODULE, businessType = BusinessType.CLEAN)
    public ResultResponse refreshCache() {
        redisCacheService.refreshDictData();
        return success();
    }


}
