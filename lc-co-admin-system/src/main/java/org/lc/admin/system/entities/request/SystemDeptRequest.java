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
 * Description: 部门请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemDeptRequest extends BaseRequest {

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;

    /**
     * 电子邮件
     */
    @Pattern(regexp = RePatternConstantsPool.EMAIL_PATTERN,message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 领袖
     */
    private String leader;

    /**
     * 电话
     */
    @Pattern(regexp = RePatternConstantsPool.PHONE_PATTERN,message = "手机号码格式不正确")
    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    private String phone;



}
