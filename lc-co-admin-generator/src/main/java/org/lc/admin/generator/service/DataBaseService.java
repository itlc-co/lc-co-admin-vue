package org.lc.admin.generator.service;

import org.lc.admin.generator.entities.entity.DataBaseTable;
import org.lc.admin.generator.entities.entity.DataBaseTableColumn;
import org.lc.admin.generator.entities.request.DataBaseTableRequest;

import java.util.List;

/**
 * Description: 数据库service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 21:21
 */
public interface DataBaseService {
    /**
     * Description: 根据请求参数查询数据库表列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link DataBaseTable }> 数据库表列表数据
     * @date 2022-10-12 21:45
     */
    List<DataBaseTable> selectDataBaseTableList(DataBaseTableRequest requestParam);

    /**
     * Description: 根据数据库表名查询数据库表列字段列表
     *
     * @param tableName 数据库表名
     * @return {@link List }<{@link DataBaseTableColumn }> 数据库表列字段列表
     * @date 2022-10-12 22:07
     */
    List<DataBaseTableColumn> selectDataBaseTableColumnList(String tableName);

    /**
     * Description: 根据数据库表名列表查询数据库表列表数据
     *
     * @param tableNames 表名
     * @return {@link List }<{@link DataBaseTable }> 数据库表列表数据
     * @date 2022-10-12 22:40
     */
    List<DataBaseTable> selectDataBaseTableListByTableNames(List<String> tableNames);
}
