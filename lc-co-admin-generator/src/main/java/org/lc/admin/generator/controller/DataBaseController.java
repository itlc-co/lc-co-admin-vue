package org.lc.admin.generator.controller;


import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.generator.entities.request.DataBaseTableRequest;
import org.lc.admin.generator.service.DataBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 数据库controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 21:13
 */
@RestController
@RequestMapping("/tool/db")
public class DataBaseController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(DataBaseController.class);

    public static final String MODULE = "tool_db";

    @Resource
    private DataBaseService dataBaseService;

    /**
     * Description: 根据请求参数查询数据库表列表表格数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 数据库表列表表格数据接口
     * @date 2022-10-12 21:45
     */
    @GetMapping("/list")
    @Pagination
    public TableData list(DataBaseTableRequest requestParam) {
        return getDataTable(dataBaseService.selectDataBaseTableList(requestParam));
    }

    /**
     * Description: 根据数据库表名查询数据库表列字段列表表格数据
     *
     * @param tableName 数据库表名
     * @return {@link TableData } 数据库表列字段列表表格数据
     * @date 2022-10-12 22:07
     */
    @GetMapping(value = "/column/{tableName}")
    public TableData columnList(@PathVariable("tableName") String tableName) {
        return TableData.success(dataBaseService.selectDataBaseTableColumnList(tableName));
    }

    /**
     * Description: 根据数据库表名列表查询数据库表列表表格数据
     *
     * @param tableNames 表名
     * @return {@link TableData } 数据库表列表表格数据
     * @date 2022-10-12 22:39
     */
    @GetMapping("/{tableNames}")
    public TableData list(@PathVariable("tableNames") List<String> tableNames) {
        return getDataTable(dataBaseService.selectDataBaseTableListByTableNames(tableNames));
    }

}
