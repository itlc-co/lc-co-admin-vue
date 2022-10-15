package org.lc.admin.generator.service;

import org.lc.admin.generator.entities.entity.GenTable;
import org.lc.admin.generator.entities.entity.GenTableColumn;
import org.lc.admin.generator.entities.request.GenTableRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Description: 生成器service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 22:51
 */
public interface GenService {
    /**
     * Description: 根据表名导入数据库表数据
     *
     * @param tableNames 表名字符串, 多个`,`分割
     * @date 2022-10-12 22:53
     */
    void importTable(String tableNames);

    /**
     * Description: 根据请求参数查询生成器表列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link GenTable }> 生成器表列表数据
     * @date 2022-10-13 16:08
     */
    List<GenTable> selectGenTableList(GenTableRequest requestParam);

    /**
     * Description: 根据生成器表id查询生成器表列数据map
     *
     * @param tableId 生成器表id
     * @return {@link Map }<{@link String }, {@link Object }> 生成器表列数据map
     * @date 2022-10-13 17:14
     */
    Map<String, Object> genInfo(Long tableId);

    /**
     * Description: 根据生成器表id查询生成器表列列表数据
     *
     * @param tableId 生成器表id
     * @return {@link List }<{@link GenTableColumn }> 生成器表列列表数据
     * @date 2022-10-13 20:16
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * Description: 根据表名生成代码
     *
     * @param tableName 表名
     * @param response  响应
     * @date 2022-10-15 17:06
     */
    void generatorCode(String tableName, HttpServletResponse response);

    /**
     * Description: 根据请求参数编辑生成器表&生成器表列数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:46
     */
    Integer edit(GenTableRequest requestParam);

    /**
     * Description: 根据生成器表主键列表删除生成器表&生成器表列列表数据
     *
     * @param tableIds 生成器表主键列表
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:46
     */
    Integer delete(List<Long> tableIds);

    /**
     * Description: 根据生成器表主键预览生成器生成的代码数据
     *
     * @param tableId 生成器表主键
     * @return {@link Map }<{@link String }, {@link Object }> 生成器生成的代码数据Map
     * @date 2022-10-15 10:57
     */
    Map<String, Object> previewCode(Long tableId);

    /**
     * Description: 根据生成器表名生成代码的字节数组数据
     *
     * @param tableName 生成器表名
     * @return {@link byte[] } 代码的字节数组数据
     * @date 2022-10-15 11:06
     */
    byte[] downloadCode(String tableName);

    /**
     * Description: 根据生成器表名同步数据库
     *
     * @param tableName 表名
     * @date 2022-10-15 11:29
     */
    void syncDataBase(String tableName);

    /**
     * Description: 根据生成器表名列表下载生成器代码字节数组数据
     *
     * @param tableNames 表名列表 多个`,`分割
     * @return {@link byte[] } 字节数组数据
     * @date 2022-10-15 13:08
     */
    byte[] downloadCode(List<String> tableNames);
}
