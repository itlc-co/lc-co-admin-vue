package org.lc.admin.framework.config;

import cn.hutool.core.map.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.lc.admin.framework.handler.filter.AsyncRequestFilter;
import org.lc.admin.framework.handler.filter.XssRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;
import java.util.Map;

/**
 * Description: 过滤器配置
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 15:01
 */
@Configuration
public class FilterConfig {

    @Value("${xss.enabled}")
    private String enabled;

    @Value("${xss.excludes}")
    private String excludes;

    @Value("${xss.urlPatterns}")
    private String urlPatterns;

    @Value("${xss.level}")
    private String level;

    /**
     * Description: xss请求过滤器注册
     *
     * @return {@link FilterRegistrationBean } 过滤注册bean
     * @date 2022-10-07 14:59
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    @ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
    public FilterRegistrationBean xssRequestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 请求程度调度
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        // 注册bean设置过滤器
        registration.setFilter(new XssRequestFilter());
        // url匹配规则
        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        // 过滤器名称
        registration.setName("xssRequestFilter");
        // 最高优先级
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        // 初始化参数
        Map<String, String> initParameters = MapUtil.newHashMap(3);
        // 排除的url，是否开启xss过滤，xss等级
        initParameters.put("excludes", excludes);
        initParameters.put("enabled", enabled);
        initParameters.put("level", level);
        registration.setInitParameters(initParameters);
        return registration;
    }

    /**
     * Description: 异步请求过滤器注册
     *
     * @return {@link FilterRegistrationBean } 过滤注册bean
     * @date 2022-10-07 14:58
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean asyncRequestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AsyncRequestFilter());
        // url匹配规则 /* 所有请求
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("asyncRequestFilter");
        // 最低优先级
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }

}
