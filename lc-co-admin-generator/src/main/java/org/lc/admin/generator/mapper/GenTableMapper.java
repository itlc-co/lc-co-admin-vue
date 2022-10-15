package org.lc.admin.generator.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.generator.entities.entity.GenTable;
import org.lc.admin.generator.entities.request.GenTableRequest;

import java.util.List;

/**
 * Description: 生成器表mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 20:57
 */
public interface GenTableMapper {
    /**
     * Description: 根据生成器表插入生成器表数据
     *
     * @param genTable 生成器表
     * @return {@link Integer } 记录数
     * @date 2022-10-13 16:10
     */
    Integer insertGenTable(GenTable genTable);

    /**
     * Description: 根据请求参数查询生成器表列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link GenTable }> 生成器表列表数据
     * @date 2022-10-13 16:09
     */
    List<GenTable> selectGenTableList(GenTableRequest requestParam);

    /**
     * Description: 根据生成器表id查询生成器表数据
     *
     * @param tableId 生成器表id
     * @return {@link GenTable } 生成器表数据
     * @date 2022-10-13 17:18
     */
    GenTable selectGenTableById(@Param("tableId") Long tableId);

    /**
     * Description: 查询所有生成器表列表数据
     *
     * @return {@link List }<{@link GenTable }> 生成器表列表数据
     * @date 2022-10-13 17:37
     */
    List<GenTable> selectGenTableAll();

    /**
     * Description: 根据生成器表名查询生成器表数据
     *
     * @param tableName 生成器表名
     * @return {@link GenTable } 生成器表数据
     * @date 2022-10-13 21:20
     */
    GenTable selectGenTableByName(@Param("tableName") String tableName);

    /**
     * Description: 根据生成器表数据更新生成器表数据
     *
     * @param genTable 生成器表数据
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:25
     */
    Integer updateGenTable(GenTable genTable);

    /**
     * Description: 根据生成器表主键列表删除生成器表列表数据
     *
     * @param tableIds 生成器表主键
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:51
     */
    Integer deleteGenTableByIds(@Param("tableIds") List<Long> tableIds);
}
