package org.lc.admin.framework.handler.warpper;

import cn.hutool.core.map.MapUtil;
import org.lc.admin.common.utils.servlet.ServletUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

/**
 * Description: 异步请求包装实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:38
 */
public class AsyncRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 请求体
     */
    private final byte[] body;
    /**
     * 请求头map
     */
    private final Map<String, String> headMap;
    /**
     * 请求参数map
     */
    private final Map<String, String> requestParamMap;

    public AsyncRequestWrapper(HttpServletRequest request) throws IOException {

        super(request);
        // 封装请求体
        body = ServletUtils.getBodyBytes(request);

        // 封装请求头map
        headMap = MapUtil.newHashMap();
        Enumeration<String> headNameList = request.getHeaderNames();
        while (headNameList.hasMoreElements()) {
            // 迭代获取请求头key-value
            String key = headNameList.nextElement();
            headMap.put(key.toLowerCase(), request.getHeader(key));
        }

        // 封装请求参数map
        requestParamMap = MapUtil.newHashMap();
        Enumeration<String> parameterNameList = request.getParameterNames();
        while (parameterNameList.hasMoreElements()) {
            // 迭代获取请求参数key-value
            String key = parameterNameList.nextElement();
            requestParamMap.put(key.toLowerCase(), request.getParameter(key));
        }

    }

    /**
     * Description: 获取reader缓存读取实例
     *
     * @return {@link BufferedReader }  缓存读取实例
     * @throws IOException io异常
     * @date 2022-10-02 15:40
     */
    @Override
    public BufferedReader getReader() throws IOException {
        // 请求二进制数据封装为BufferedReader
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * Description: 获取Servlet输入流
     *
     * @return {@link ServletInputStream } Servlet输入流
     * @throws IOException io异常
     * @date 2022-10-02 15:41
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        // 请求体封装为ByteArrayInputStream
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                // 读取请求体二进制数据
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int available() {
                return byteArrayInputStream.available();
            }
        };
    }

    /**
     * Description: 根据请求头名称获取请求头数据
     *
     * @param name 请求头名称
     * @return {@link String } 请求头数据
     * @date 2022-10-02 15:42
     */
    @Override
    public String getHeader(String name) {
        return headMap.get(name.toLowerCase());
    }

    /**
     * Description: 根据请求参数或取请求参数数据
     *
     * @param name 请求参数名称
     * @return {@link String } 请求参数数据
     * @date 2022-10-02 15:42
     */
    @Override
    public String getParameter(String name) {
        return requestParamMap.get(name);
    }

    /**
     * Description: 获取请求体数据字节数组
     *
     * @return {@link byte[] }  字节数组
     * @date 2022-10-05 19:07
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * Description: 获取请求体数据字符串
     *
     * @return {@link String }  数据字符串
     * @date 2022-10-05 19:08
     */
    public String getBodyString() {
        return new String(this.body, StandardCharsets.UTF_8);
    }

    /**
     * Description: 获取请求头数据map
     *
     * @return {@link Map }<{@link String }, {@link String }> 请求头map
     * @date 2022-10-05 19:09
     */
    public Map<String, String> getHeadMap() {
        return headMap;
    }

    /**
     * Description: 获取请求参数数据map
     *
     * @return {@link Map }<{@link String }, {@link String }> 参数map
     * @date 2022-10-05 19:09
     */
    public Map<String, String> getRequestParamMap() {
        return requestParamMap;
    }


}
