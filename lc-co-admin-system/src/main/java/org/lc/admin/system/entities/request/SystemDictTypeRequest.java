package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;
import org.lc.admin.common.pool.RePatternConstantsPool;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Description: 字典类型请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 14:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemDictTypeRequest extends BaseRequest {

    /**
     * 字典 id
     */
    private Long dictId;

    /**
     * 字典类型名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 100, message = "字典类型名称长度不能超过100个字符")
    private String dictName;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    @Pattern(regexp = RePatternConstantsPool.DICT_TYPE_PATTERN, message = "字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）")
    private String dictType;

    /**
     * 字典模块
     */
    private String dictModule;

    /**
     * 备注
     */
    private String remark;


}
