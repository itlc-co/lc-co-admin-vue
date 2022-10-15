package org.lc.admin.framework.handler.interceptor.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.lc.admin.common.annotation.RepeatRequest;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.framework.handler.interceptor.RepeatRequestInterceptor;
import org.lc.admin.framework.handler.warpper.AsyncRequestWrapper;
import org.lc.admin.framework.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Description: 重复请求拦截器实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-05 18:59
 */
@Component
public class RepeatRequestInterceptorImpl extends RepeatRequestInterceptor {

    public static final Logger log = LoggerFactory.getLogger(RepeatRequestInterceptorImpl.class);
    public final String REQUEST_PARAM = "requestParam";
    public final String REQUEST_TIME = "requestTime";

    @Resource
    private RedisCacheService redisCacheService;


    /**
     * Description: 是否为重复请求
     *
     * @param request       请求
     * @param repeatRequest 重复请求注解
     * @return boolean true 是 false 否
     * @date 2022-10-05 18:59
     */
    @SuppressWarnings("unchecked")
    @Override
    protected boolean isRepeatSubmit(HttpServletRequest request, RepeatRequest repeatRequest) {
        // 是否为重复请求标记位
        boolean flag = false;

        // 当前请求数据map以及请求参数
        Map<String, Object> nowMap = MapUtil.newHashMap(2);
        String nowParam = StrUtil.EMPTY_JSON;

        if (request instanceof AsyncRequestWrapper) {
            // 当前请求为异步请求的实现
            // 获取请求体中的参数如果为空则获取请求头参数
            AsyncRequestWrapper requestWrapper = (AsyncRequestWrapper) request;
            nowParam = StrUtil.blankToDefault(JSONUtil.toJsonStr(requestWrapper.getBodyString()), JSONUtil.toJsonStr(requestWrapper.getRequestParamMap()));
        }

        if (StrUtil.isBlank(nowParam)) {
            // 非异步请求实现获取请求头参数map
            nowParam = JSONUtil.toJsonStr(request.getParameterMap());
        }

        // 当前请求的请求参数（请求体参数中或者请求头参数）以及请求时间
        nowMap.put(REQUEST_PARAM, nowParam);
        nowMap.put(REQUEST_TIME, System.currentTimeMillis());

        // 用户认证token字符串
        String token = TokenUtils.getToken(request);

        // 请求uri
        String uri = request.getRequestURI();

        // 封装缓存键
        String key = StrUtil.format("{}::{}", token, uri);

        // 获取上一次重复请求的缓存值 不存在为null
        Map<String, Object> preValueMap = redisCacheService.getRepeatRequest(key);
        if (MapUtil.isNotEmpty(preValueMap)) {
            // 存在上一次重复请求的缓存值比较value
            Map<String, Object> preMap = (Map<String, Object>) preValueMap.getOrDefault(uri, MapUtil.newHashMap());
            // 比较请求参数，请求时间
            flag = compareParams(nowMap, preMap) && compareTime(nowMap, preMap, repeatRequest.interval());
        }

        // 不存在则保存当前请求的重复请求缓存数据
        this.redisCacheService.saveRepeatRequest(key, MapUtil.of(uri, nowMap), repeatRequest.interval());
        return flag;
    }

    /**
     * Description: 比较两次请求时间差距是否在interval内
     *
     * @param nowMap   当前请求数据map
     * @param preMap   上一次请求数据map
     * @param interval 时间间隔
     * @return boolean true 是 false 否
     * @date 2022-10-05 20:03
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
        long nowTime = (long) nowMap.getOrDefault(REQUEST_TIME, 0L);
        long preTime = (long) preMap.getOrDefault(REQUEST_TIME, 0L);
        return nowTime != preTime && (nowTime - preTime) < interval;
    }

    /**
     * Description: 比较两次请求参数是否一致
     *
     * @param nowMap 当前请求数据map
     * @param preMap 上一次请求数据map
     * @return boolean true 一致 false 不一致
     * @date 2022-10-05 20:01
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        // 获取当前与上一次请求的请求参数字符串并进行比较
        String nowParams = (String) nowMap.getOrDefault(REQUEST_PARAM, StrUtil.EMPTY);
        String preParams = (String) preMap.getOrDefault(REQUEST_PARAM, StrUtil.EMPTY);
        return StrUtil.isAllNotEmpty(nowParams, preParams) && StrUtil.equalsIgnoreCase(nowParams, preParams);
    }


}
