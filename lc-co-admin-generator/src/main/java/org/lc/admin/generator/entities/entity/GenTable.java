package org.lc.admin.generator.entities.entity;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ArrayUtils;
import org.lc.admin.common.base.entities.entity.BaseEntity;
import org.lc.admin.common.pool.GenConstantsPool;

import java.util.List;

/**
 * Description: 代码生成器表数据
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 20:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenTable extends BaseEntity {

    /**
     * 编号
     */
    private Long tableId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 实体类名称(首字母大写)
     */
    private String className;

    /**
     * 使用的模板（crud单表操作 tree树表操作 sub主子表操作）
     */
    private String tplCategory;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 生成模块名
     */
    private String moduleName;

    /**
     * 生成业务名
     */
    private String businessName;

    /**
     * 生成功能名
     */
    private String functionName;

    /**
     * 生成作者
     */
    private String functionAuthor;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    private Character genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    private String genPath;

    /**
     * 其他选项
     */
    private String options;

    /**
     * 主键信息
     */
    private GenTableColumn pkColumn;

    /**
     * 表列信息
     */
    private List<GenTableColumn> columns;

    /**
     * 上级菜单ID字段
     */
    private String parentMenuId;

    /**
     * 上级菜单名称字段
     */
    private String parentMenuName;

    public static boolean isSuperColumn(String tplCategory, String javaField) {
        if (isTree(tplCategory)) {
            return StrUtil.equalsAnyIgnoreCase(javaField,
                    ArrayUtils.addAll(GenConstantsPool.TREE_ENTITY, GenConstantsPool.BASE_ENTITY));
        }
        return StrUtil.equalsAnyIgnoreCase(javaField, GenConstantsPool.BASE_ENTITY);
    }

    public static boolean isTree(String tplCategory) {
        return tplCategory != null && StrUtil.equals(GenConstantsPool.TPL_TREE, tplCategory);
    }

    public boolean isSuperColumn(String javaField) {
        return isSuperColumn(this.tplCategory, javaField);
    }

    public boolean isTree() {
        return isTree(this.tplCategory);
    }

}
