package org.lc.admin.generator.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.pool.StrConstantsPool;
import org.lc.admin.common.pool.GenConstantsPool;
import org.lc.admin.generator.config.GeneratorConfig;
import org.lc.admin.generator.entities.entity.DataBaseTable;
import org.lc.admin.generator.entities.entity.DataBaseTableColumn;
import org.lc.admin.generator.entities.entity.GenTable;
import org.lc.admin.generator.entities.entity.GenTableColumn;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 生成器工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-13 16:30
 */
public class GeneratorUtils {

    /**
     * Description: 根据数据库表初始化生成器表数据
     *
     * @param dataBaseTable 数据库表
     * @return {@link GenTable } 生成器表
     * @date 2022-10-13 16:30
     */
    public static GenTable initGenTable(DataBaseTable dataBaseTable) {
        // 数据库表名以及注释
        String tableName = dataBaseTable.getTableName();
        String tableComment = dataBaseTable.getTableComment();
        return GenTable.builder()
                // 生成器表名
                .tableName(tableName)
                // 生成器表注释
                .tableComment(tableComment)
                // 生成器表对应的类名
                .className(convertClassName(tableName))
                // 生成器表对应的包名
                .packageName(GeneratorConfig.getPackageName())
                // 模块名
                .moduleName(getModuleName())
                // 业务名
                .businessName(getBusinessName(tableName))
                // 方法名
                .functionName(getFunctionName(tableComment))
                // 方法作者
                .functionAuthor(GeneratorConfig.getAuthor())
                .build();
    }


    /**
     * Description: 获取方法名
     *
     * @param tableComment 表注释
     * @return {@link String } 方法名
     * @date 2022-10-13 16:38
     */
    private static String getFunctionName(String tableComment) {
        return ReUtil.replaceAll(tableComment, "(?:表|admin)", StrConstantsPool.EMPTY_STR);
    }

    /**
     * Description: 获取业务名称
     *
     * @param tableName 表名
     * @return {@link String } 业务名称
     * @date 2022-10-13 16:38
     */
    private static String getBusinessName(String tableName) {
        return StrUtil.subAfter(tableName, StrConstantsPool.UNDERLINE_SEPARATOR, true);
    }

    /**
     * Description: 获取模块名称
     *
     * @return {@link String } 模块名
     * @date 2022-10-13 16:37
     */
    private static String getModuleName() {
        return StrUtil.subAfter(GeneratorConfig.getPackageName(), StrConstantsPool.POINT_SEPARATOR, true);
    }

    /**
     * Description: 将表名转换为类名
     *
     * @param tableName 表名
     * @return {@link String } 类名
     * @date 2022-10-13 16:35
     */
    private static String convertClassName(String tableName) {
        // 获取是否自动移除前缀配置以及表名前缀列表
        boolean isAutoRemovePre = GeneratorConfig.isAutoRemovePre();
        List<String> tablePrefixList = GeneratorConfig.getTablePrefixList();
        if (isAutoRemovePre && CollUtil.isNotEmpty(tablePrefixList)) {
            // 替换表名前缀
            tableName = replaceTablePrefix(tableName, tablePrefixList);
        }
        // 转换为大驼峰
        return NamingCase.toPascalCase(tableName);
    }

    /**
     * Description: 替换表名前缀
     *
     * @param tableName       表名
     * @param tablePrefixList 表前缀列表
     * @return {@link String } 替换后的表
     * @date 2022-10-13 16:37
     */
    private static String replaceTablePrefix(final String tableName, List<String> tablePrefixList) {
        List<String> tablePreList = tablePrefixList.stream().filter((tablePrefix) -> StrUtil.startWithIgnoreCase(tableName, tablePrefix)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(tablePreList)) {
            return StrUtil.replace(tableName, tablePrefixList.get(0), StrConstantsPool.EMPTY_STR);
        }
        return tableName;
    }


    /**
     * Description: 根据数据库表列字段初始化生成器表列字段数据
     *
     * @param dataBaseTableColumn 数据库表列字段
     * @return {@link GenTableColumn } 生成器表列字段数据
     * @date 2022-10-13 16:39
     */
    public static GenTableColumn initGenTableColumn(DataBaseTableColumn dataBaseTableColumn) {
        String columnType = dataBaseTableColumn.getColumnType();
        String columnName = dataBaseTableColumn.getColumnName();
        return GenTableColumn.builder()
                // 列类型
                .columnType(columnType)
                // 列名
                .columnName(columnName)
                .columnComment(dataBaseTableColumn.getColumnComment())
                // 排序数
                .sort(dataBaseTableColumn.getSort())
                // 是否为主键
                .isPk(dataBaseTableColumn.getIsPrimaryKey())
                // 是否为必须
                .isRequired(dataBaseTableColumn.getIsRequired())
                // 是否自增
                .isIncrement(dataBaseTableColumn.getIsAutoIncrement())
                // java字段名
                .javaField(convertJavaField(columnName))
                // 字段别名
                .fieldAlias(getFieldAlias(dataBaseTableColumn))
                // java类型
                .javaType(getJavaType(dataBaseTableColumn))
                // 查询类型
                .queryType(getQueryType(columnName))
                // html类型
                .htmlType(getHtmlType(dataBaseTableColumn))
                // 默认所有字段都需要插入
                .isInsert(GenConstantsPool.REQUIRE)
                // 是否为编辑字段
                .isEdit(getIsEdit(dataBaseTableColumn))
                // 是否为列表字段
                .isList(getIsList(dataBaseTableColumn))
                // 是否为查询字段
                .isQuery(getIsQuery(dataBaseTableColumn))
                .build();
    }

