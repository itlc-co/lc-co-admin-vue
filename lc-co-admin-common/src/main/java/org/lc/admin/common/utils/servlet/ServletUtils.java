package org.lc.admin.common.utils.servlet;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.base.pool.CharacterSet;
import org.lc.admin.common.base.pool.ContentType;
import org.lc.admin.common.base.pool.StatusCode;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Description: Servlet工具类
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:15
 */
public class ServletUtils {

    /**
     * 定义移动端请求的所有可能类型
     */
    private final static String[] agent = {"Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser"};

    /**
     * Description: 获取String参数
     *
     * @param name 参数名称
     * @return {@link String } 参数值
     * @date 2022-09-24 10:39
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * Description: 获取String参数
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return {@link String } 参数值
     * @date 2022-09-24 10:39
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * Description: 获取Integer参数
     *
     * @param name 参数名称
     * @return {@link Integer } 参数值
     * @date 2022-09-24 10:39
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * Description: 获取Integer参数
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return {@link Integer } 参数值
     * @date 2022-09-24 10:39
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * Description: 获取Boolean参数
     *
     * @param name 参数名称
     * @return {@link Boolean } 参数值
     * @date 2022-09-24 10:39
     */
    public static Boolean getParameterToBool(String name) {
        return Convert.toBool(getRequest().getParameter(name));
    }

    /**
     * Description: 获取Boolean参数
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return {@link Boolean } 参数值
     * @date 2022-09-24 10:38
     */
    public static Boolean getParameterToBool(String name, Boolean defaultValue) {
        return Convert.toBool(getRequest().getParameter(name), defaultValue);
    }

    /**
     * Description: 获取request请求
     *
     * @return {@link HttpServletRequest } 请求
     * @date 2022-09-24 10:38
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = getRequestAttributes();
        return ObjectUtil.isNotNull(attributes) ? attributes.getRequest() : null;
    }

    /**
     * Description: 获取response
     *
     * @return {@link HttpServletResponse } response 响应
     * @date 2022-09-24 10:38
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * Description:  获取session
     *
     * @return {@link HttpSession } http session
     * @date 2022-09-24 10:38
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * Description: 获取请求属性
     *
     * @return {@link ServletRequestAttributes } 服务请求属性
     * @date 2022-09-24 10:38
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * Description: 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   响应json字符串
     * @return {@link String } 响应json字符串
     * @date 2022-09-24 10:38
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setContentType(ContentType.JSON);
            response.setStatus(StatusCode.HTTP_OK);
            response.setCharacterEncoding(CharacterSet.UTF_8);
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }


    /**
     * Description: 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param result   响应结果集
     * @return {@link String } 响应json字符串
     * @date 2022-10-08 20:01
     */
    public static String renderString(HttpServletResponse response, ResultResponse result) {
        return renderString(response, JSONUtil.toJsonStr(result));
    }


    /**
     * Description: 是否为ajax请求
     *
     * @param request 请求
     * @return boolean true 是 false 不是
     * @date 2022-09-24 10:37
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            return true;
        }
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
            return true;
        }
        String uri = request.getRequestURI();
        if (StrUtil.equalsAnyIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }
        String ajax = request.getParameter("__ajax");
        return StrUtil.equalsAnyIgnoreCase(ajax, "json", "xml");
    }

    /**
     * Description: 判断User-Agent 是不是来自于移动端
     *
     * @param ua User-Agent
     * @return boolean true 来自于移动端 false 不来自移动端
     * @date 2022-09-24 10:37
     */
    public static boolean checkAgentIsMobile(String ua) {
        boolean flag = false;
        if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
            // 排除 苹果桌面系统
            if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
                for (String item : agent) {
                    if (ua.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * Description: 内容编码
     *
     * @param str 内容
     * @return {@link String } 编码后字符串
     * @date 2022-09-24 10:36
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, CharacterSet.UTF_8);
        } catch (UnsupportedEncodingException e) {
            return StrUtil.EMPTY;
        }
    }

    /**
     * Description: 内容解码
     *
     * @param str 内容
     * @return {@link String } 解码后字符串
     * @date 2022-09-24 10:36
     */
    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, CharacterSet.UTF_8);
        } catch (UnsupportedEncodingException e) {
            return StrUtil.EMPTY;
        }
    }

    /**
     * Description: 转换请求
     *
     * @param request 请求
     * @return {@link HttpServletRequest } http请求
     * @date 2022-09-24 10:36
     */
    public static HttpServletRequest toHttpServlet(ServletRequest request) {
        return (HttpServletRequest) request;
    }


    /**
     * Description: 获取请求体数据字节数组
     *
     * @param request 请求
     * @return {@link byte[] } 请求体数据字节数组
     * @date 2022-10-05 15:56
     */
    public static byte[] getBodyBytes(HttpServletRequest request) {
        try {
            return IoUtil.readBytes(request.getInputStream());
        } catch (Exception e) {
            return new byte[]{};
        }
    }


    /**
     * Description: 获取uri
     *
     * @return {@link String } uri
     * @date 2022-10-05 22:05
     */
    public static String getUri() {
        String uri = StrUtil.EMPTY;
        HttpServletRequest request = getRequest();
        if (ObjectUtil.isNotNull(request)) {
            return request.getRequestURI();
        }
        return uri;
    }

    /**
     * Description: 获取请求方法
     *
     * @return {@link String } 请求方法
     * @date 2022-10-05 22:07
     */
    public static String getRequestMethod() {
        String uri = StrUtil.EMPTY;
        HttpServletRequest request = getRequest();
        if (ObjectUtil.isNotNull(request)) {
            return request.getMethod();
        }
        return uri;
    }
}
