package org.lc.admin.common.utils.server;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 * Description: http工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-08 17:59
 */
public class HttpUtils {


    /**
     * Description: 是否为tcp链接
     *
     * @param path 路径
     * @return boolean true 是tcp链接 false 不是tcp链接
     * @date 2022-09-08 18:02
     */
    public static boolean isTcpLink(String path) {
        return HttpUtil.isHttp(path) || HttpUtil.isHttps(path);
    }

}
