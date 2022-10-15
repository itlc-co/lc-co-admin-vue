package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Description: 认证请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "用户账号不能为空白")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空白")
    @Size(max = 15, message = "密码长度不能超过15个字符")
    private String password;

}
