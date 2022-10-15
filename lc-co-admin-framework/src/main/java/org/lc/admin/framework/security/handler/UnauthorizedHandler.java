package org.lc.admin.framework.security.handler;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.utils.servlet.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Description: 认证失败处理类 返回未认证成功
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-29  18:13
 */
@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 510101199812169104L;

    /**
     * Description: 开始处理
     *
     * @param request                 请求
     * @param response                响应
     * @param authenticationException 认证异常
     * @throws IOException      io异常
     * @throws ServletException servlet异常
     * @date 2022-09-24 11:10
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        ServletUtils.renderString(response, ResultResponse.error(StatusMsg.NOT_AUTH_ERROR));
    }

}