    private static String getFieldAlias(DataBaseTableColumn dataBaseTableColumn) {
        return StrUtil.upperFirst(dataBaseTableColumn.isPrimaryKey() ? StrUtil.subAfter(dataBaseTableColumn.getColumnName(), StrConstantsPool.UNDERLINE_SEPARATOR, true) : StrUtil.toCamelCase(dataBaseTableColumn.getColumnName()));
    }

    /**
     * Description: 根据数据库表列获取是否为查询字段
     *
     * @param dataBaseTableColumn 数据库表列
     * @return {@link Character } '1':是 '0':否
     * @date 2022-10-13 16:46
     */
    private static Character getIsQuery(DataBaseTableColumn dataBaseTableColumn) {
        return !StrUtil.containsAnyIgnoreCase(dataBaseTableColumn.getColumnName(), GenConstantsPool.COLUMN_NOT_QUERY) && !dataBaseTableColumn.isPrimaryKey() ? '1' : '0';
    }

    /**
     * Description: 根据数据库表列获取是否为列表字段
     *
     * @param dataBaseTableColumn 数据库表列
     * @return {@link Character } '1':是 '0':否
     * @date 2022-10-13 16:46
     */
    private static Character getIsList(DataBaseTableColumn dataBaseTableColumn) {
        return !StrUtil.containsAnyIgnoreCase(dataBaseTableColumn.getColumnName(), GenConstantsPool.COLUMN_NOT_LIST) && !dataBaseTableColumn.isPrimaryKey() ? '1' : '0';
    }

    /**
     * Description: 根据数据库表列获取是否为编辑字段
     *
     * @param dataBaseTableColumn 数据库表列
     * @return {@link Character } '1':是 '0':否
     * @date 2022-10-13 16:45
     */
    private static Character getIsEdit(DataBaseTableColumn dataBaseTableColumn) {
        return !StrUtil.containsAnyIgnoreCase(dataBaseTableColumn.getColumnName(), GenConstantsPool.COLUMN_NOT_EDIT) && !dataBaseTableColumn.isPrimaryKey() ? '1' : '0';
    }

    /**
     * Description: 根据列名获取查询类型
     *
     * @param columnName 列名
     * @return {@link String } 查询类型
     * @date 2022-10-13 16:44
     */
    private static String getQueryType(String columnName) {
        // 非名称字段相等查询，名称字段模糊查询
        return StrUtil.endWithAnyIgnoreCase(columnName, "name") ? GenConstantsPool.QUERY_TYPE_LIKE : GenConstantsPool.DEFAULT_QUERY_TYPE;
    }

    /**
     * Description: 根据数据库表列数据获取html类型
     *
     * @param dataBaseTableColumn 数据库表列
     * @return {@link String } html类型
     * @date 2022-10-13 16:44
     */
    private static String getHtmlType(DataBaseTableColumn dataBaseTableColumn) {
        // 列名与列类型
        String columnType = dataBaseTableColumn.getColumnType();
        String columnName = dataBaseTableColumn.getColumnName();
        // 默认输入框类型
        String htmlType = GenConstantsPool.HTML_TYPE_INPUT;
        if (StrUtil.containsAnyIgnoreCase(columnType, GenConstantsPool.COLUMN_TYPE_STR)) {
            if (getColumnLength(columnType) >= 500) {
                // 字符串长度超过500设置为文本域类型
                htmlType = GenConstantsPool.HTML_TYPE_TEXTAREAT;
            }
        } else if (StrUtil.containsAnyIgnoreCase(columnType, GenConstantsPool.COLUMN_TYPE_TEXT)) {
            // text类型设置为文本域类型
            htmlType = GenConstantsPool.HTML_TYPE_TEXTAREAT;
        } else if (StrUtil.containsAnyIgnoreCase(columnType, GenConstantsPool.COLUMN_TYPE_ALL_TIME)) {
            // 时间类型设置时间控件
            htmlType = GenConstantsPool.HTML_TYPE_DATE_TIME;
        }
        if (StrUtil.endWithAnyIgnoreCase(columnName, "status")) {
            // 状态字段设置单选框
            htmlType = GenConstantsPool.HTML_TYPE_RADIO;
        } else if (StrUtil.endWithAnyIgnoreCase(columnName, "type", "sex")) {
            // 类型&性别字段设置下拉框
            htmlType = GenConstantsPool.HTML_TYPE_SELECT;
        } else if (StrUtil.endWithAnyIgnoreCase(columnName, "image")) {
            // 图片字段设置图片上传控件
            htmlType = GenConstantsPool.HTML_TYPE_IMAGE_UPLOAD;
        } else if (StrUtil.endWithAnyIgnoreCase(columnName, "file")) {
            // 文件字段设置文件上传控件
            htmlType = GenConstantsPool.HTML_TYPE_FILE_UPLOAD;
        } else if (StrUtil.endWithAnyIgnoreCase(columnName, "content")) {
            // 内容字段设置富文本控件
            htmlType = GenConstantsPool.HTML_TYPE_EDITOR;
        }
        return htmlType;
    }

