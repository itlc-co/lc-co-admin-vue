package org.lc.admin.generator.service;

import org.lc.admin.generator.entities.entity.DataBaseTable;
import org.lc.admin.generator.entities.entity.GenTable;
import org.lc.admin.generator.entities.request.GenTableRequest;

import java.util.List;

/**
 * Description: 生成器表service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 20:58
 */
public interface GenTableService {

    /**
     * Description: 根据数据库表列表导入数据库表数据
     *
     * @param tableList 数据库表列表
     * @date 2022-10-12 22:56
     */
    void importTable(List<DataBaseTable> tableList);

    /**
     * Description: 根据请求参数查询生成器表列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link GenTable }> 生成器表列表数据
     * @date 2022-10-13 16:09
     */
    List<GenTable> selectGenTableList(GenTableRequest requestParam);

    /**
     * Description: 根据生成器表插入生成器表数据
     *
     * @param genTable 生成器表
     * @return {@link Integer } 记录数
     * @date 2022-10-13 16:10
     */
    Integer insertGenTable(GenTable genTable);

    /**
     * Description: 根据生成器表id查询生成器表数据
     *
     * @param tableId 生成器表id
     * @return {@link GenTable } 生成器表数据
     * @date 2022-10-13 17:17
     */
    GenTable selectGenTableById(Long tableId);

    /**
     * Description: 查询所有生成器表列表数据
     *
     * @return {@link List }<{@link GenTable }> 生成器表列表数据
     * @date 2022-10-13 17:36
     */
    List<GenTable> selectGenTableAll();

    /**
     * Description: 根据生成器表名查询生成器表数据
     *
     * @param tableName 生成器表名
     * @return {@link GenTable } 生成器表数据
     * @date 2022-10-13 21:19
     */
    GenTable selectGenTableByName(String tableName);

    /**
     * Description: 根据请求参数编辑生成器表数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:20
     */
    Integer edit(GenTableRequest requestParam);

    /**
     * Description: 根据请求参数更新生成器表数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:23
     */
    Integer updateGenTable(GenTableRequest requestParam);

    /**
     * Description: 根据生成器表数据更新生成器表数据
     *
     * @param genTable 生成器表数据
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:24
     */
    Integer updateGenTable(GenTable genTable);

    /**
     * Description: 根据生成器表主键列表删除生成器表列表数据
     *
     * @param tableIds 生成器表主键
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:48
     */
    Integer deleteGenTableByIds(List<Long> tableIds);
}
