package org.lc.admin.generator.service;

import org.lc.admin.generator.entities.entity.GenTableColumn;

import java.util.List;

/**
 * Description: 生成器表字段service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 20:58
 */
public interface GenTableColumnService {
    /**
     * Description:根据生成器表列字段插入生成器表列字段数据
     *
     * @param genTableColumn 生成器表列字段
     * @return {@link Integer } 记录数
     * @date 2022-10-13 16:49
     */
    Integer insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * Description: 根据生成器表id查询生成器表列列表数据
     *
     * @param tableId 生成器表id
     * @return {@link List }<{@link GenTableColumn }> 生成器表列列表数据
     * @date 2022-10-13 17:45
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * Description: 根据生成器表列数据更新生成器表列数据
     *
     * @param genTableColumn 生成器表列数据
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:29
     */
    Integer updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * Description: 根据生成器表主键列表删除生成器表列数据
     *
     * @param tableIds 生成器表主键列表
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:52
     */
    Integer deleteGenTableColumnByTableIds(List<Long> tableIds);

    /**
     * Description: 根据生成器表列主键列表删除生成器表列列表数据
     *
     * @param columnIds 生成器表列主键列表
     * @return {@link Integer } 记录数
     * @date 2022-10-15 13:02
     */
    Integer deleteGenTableColumnByIds(List<Long> columnIds);

    /**
     * Description: 根据生成器表主键删除生成器表列数据
     *
     * @param tableId 生成器表主键
     * @return {@link Integer } 记录数
     * @date 2022-10-15 17:18
     */
    Integer deleteGenTableColumnByTableId(Long tableId);

    /**
     * Description: 根据生成器表列列表数据插入生成器表列列表数据
     *
     * @param genTableColumns 生成器表列列表数据
     * @return {@link Integer } 记录数
     * @date 2022-10-15 17:22
     */
    Integer insertGenTableColumn(List<GenTableColumn> genTableColumns);
}
