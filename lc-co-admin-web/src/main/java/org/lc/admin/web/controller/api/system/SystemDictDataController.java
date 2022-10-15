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
import org.lc.admin.system.entities.request.SystemDictDataRequest;
import org.lc.admin.system.service.SystemDictDataService;
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
 * Description: 系统字典数据controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 20:32
 */
@RestController
@RequestMapping("/system/dict/data")
@Validated
public class SystemDictDataController extends BaseController {

    public static final String MODULE = "system_dict_data";

    public static final Logger log = LoggerFactory.getLogger(SystemDictDataController.class);

    @Resource
    private SystemDictDataService dictDataService;

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 查询字典值数据接口
     *
     * @param dictType 字典类型
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-11 20:37
     */
    @GetMapping("/type/{dictType}")
    @PreAuthorize("@permissionService.hasPermission('system:dict:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse dictType(@PathVariable("dictType") String dictType) {
        List<SystemDictData> data = this.redisCacheService.getDictDataListByDictType(dictType);
        if (CollUtil.isEmpty(data)) {
            // 保存字典数据缓存
            data = this.dictDataService.selectDictDataByDictType(dictType);
            this.redisCacheService.saveDictDataList(data);
        }
        return success(data);
    }

    /**
     * Description: 查询字典值列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-23 13:36
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('system:dict:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(SystemDictDataRequest requestParam) {
        return getDataTable(this.dictDataService.selectDictDataList(requestParam));
    }

    /**
     * Description: 查询字典值详情数据接口
     *
     * @param dataId 数据id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:37
     */
    @GetMapping(value = "/{dataId}")
    @PreAuthorize("@permissionService.hasPermission('system:dict:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse dictData(@PathVariable("dataId") Long dataId) {
        return toResponse(this.dictDataService.selectDictDataByDataId(dataId));
    }

    /**
     * Description: 添加字典值数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:38
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('system:dict:add')")
    @Log(module = MODULE, businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated @RequestBody SystemDictDataRequest requestParam) {
        Integer row = this.dictDataService.addDictData(requestParam);
        if (row > 0) {
            // 保存字典数据缓存
            this.redisCacheService.saveDictData(BeanUtil.toBean(requestParam, SystemDictData.class));
        }
        return toResponse(row);
    }

    /**
     * Description: 编辑字典值数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:38
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('system:dict:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated @RequestBody SystemDictDataRequest requestParam) {
        Integer row = this.dictDataService.editDictData(requestParam);
        if (row > 0) {
            // 保存字典数据缓存
            this.redisCacheService.saveDictData(BeanUtil.toBean(requestParam, SystemDictData.class));
        }
        return toResponse(row);
    }

    /**
     * Description: 删除字典值列表数据接口
     *
     * @param dataIds 数据id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 13:38
     */
    @DeleteMapping("/{dataId}")
    @PreAuthorize("@permissionService.hasPermission('system:dict:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("dataId") List<Long> dataIds) {
        List<SystemDictData> dictDatas = this.dictDataService.deleteDictDataByDataIds(dataIds);
        if (CollUtil.isNotEmpty(dictDatas)) {
            // 保存字典数据缓存
            this.redisCacheService.saveDictDataListByType(dictDatas);
        }
        return success(CollUtil.isNotEmpty(dictDatas) ? dictDatas.size() : 0);
    }

    /**
     * Description: 导出字典数据列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-09-23 13:35
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('system:dict:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(SystemDictDataRequest requestParam, HttpServletResponse response) {
        try {
            // 根据请求参数导出字典数据列表数据
            this.dictDataService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }


}
