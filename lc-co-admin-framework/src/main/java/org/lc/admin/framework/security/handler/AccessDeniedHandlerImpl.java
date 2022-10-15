package org.lc.admin.framework.security.handler;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.base.pool.Message;
import org.lc.admin.common.base.pool.StatusCode;
import org.lc.admin.common.utils.servlet.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 拒绝访问处理器实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 14:31
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    /**
     * Description: 处理实现逻辑
     *
     * @param request               请求
     * @param response              响应
     * @param accessDeniedException 拒绝访问异常
     * @throws IOException      io异常
     * @throws ServletException servlet异常
     * @date 2022-10-07 14:31
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 获取请求地址
        String uri = request.getRequestURI();
        // 获取权限检验异常消息以及异常状态码
        Integer code = StatusCode.ACCESS_DENIED;
        String message = StrUtil.format(Message.ACCESS_DENIED, uri);
        // 返回结果集响应
        ServletUtils.renderString(response, ResultResponse.error(code, message));
    }

}
