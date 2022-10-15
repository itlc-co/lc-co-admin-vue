package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Description: 配置请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfigRequest extends BaseRequest {

    /**
     * 配置id
     */
    private Long configId;

    /**
     * 参数键值
     */
    @NotBlank(message = "配置键值不能为空")
    @Size(max = 500, message = "配置键值长度不能超过500个字符")
    private String configValue;

    /**
     * 配置模块
     */
    @NotBlank(message = "配置模块不能为空")
    @Size(max = 50, message = "配置模块长度不能超过50个字符")
    private String configModule;

    /**
     * 配置名称
     */
    @NotBlank(message = "配置名称不能为空")
    @Size(max = 100, message = "配置名称不能超过100个字符")
    private String configName;

    /**
     * 配置键
     */
    @NotBlank(message = "配置键名长度不能为空")
    @Size(min = 0, max = 100, message = "配置键名长度不能超过100个字符")
    private String configKey;

    /**
     * 是否系统内置（Y是 N否）
     */
    private Character isBuiltin;

}
