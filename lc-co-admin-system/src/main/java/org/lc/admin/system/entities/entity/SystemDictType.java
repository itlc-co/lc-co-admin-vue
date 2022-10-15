package org.lc.admin.system.entities.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 字典类型
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 14:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemDictType extends BaseEntity {

    /**
     * 字典类型名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典模块
     */
    private String dictModule;


}
