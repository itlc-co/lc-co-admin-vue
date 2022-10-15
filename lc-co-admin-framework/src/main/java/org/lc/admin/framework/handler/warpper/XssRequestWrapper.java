package org.lc.admin.framework.handler.warpper;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.utils.server.HtmlUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Description: 防止xss注入请求包装器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 11:19
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

    public String level = "none";

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public XssRequestWrapper(HttpServletRequest request, String level) {
        super(request);
        this.level = level;
    }

    /**
     * Description: 根据参数名称获取参数值
     *
     * @param name 参数名称
     * @return {@link String[] } 参数值数组
     * @date 2022-10-07 11:57
     */
    @Override
    public String[] getParameterValues(String name) {
        // 去除xss注入文本和过滤前后空格
        return Arrays.stream(super.getParameterValues(name)).map((str) -> HtmlUtils.clean(level, str)).filter(StrUtil::isNotBlank).distinct().toArray(String[]::new);
    }

    /**
     * Description: 获取Servlet输入流
     *
     * @return {@link ServletInputStream } Servlet输入流
     * @throws IOException io异常
     * @date 2022-10-07 11:57
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 默认输入流为父类输入流
        ServletInputStream input = super.getInputStream();
        // 当前content-type为json并且请求体中有数据
        if (isJsonRequest() && input.available() > 0) {
            // 读取请求体json数据并使用utf8编码集
            String json = IoUtil.read(input, StandardCharsets.UTF_8);
            if (StrUtil.isNotEmpty(json)) {
                // xss文本过滤
                json = HtmlUtils.clean(level, json);
                // 转换为字节数组输入流
                final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
                // 实现一个ServletInputStream输入流
                input = new ServletInputStream() {
                    @Override
                    public boolean isFinished() {
                        return true;
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public int available() {
                        return bis.available();
                    }

                    @Override
                    public void setReadListener(ReadListener readListener) {
                    }

                    @Override
                    public int read() {
                        // 读取请求体数据
                        return bis.read();
                    }
                };
            }
        }
        return input;
    }


    /**
     * Description: 判断是否为application/json请求
     *
     * @return boolean
     * @date 2022-10-07 11:20
     */
    public boolean isJsonRequest() {
        return StrUtil.startWithIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON_VALUE);
    }


}
