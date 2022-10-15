package org.lc.admin.framework.handler.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.framework.handler.warpper.AsyncRequestWrapper;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Description: 异步请求过滤器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:37
 */
@Component
public class AsyncRequestFilter implements Filter, Ordered {

    /**
     * Description: 过滤器初始化
     *
     * @param filterConfig 过滤器配置
     * @throws ServletException servlet异常
     * @date 2022-10-02 15:37
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * Description: 异步请求过滤器内部实现
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤链
     * @throws IOException      io异常
     * @throws ServletException servlet异常
     * @date 2022-10-02 15:37
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest && StrUtil.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            // 封装为异步RequestWrapper
            requestWrapper = new AsyncRequestWrapper((HttpServletRequest) request);
        }
        // 在chain.doFiler方法中传递新的request对象
        chain.doFilter(ObjectUtil.defaultIfNull(requestWrapper, request), response);
    }

    /**
     * Description: 过滤器摧毁
     *
     * @date 2022-10-02 15:38
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * Description: 优先级
     *
     * @return int 优先级num
     * @date 2022-10-02 15:38
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
