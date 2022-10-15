package org.lc.admin.system.entities.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 字典数据
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SystemDictData extends BaseEntity {

    /**
     * 数据id
     */
    private Integer dataId;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典数据排序
     */
    private Integer dictSort;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典模块
     */
    private String dictModule;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格字典样式
     */
    private String listClass;

    /**
     * 是否默认（true是 false否）
     */
    private Character isDefault;


}
