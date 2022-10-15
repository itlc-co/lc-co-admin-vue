package org.lc.admin.framework.config;

import org.lc.admin.common.pool.FileConstantsPool;
import org.lc.admin.framework.handler.interceptor.RepeatRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Description: wed mvc 配置
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-29  20:37
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private RepeatRequestInterceptor repeatRequestInterceptor;

    /**
     * Description: 添加资源处理程序
     *
     * @param registry 注册表
     * @date 2022-10-07 10:40
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 本地文件上传路径资源映射
        registry.addResourceHandler(FileConstantsPool.RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + ApplicationConfig.getProfile() + "/");

        // swagger knife4j api接口文档资源映射
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    /**
     * Description: 添加拦截器
     *
     * @param registry 拦截器注册
     * @date 2022-10-05 20:09
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(repeatRequestInterceptor).addPathPatterns("/**");
    }

    /**
     * Description: 跨域过滤器配置
     *
     * @return {@link CorsFilter } 跨域过滤器配置
     * @date 2022-10-07 10:40
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }


}
