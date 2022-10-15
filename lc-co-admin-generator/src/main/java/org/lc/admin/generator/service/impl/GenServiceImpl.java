package org.lc.admin.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.pool.CharacterSet;
import org.lc.admin.common.exec.GeneratorException;
import org.lc.admin.common.utils.ConvertUtils;
import org.lc.admin.generator.entities.entity.DataBaseTable;
import org.lc.admin.generator.entities.entity.DataBaseTableColumn;
import org.lc.admin.generator.entities.entity.GenTable;
import org.lc.admin.generator.entities.entity.GenTableColumn;
import org.lc.admin.generator.entities.request.GenTableRequest;
import org.lc.admin.generator.service.*;
import org.lc.admin.generator.utils.GeneratorUtils;
import org.lc.admin.generator.velocity.VelocityInitializer;
import org.lc.admin.generator.velocity.VelocityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

/**
 * Description: 生成器service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 22:52
 */
@Service
public class GenServiceImpl implements GenService {

    @Resource
    private GenTableService genTableService;

    @Resource
    private DataBaseTableService dataBaseTableService;

    @Resource
    private DataBaseTableColumnService dataBaseTableColumnService;

    @Resource
    private GenTableColumnService genTableColumnService;


    /**
     * Description: 根据表名导入数据库表数据
     *
     * @param tableNames 表名字符串, 多个`,`分割
     * @date 2022-10-12 22:55
     */
    @Override
    public void importTable(String tableNames) {
        List<DataBaseTable> dataBaseTables = dataBaseTableService.selectDataBaseTableListByTableNames(ConvertUtils.toStrList(tableNames));
        genTableService.importTable(dataBaseTables);
    }

    /**
     * Description: 根据请求参数查询生成器表列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link GenTable }> 生成器表列表数据
     * @date 2022-10-13 16:09
     */
    @Override
    public List<GenTable> selectGenTableList(GenTableRequest requestParam) {
        return genTableService.selectGenTableList(requestParam);
    }

