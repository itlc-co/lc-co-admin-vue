package org.lc.admin.generator.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.generator.entities.entity.DataBaseTableColumn;

import java.util.List;

/**
 * Description: 数据库表列mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 21:50
 */
public interface DataBaseTableColumnMapper {
    /**
     * Description: 根据数据库表名查询数据库表列字段列表
     *
     * @param tableName 数据库表名
     * @return {@link List }<{@link DataBaseTableColumn }> 数据库表列字段列表
     * @date 2022-10-12 22:13
     */
    List<DataBaseTableColumn> selectDataBaseTableColumnList(@Param("tableName") String tableName);
}
