package org.lc.admin.framework.handler.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import org.lc.admin.common.annotation.RepeatRequest;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.utils.servlet.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-05  17:37
 */
@Component
public abstract class RepeatRequestInterceptor extends HandlerInterceptorAdapter {

    public static final Logger log = LoggerFactory.getLogger(RepeatRequestInterceptor.class);

    /**
     * Description: 重复请求前置拦截处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean true 通过 false 不通过
     * @throws Exception 异常
     * @date 2022-10-05 17:54
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截是否通过标记
        boolean flag = true;
        if (handler instanceof HandlerMethod) {
            // 方法处理器的具体实现处理器
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法Method实例以及方法上重复请求注解实例
            Method method = handlerMethod.getMethod();
            RepeatRequest annotation = method.getAnnotation(RepeatRequest.class);
            if (ObjectUtil.isNotNull(annotation)) {
                // 存在重复请求注解说明需要拦截重复请求
                if (this.isRepeatSubmit(request, annotation)) {
                    // 结果集响应并且不通过拦截器
                    ServletUtils.renderString(response, JSONUtil.toJsonStr(ResultResponse.error(annotation.message())));
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * Description: 是否为重复请求的抽象方法
     *
     * @param request       请求
     * @param repeatRequest 重复请求注解
     * @return boolean true 是 false 否
     * @date 2022-10-05 17:52
     */
    protected abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatRequest repeatRequest);


}