    /**
     * Description: 根据生成器表id查询生成器表列数据map
     *
     * @param tableId 生成器表id
     * @return {@link Map }<{@link String }, {@link Object }> 生成器表列数据map
     * @date 2022-10-13 17:15
     */
    @Override
    public Map<String, Object> genInfo(Long tableId) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        GenTable genTable = genTableService.selectGenTableById(tableId);
        if (ObjectUtil.isNotNull(genTable)) {
            // put生成器表数据
            map.put("info" , genTable);
        }
        List<GenTable> genTables = genTableService.selectGenTableAll();
        if (CollUtil.isNotEmpty(genTables)) {
            // put所有生成器表
            map.put("tables" , genTables);
        }
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        if (CollUtil.isNotEmpty(list)) {
            // put生成器表id对于的生成器表列数据
            map.put("rows" , list);
        }
        return map;
    }

    /**
     * Description: 根据生成器表id查询生成器表列列表数据
     *
     * @param tableId 生成器表id
     * @return {@link List }<{@link GenTableColumn }> 生成器表列列表数据
     * @date 2022-10-13 20:18
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
        return genTableColumnService.selectGenTableColumnListByTableId(tableId);
    }

    /**
     * Description: 根据表名生成代码
     *
     * @param tableName 表名
     * @param response  响应
     * @date 2022-10-15 13:11
     */
    @Override
    public void generatorCode(String tableName, HttpServletResponse response) {
        // 查询表信息
        GenTable genTable = genTableService.selectGenTableByName(tableName);
        // 设置主键列数据
        setPkColumn(genTable);
        // Velocity引擎初始化
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(genTable);

        List<String> templates = VelocityUtils.getTemplateList(genTable.getTplCategory());
        for (String template : templates) {
            if (!StrUtil.containsAny(template, "sql.vm" , "api.js.vm" , "index.vue.vm" , "index-tree.vue.vm")) {
                // 渲染模板
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, CharacterSet.UTF_8);
                tpl.merge(context, sw);
                // 生成器生成代码路径
                String path = getGenPath(genTable, template);
                File file = getGenPathFile(path);
                // 写出数据
                FileUtil.writeString(sw.toString(), file, StandardCharsets.UTF_8);
            }
        }
    }

    /**
     * Description: 根据表名生成代码至输出流
     *
     * @param tableName    表名
     * @param outputStream 输出流
     * @date 2022-10-15 13:12
     */
    private void generatorCode(String tableName, OutputStream outputStream) {
        // 查询表信息
        GenTable genTable = genTableService.selectGenTableByName(tableName);
        // 设置主键列数据
        setPkColumn(genTable);
        // Velocity引擎初始化
        VelocityInitializer.initVelocity();
        // 配置上下文环境变量参数
        VelocityContext context = VelocityUtils.prepareContext(genTable);
        // 文件名列表
        List<String> fileNames = ListUtil.list(false);
        // 输入流列表
        List<InputStream> ins = ListUtil.list(false);
        // 模板列表
        List<String> templates = VelocityUtils.getTemplateList(genTable.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharacterSet.UTF_8);
            tpl.merge(context, sw);
            String fileName = VelocityUtils.getFileName(template, genTable);
            // 转换输入流
            ByteArrayInputStream inputStream = IoUtil.toStream(sw.toString(), StandardCharsets.UTF_8);
            // 添加文件名以及输入流
            fileNames.add(fileName);
            ins.add(inputStream);
        }
        // zip压缩
        ZipUtil.zip(outputStream, fileNames.toArray(new String[0]), ins.toArray(new InputStream[0]));
    }

    /**
     * Description: 根据请求参数编辑生成器表&生成器表列数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:36
     */
    @Override
    public Integer edit(GenTableRequest requestParam) {
        return genTableService.edit(requestParam);
    }

    /**
     * Description: 根据生成器表主键列表删除生成器表&生成器表列列表数据
     *
     * @param tableIds 生成器表主键列表
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:48
     */
    @Override
    public Integer delete(List<Long> tableIds) {
        // 根据生成器表主键列表删除生成器表数据
        Integer row = genTableService.deleteGenTableByIds(tableIds);
        if (ObjectUtil.isNotNull(row) && row > 0) {
            // 根据生成器表主键列表删除生成器表列数据
            genTableColumnService.deleteGenTableColumnByTableIds(tableIds);
        }
        return row;
    }

    /**
     * Description: 根据生成器表主键预览生成器生成的代码数据
     *
     * @param tableId 生成器表主键
     * @return {@link Map }<{@link String }, {@link Object }> 生成器生成的代码数据Map
     * @date 2022-10-15 10:58
     */
    @Override
    public Map<String, Object> previewCode(Long tableId) {
        Map<String, Object> dataMap = MapUtil.newHashMap(10);
        // 查询表信息
        GenTable genTable = genTableService.selectGenTableById(tableId);
        // 设置主键列信息
        setPkColumn(genTable);
        // Velocity模板引擎初始化
        VelocityInitializer.initVelocity();
        // 配置上下文环境变量参数
        VelocityContext context = VelocityUtils.prepareContext(genTable);
        // 根据生成器表模板类型获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(genTable.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharacterSet.UTF_8);
            tpl.merge(context, sw);
            // put生成后的数据
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    /**
     * Description: 根据生成器表名生成代码的字节数组数据
     *
     * @param tableName 生成器表名
     * @return {@link byte[] } 代码的字节数组数据
     * @date 2022-10-15 11:09
     */
    @Override
    public byte[] downloadCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    /**
     * Description: 根据生成器表名同步数据库
     *
     * @param tableName 表名
     * @date 2022-10-15 11:30
     */
    @Override
    public void syncDataBase(String tableName) {
        GenTable genTable = genTableService.selectGenTableByName(tableName);
        if (ObjectUtil.isNull(genTable)) {
            // 未导入数据异常
            throw new GeneratorException(StatusMsg.GENERATOR_NOT_IMPORT_ERROR);
        }
        Long tableId = genTable.getTableId();
        List<DataBaseTableColumn> dataBaseTableColumns = dataBaseTableColumnService.selectDataBaseTableColumnList(tableName);
        if (CollUtil.isEmpty(dataBaseTableColumns)) {
            // 不存在表结构异常
            throw new GeneratorException(StatusMsg.GENERATOR_NOT_TABLE_ERROR);
        }
        // 删除原生成器表列数据
        Integer row = deleteGenTableColumnByTableId(tableId);
        if (ObjectUtil.isNotNull(row) && row >= 0) {
            // 插入新生成器表列数据保存与数据库中的表列数据一致
            insertGenTableColumn(dataBaseTableColumns, tableId);
        }
    }

    /**
     * Description: 根据数据库表列列表数据插入生成器表列列表数据
     *
     * @param dataBaseTableColumns 数据库表列列表
     * @return {@link Integer } 记录数
     * @date 2022-10-15 17:21
     */
    private Integer insertGenTableColumn(List<DataBaseTableColumn> dataBaseTableColumns, Long tableId) {
        List<GenTableColumn> genTableColumns = dataBaseTableColumns.stream().map(GeneratorUtils::initGenTableColumn).map((genTableColumn) -> genTableColumn.setTableId(tableId)).collect(Collectors.toList());
        return genTableColumnService.insertGenTableColumn(genTableColumns);
    }

    /**
     * Description: 根据生成器表主键删除生成器表列数据
     *
     * @param tableId 生成器表主键
     * @return {@link Integer } 记录数
     * @date 2022-10-15 17:21
     */
    private Integer deleteGenTableColumnByTableId(Long tableId) {
        return genTableColumnService.deleteGenTableColumnByTableId(tableId);
    }

    /**
     * Description: 根据生成器表名列表下载生成器代码字节数组数据
     *
     * @param tableNames 表名列表 多个`,`分割
     * @return {@link byte[] } 字节数组数据
     * @date 2022-10-15 13:09
     */
    @Override
    public byte[] downloadCode(List<String> tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            generatorCode(tableName, zip);
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    /**
     * Description: 得到生成器代码路径文件
     *
     * @param path 路径
     * @return {@link File } file 文件 父级目录不存在则创建
     * @date 2022-10-15 13:10
     */
    private File getGenPathFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.mkdir();
            }
        }
        return file;
    }

    /**
     * Description: 得到生成器代码路径
     *
     * @param genTable 生成器表
     * @param template 模板
     * @return {@link String } 生成器代码路径
     * @date 2022-10-15 13:10
     */
    private String getGenPath(GenTable genTable, String template) {
        String genPath = genTable.getGenPath();
        String path = genPath + File.separator + VelocityUtils.getFileName(template, genTable);
        if (StrUtil.equals(genPath, "/")) {
            path = System.getProperty("user.dir") + "/lc-co-admin-temp" + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, genTable);
        }
        return path;
    }


    /**
     * Description: 设置生成器表主键列
     *
     * @param table 表格
     * @date 2022-10-15 13:11
     */
    public void setPkColumn(GenTable table) {
        for (GenTableColumn column : table.getColumns()) {
            if (column.isPk()) {
                table.setPkColumn(column);
                break;
            }
        }
        if (ObjectUtil.isNull(table.getPkColumn())) {
            table.setPkColumn(table.getColumns().get(0));
        }
    }

}
