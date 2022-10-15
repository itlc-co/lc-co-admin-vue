package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;
import org.lc.admin.common.pool.RePatternConstantsPool;
import org.lc.admin.common.validated.annotation.Xss;
import org.lc.admin.common.validated.group.Insert;
import org.lc.admin.common.validated.group.ResetPwd;
import org.lc.admin.common.validated.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Description: 用户请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-10 19:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserRequest extends BaseRequest {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 用户名
     */
    @NotBlank(message = "用户账号不能为空白", groups = {Insert.class, Update.class})
    @Xss(message = "用户名称不能包含脚本字符", groups = {Insert.class, Update.class})
    @Size(max = 30, message = "用户账号长度不能超过30个字符", groups = {Insert.class, Update.class})
    private String userName;

    /**
     * 用户昵称
     */
    @Size(max = 30, message = "用户昵称长度不能超过30个字符", groups = {Insert.class, Update.class})
    @Xss(message = "用户昵称不能包含脚本字符", groups = {Insert.class, Update.class})
    private String nickName;

    /**
     * 手机号码
     */
    @Pattern(regexp = RePatternConstantsPool.PHONE_PATTERN, message = "手机号码格式不正确", groups = Insert.class)
    private String phoneNumber;

    /**
     * 电子邮件
     */
    @Pattern(regexp = RePatternConstantsPool.EMAIL_PATTERN, message = "邮箱格式不正确", groups = Insert.class)
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空白", groups = {Insert.class, ResetPwd.class})
    @Size(max = 15, message = "密码长度不能超过15个字符", groups = {Insert.class, ResetPwd.class})
    private String password;

    /**
     * 岗位id列表
     */
    private List<Long> postIds;

    /**
     * 角色id列表
     */
    private List<Long> roleIds;

    /**
     * 角色id
     */
    private Long roleId;


}
