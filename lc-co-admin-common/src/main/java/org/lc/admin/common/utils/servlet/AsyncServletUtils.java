package org.lc.admin.common.utils.servlet;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 异步servlet工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:51
 */
public class AsyncServletUtils {

    /**
     * 请求共享线程本地
     */
    public static TransmittableThreadLocal<HttpServletRequest> requestTransmittableThreadLocal = new TransmittableThreadLocal<HttpServletRequest>();

    /**
     * Description: 设置请求
     *
     * @param request 请求
     * @date 2022-10-02 15:51
     */
    public static void setRequest(HttpServletRequest request) {
        requestTransmittableThreadLocal.set(request);
    }

    /**
     * Description: 获取请求
     *
     * @return {@link HttpServletRequest } 请求实例
     * @date 2022-10-02 15:51
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = requestTransmittableThreadLocal.get();
        return ObjectUtil.isNotNull(request) ? request : ServletUtils.getRequest();
    }

    /**
     * Description: 删除请求
     *
     * @date 2022-10-02 15:52
     */
    public static void remove() {
        requestTransmittableThreadLocal.remove();
    }


}
