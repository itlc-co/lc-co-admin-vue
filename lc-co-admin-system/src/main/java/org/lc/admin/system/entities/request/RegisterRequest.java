package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Description: 注册请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends AuthRequest {

    /**
     * 再一次密码
     */
    @NotBlank(message = "密码不能为空白")
    @Size(max = 15, message = "密码长度不能超过15个字符")
    private String againPassword;


}
