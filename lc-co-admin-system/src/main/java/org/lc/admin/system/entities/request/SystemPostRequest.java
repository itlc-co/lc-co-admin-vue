package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Description: 岗位请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemPostRequest extends BaseRequest {

    /**
     * 岗位名称
     */
    @NotBlank(message = "岗位名称不能为空")
    @Size(max = 50, message = "岗位名称长度不能超过50个字符")
    private String postName;

    /**
     * 岗位编码
     */
    @NotBlank(message = "岗位编码不能为空")
    @Size(max = 64, message = "岗位编码长度不能超过64个字符")
    private String postCode;

    /**
     * 岗位id
     */
    private Long postId;

}