    /**
     * Description: 根据列类型获取列长度
     *
     * @param columnType 列类型
     * @return {@link Integer } 列长度
     * @date 2022-10-13 16:46
     */
    private static Integer getColumnLength(String columnType) {
        Integer length = 0;
        if (StrUtil.contains(columnType, StrConstantsPool.PARENTHESES_LEFT)) {
            length = Integer.parseInt(StrUtil.subBetween(columnType, StrConstantsPool.PARENTHESES_LEFT, StrConstantsPool.PARENTHESES_RIGHT));
        }
        return length;
    }

    /**
     * Description: 根据数据库列获取java字段类型
     *
     * @param dataBaseTableColumn 数据库列
     * @return {@link String } java字段类型
     * @date 2022-10-13 16:42
     */
    private static String getJavaType(DataBaseTableColumn dataBaseTableColumn) {
        String columnType = dataBaseTableColumn.getColumnType();
        String dataType = getColumnType(columnType);
        // 默认String类型
        String javaType = GenConstantsPool.DEFAULT_JAVA_TYPE;
        if (StrUtil.containsAnyIgnoreCase(dataType, GenConstantsPool.COLUMN_TYPE_STR)) {
            // 字符串型，统一用String
            javaType = GenConstantsPool.JAVA_TYPE_STRING;
        } else if (StrUtil.containsAnyIgnoreCase(dataType, GenConstantsPool.COLUMN_TYPE_TEXT)) {
            // 长文本型，统一用String
            javaType = GenConstantsPool.JAVA_TYPE_STRING;
        } else if (StrUtil.containsAnyIgnoreCase(dataType, GenConstantsPool.COLUMN_TYPE_NUMBER)) {
            List<String> strList = StrUtil.split(StrUtil.subBetween(columnType, StrConstantsPool.PARENTHESES_LEFT, StrConstantsPool.PARENTHESES_RIGHT), StrConstantsPool.COMMA_SEPARATOR);
            if (CollUtil.isNotEmpty(strList)) {
                if (strList.size() == 2 && Integer.parseInt(strList.get(0)) > 0) {
                    // 浮点型，统一用BigDecimal
                    javaType = GenConstantsPool.JAVA_TYPE_BIG_DECIMAL;
                } else if (strList.size() == 1 && Integer.parseInt(strList.get(0)) <= 10) {
                    // 整形型，统一用Integer
                    javaType = GenConstantsPool.JAVA_TYPE_INTEGER;
                } else {
                    // 长整形，统一用Long
                    javaType = GenConstantsPool.JAVA_TYPE_LONG;
                }
            } else {
                // 长整形，统一用Long
                javaType = GenConstantsPool.JAVA_TYPE_LONG;
            }
        } else if (StrUtil.containsAnyIgnoreCase(columnType, GenConstantsPool.COLUMN_TYPE_DATE_TIME)) {
            // 日期时间类型，统一用LocalDateTime
            javaType = GenConstantsPool.JAVA_TYPE_LOCAL_DATE_TIME;
        } else if (StrUtil.containsAnyIgnoreCase(columnType, GenConstantsPool.COLUMN_TYPE_DATE)) {
            // 日期类型，统一用LocalDate
            javaType = GenConstantsPool.JAVA_TYPE_LOCAL_DATE;
        } else if (StrUtil.containsAnyIgnoreCase(columnType, GenConstantsPool.COLUMN_TYPE_TIME)) {
            // 时间类型，统一用LocalTime
            javaType = GenConstantsPool.JAVA_TYPE_LOCAL_TIME;
        } else if (StrUtil.containsAnyIgnoreCase(columnType, GenConstantsPool.COLUMN_TYPE_BLOB)) {
            // 二进制数据类型，统一用byte[]
            javaType = GenConstantsPool.JAVA_TYPE_BYTE_ARRAY;
        }
        return javaType;
    }

    /**
     * Description: 根据列名转换为java字段名
     *
     * @param columnName 列名
     * @return {@link String } java字段名
     * @date 2022-10-13 16:40
     */
    private static String convertJavaField(String columnName) {
        // 转换为驼峰命名
        return StrUtil.toCamelCase(columnName);
    }

    /**
     * Description: 根据列类型获取生成器列类型
     *
     * @param columnType 列类型
     * @return {@link String } 生成器列类型
     * @date 2022-10-13 16:47
     */
    private static String getColumnType(String columnType) {
        return StrUtil.containsIgnoreCase(columnType, StrConstantsPool.PARENTHESES_LEFT) ? StrUtil.subBefore(columnType, StrConstantsPool.PARENTHESES_LEFT, true) : columnType;
    }
}
