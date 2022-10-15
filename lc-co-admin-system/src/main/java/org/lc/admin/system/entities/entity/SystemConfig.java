package org.lc.admin.system.entities.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 配置
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SystemConfig extends BaseEntity {


    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 配置模块
     */
    private String configModule;

    /**
     * 是否系统内置（true是 false否）
     */
    private Character isBuiltin;

}
