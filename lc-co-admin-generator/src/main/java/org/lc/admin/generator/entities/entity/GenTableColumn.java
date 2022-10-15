package org.lc.admin.generator.entities.entity;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;
import org.lc.admin.common.pool.GenConstantsPool;

/**
 * Description: 代码生成器表列
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 20:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class GenTableColumn extends BaseEntity {

    /**
     * 编号
     */
    private Long columnId;

    /**
     * 归属表编号
     */
    private Long tableId;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * 是否主键（1是）
     */
    private Character isPk;

    /**
     * 是否自增（1是）
     */
    private Character isIncrement;

    /**
     * 是否必填（1是）
     */
    private Character isRequired;

    /**
     * 是否为插入字段（1是）
     */
    private Character isInsert;

    /**
     * 是否编辑字段（1是）
     */
    private Character isEdit;

    /**
     * 是否列表字段（1是）
     */
    private Character isList;

    /**
     * 是否查询字段（1是）
     */
    private Character isQuery;

    /**
     * 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
     */
    private String queryType;

    /**
     * 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）
     */
    private String htmlType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 字段别名
     */
    private String fieldAlias;

    /**
     * jdbc类型
     */
    private String jdbcType;

    public static boolean isList(Character isList) {
        return ObjectUtil.isNotNull(isList) && CharUtil.equals(isList, GenConstantsPool.REQUIRE, true);
    }

    public static boolean isEdit(Character isEdit) {
        return ObjectUtil.isNotNull(isEdit) && CharUtil.equals(isEdit, GenConstantsPool.REQUIRE, true);
    }

    public static boolean isInsert(Character isInsert) {
        return ObjectUtil.isNotNull(isInsert) && CharUtil.equals(isInsert, GenConstantsPool.REQUIRE, true);
    }

    public static boolean isRequired(Character isRequired) {
        return ObjectUtil.isNotNull(isRequired) && CharUtil.equals(isRequired, GenConstantsPool.REQUIRE, true);
    }

    public static boolean isIncrement(Character isIncrement) {
        return ObjectUtil.isNotNull(isIncrement) && CharUtil.equals(isIncrement, GenConstantsPool.REQUIRE, true);
    }

    public static boolean isPk(Character isPk) {
        return ObjectUtil.isNotNull(isPk) && CharUtil.equals(isPk, GenConstantsPool.REQUIRE, true);
    }

    public boolean isPk() {
        return isPk(isPk);
    }

    public boolean isIncrement() {
        return isIncrement(isIncrement);
    }

    public boolean isRequired() {
        return isRequired(isRequired);
    }

    public boolean isInsert() {
        return isInsert(isInsert);
    }

    public boolean isEdit() {
        return isEdit(isEdit);
    }

    public boolean isList() {
        return isList(isList);
    }


}
