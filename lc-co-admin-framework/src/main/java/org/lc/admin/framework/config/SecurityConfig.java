package org.lc.admin.framework.config;

import org.lc.admin.framework.security.filter.AuthTokenFilter;
import org.lc.admin.framework.security.filter.ValidateCodeFilter;
import org.lc.admin.framework.security.handler.AuthenticationProvider;
import org.lc.admin.framework.security.handler.LogoutHandler;
import org.lc.admin.framework.security.handler.UnauthorizedHandler;
import org.lc.admin.framework.security.service.PasswordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

/**
 * Description: spring security 配置
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  12:08
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordService passwordService;

    @Resource
    private UnauthorizedHandler unauthorizedHandler;

    @Resource
    private LogoutHandler logoutHandler;

    @Resource
    private ValidateCodeFilter validateCodeFilter;

    @Resource
    private AuthTokenFilter authTokenFilter;

    @Resource
    private CorsFilter corsFilter;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;


    /**
     * Description:  解决无法直接注入AuthenticationManager
     *
     * @return org.springframework.security.authentication.AuthenticationManager 认证管理器
     * @throws Exception 异常
     * @date 2022-09-01 21:34
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * Description: http协议安全配置
     *
     * @param httpSecurity http安全配置
     * @return void
     * @date 2022-09-01 21:37
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 访问拒绝处理类
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录/auth/login 注册/auth/register  允许匿名访问不允许认证用户访问
                .antMatchers("/auth/login", "/auth/register").anonymous()
                // 验证码/auth/captcha 通用/common/**  放行允许任何访问
                .antMatchers("/auth/captcha", "/common/**", "/test/**").permitAll()
                // 静态资源放行
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/* */*.js", "/profile/**").permitAll()
                // swagger api接口文档放行
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        // 添加Logout filter
        httpSecurity.logout().logoutUrl("/auth/logout").logoutSuccessHandler(logoutHandler);
        // 添加validateCode filter
        httpSecurity.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加autoToken filter
        httpSecurity.addFilterAfter(authTokenFilter, ValidateCodeFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, AuthTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationProvider());
    }


    /**
     * Description: 身份验证提供者
     *
     * @return {@link AuthenticationProvider }  身份验证提供者实例
     * @date 2022-09-14 11:15
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider(userDetailsService, passwordService);
    }

}
