package org.lc.admin.generator.entities.entity;

import cn.hutool.core.util.CharUtil;
import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 数据库表列
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 21:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class DataBaseTableColumn extends BaseEntity {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 是否为必须列
     */
    private Character isRequired;

    /**
     * 是否主键列
     */
    private Character isPrimaryKey;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 列注释
     */
    private String columnComment;

    /**
     * 是否自增
     */
    private Character isAutoIncrement;

    /**
     * 列类型
     */
    private String columnType;

    public static boolean isRequired(Character isRequired) {
        return !CharUtil.isBlankChar(isRequired) && CharUtil.equals(isRequired, '1', true);
    }

    public static boolean isPrimaryKey(Character isPrimaryKey) {
        return !CharUtil.isBlankChar(isPrimaryKey) && CharUtil.equals(isPrimaryKey, '1', true);
    }

    public static boolean isAutoIncrement(Character isAutoIncrement) {
        return !CharUtil.isBlankChar(isAutoIncrement) && CharUtil.equals(isAutoIncrement, '1', true);
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement(isAutoIncrement);
    }

    public boolean isRequired() {
        return isRequired(isRequired);
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey(isPrimaryKey);
    }
}
