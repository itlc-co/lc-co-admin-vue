package org.lc.admin.generator.controller;

import cn.hutool.core.io.IoUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.common.utils.ConvertUtils;
import org.lc.admin.generator.entities.entity.GenTable;
import org.lc.admin.generator.entities.request.GenTableRequest;
import org.lc.admin.generator.service.GenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 代码生成controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 20:55
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(GenController.class);

    public static final String MODULE = "gen_code";

    @Resource
    private GenService genService;

    /**
     * Description: 根据请求参数查询生成器表列表表格数据
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 生成器表列表表格数据
     * @date 2022-10-13 17:02
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('tool:gen:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(GenTableRequest requestParam) {
        List<GenTable> list = genService.selectGenTableList(requestParam);
        return getDataTable(list);
    }

    /**
     * Description: 根据表名导入表数据
     *
     * @param tableNames 表名
     * @return {@link ResultResponse } 响应结果集
     * @date 2022-10-13 16:08
     */
    @PostMapping("/importTable")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:import')")
    @Log(module = MODULE, businessType = BusinessType.IMPORT)
    public ResultResponse importTable(@RequestParam("tableNames") String tableNames) {
        genService.importTable(tableNames);
        return success();
    }

    /**
     * Description: 根据生成器表id查询生成器表数据
     *
     * @param tableId 表id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-13 18:01
     */
    @GetMapping("/{tableId}")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse genInfo(@PathVariable("tableId") Long tableId) {
        // 根据生成器表id查询生成器表列数据map
        return toResponse(genService.genInfo(tableId));
    }

    /**
     * Description: 根据生成器表id查询生成器列列表数据接口
     *
     * @param tableId 生成器表id
     * @return {@link TableData } 生成器列列表数据接口
     * @date 2022-10-13 20:19
     */
    @GetMapping(value = "/column/{tableId}")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData columnList(@PathVariable("tableId") Long tableId) {
        return TableData.success(genService.selectGenTableColumnListByTableId(tableId));
    }

    /**
     * Description: 生成代码
     *
     * @param tableName 表名
     * @param response  响应
     * @date 2022-10-15 17:05
     */
    @GetMapping("/genCode/{tableName}")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:code')")
    @Log(module = MODULE, businessType = BusinessType.GEN_CODE)
    public void genCode(@PathVariable("tableName") String tableName, HttpServletResponse response) {
        genService.generatorCode(tableName, response);
    }

    /**
     * Description: 根据请求参数编辑生成器表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-15 10:37
     */
    @PutMapping
    @PreAuthorize("@permissionService.hasPermission('tool:gen:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse edit(@RequestBody GenTableRequest requestParam) {
        return toResponse(genService.edit(requestParam));
    }

    /**
     * Description: 根据生成器表主键列表删除生成器表列表数据接口
     *
     * @param tableIds 生成器表主键
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-15 10:45
     */
    @DeleteMapping("/{tableIds}")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("tableIds") List<Long> tableIds) {
        return toResponse(genService.delete(tableIds));
    }

    /**
     * Description: 根据生成器表主键预览生成器生成的代码数据接口
     *
     * @param tableId 生成器表主键
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-15 10:58
     */
    @GetMapping("/preview/{tableId}")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:preview')")
    @Log(module = MODULE, businessType = BusinessType.GEN_CODE)
    public ResultResponse preview(@PathVariable("tableId") Long tableId) {
        return success(genService.previewCode(tableId));
    }

    /**
     * Description: 根据生成器表名下载生成的代码.zip压缩包接口
     *
     * @param tableName 生成器表名
     * @param response  响应
     * @date 2022-10-15 11:06
     */
    @GetMapping("/download/{tableName}")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:code')")
    @Log(module = MODULE, businessType = BusinessType.GEN_CODE)
    public void download(@PathVariable("tableName") String tableName, HttpServletResponse response) {
        byte[] data = genService.downloadCode(tableName);
        genCode(response, data);
    }

    /**
     * Description: 根据表名同步数据库
     *
     * @param tableName 表名
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-15 11:30
     */
    @GetMapping("/syncDataBase/{tableName}")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:edit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse syncDataBase(@PathVariable("tableName") String tableName) {
        genService.syncDataBase(tableName);
        return success();
    }

    /**
     * Description: 根据生成器表名批量生成代码接口
     *
     * @param response   响应
     * @param tableNames 生成器表名
     * @date 2022-10-15 13:05
     */
    @GetMapping("/batchGenCode")
    @PreAuthorize("@permissionService.hasPermission('tool:gen:code')")
    @Log(module = MODULE, businessType = BusinessType.GEN_CODE)
    public void batchGenCode(HttpServletResponse response, @RequestParam("tableNames") String tableNames) {
        byte[] data = genService.downloadCode(ConvertUtils.toStrList(tableNames));
        genCode(response, data);
    }


    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) {
        try {
            response.reset();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename=\"lc-co-admin-vue.zip\"");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, data);
        } catch (IOException e) {
            throw new FileException(StatusMsg.FILE_ERROR);
        }
    }

}
