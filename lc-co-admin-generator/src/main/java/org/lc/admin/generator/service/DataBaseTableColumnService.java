package org.lc.admin.generator.service;

import org.lc.admin.generator.entities.entity.DataBaseTableColumn;

import java.util.List;

/**
 * Description: 数据表列service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 21:47
 */
public interface DataBaseTableColumnService {
    /**
     * Description: 根据数据库表名查询数据库表列字段列表
     *
     * @param tableName 数据库表名
     * @return {@link List }<{@link DataBaseTableColumn }> 数据库表列字段列表
     * @date 2022-10-12 22:12
     */
    List<DataBaseTableColumn> selectDataBaseTableColumnList(String tableName);
}
