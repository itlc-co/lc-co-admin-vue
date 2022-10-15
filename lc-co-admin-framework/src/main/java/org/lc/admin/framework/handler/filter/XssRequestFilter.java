package org.lc.admin.framework.handler.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.pool.HttpRequestMethod;
import org.lc.admin.common.utils.server.UrlUtils;
import org.lc.admin.framework.handler.warpper.XssRequestWrapper;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: xss请求过滤器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 13:18
 */
@Component
public class XssRequestFilter implements Filter, Ordered {

    /**
     * 排除过滤uri
     */
    public List<String> excludes = ListUtil.list(false);

    /**
     * xss过滤开关
     */
    public boolean enabled = false;

    /**
     * xss过滤类型
     */
    public String level = "none";


    /**
     * Description: 过滤器初始化
     *
     * @param filterConfig 过滤器配置
     * @throws ServletException servlet异常
     * @date 2022-10-07 13:14
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化参数
        String tempExcludes = filterConfig.getInitParameter("excludes");
        String tempEnabled = filterConfig.getInitParameter("enabled");
        String tempLevel = filterConfig.getInitParameter("level");

        if (StrUtil.isNotBlank(tempExcludes)) {
            // 添加排除过滤uri列表
            this.excludes.addAll(StrUtil.split(tempExcludes, ","));
        }
        if (StrUtil.isNotBlank(tempEnabled)) {
            // xss过滤开关
            this.enabled = Boolean.parseBoolean(tempEnabled);
        }
        if (StrUtil.isNotBlank(tempLevel)) {
            // xss过滤类型
            this.level = tempLevel;
        }
    }

    /**
     * Description: xss请求过滤器内部实现
     *
     * @param servletRequest  servlet请求
     * @param servletResponse servlet响应
     * @param chain           过滤链
     * @throws IOException      io异常
     * @throws ServletException servlet异常
     * @date 2022-10-07 13:15
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 判断是否排除当前请求 非排除的请求封装为XssRequestWrapper
        chain.doFilter(isExcludeUri(request) ? request : new XssRequestWrapper(request, this.level), response);
    }

    /**
     * Description: 判断当前请求是否为排除的请求
     *
     * @param request 请求
     * @return boolean true 是 false 否
     * @date 2022-10-07 13:16
     */
    private boolean isExcludeUri(HttpServletRequest request) {
        // 默认排除当前请求
        boolean flag = true;
        if (enabled && CollUtil.isNotEmpty(excludes)) {
            // 请求url与请求方法
            String url = request.getServletPath();
            String method = request.getMethod();
            // GET DELETE 排除
            if (StrUtil.isNotBlank(method) && !StrUtil.equalsAnyIgnoreCase(method, HttpRequestMethod.GET, HttpRequestMethod.DELETE)) {
                // 匹配url规则
                flag = UrlUtils.isAnyMatchUrl(url, excludes);
            }
        }
        return flag;
    }

    /**
     * Description: 过滤器摧毁
     *
     * @date 2022-10-07 13:17
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * Description: 优先级
     *
     * @return int 优先级num
     * @date 2022-10-07 13:18
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
