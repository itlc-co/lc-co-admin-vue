package org.lc.admin.framework.security.filter;

import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.framework.security.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 身份验证令牌token过滤器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-06 11:25
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    /**
     * 令牌token服务
     */
    @Resource
    private TokenService tokenService;


    /**
     * Description: token认证过滤器内部
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException servlet异常
     * @throws IOException      io异常
     * @date 2022-09-06 11:26
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求中获取认证用户实例
        UserDetail user = this.tokenService.getUserDetail(request);
        // 如果存在认证用户并未认证
        if (ObjectUtil.isNotNull(user) && ObjectUtil.isNull(SecurityUtils.getAuthentication())) {
            // 校验用户认证时效性并更新时效性
            this.tokenService.verifyAuthUserTimeliness(user);
            // 重新将用户信息封装到UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 将信息存入上下文对象
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 过滤链传递
        filterChain.doFilter(request, response);
    }

}
