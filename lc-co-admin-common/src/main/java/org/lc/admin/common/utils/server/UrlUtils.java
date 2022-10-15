package org.lc.admin.common.utils.server;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * Description: url工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-08 19:43
 */
public class UrlUtils {

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param urlPattern url匹配规则
     * @param url        需要匹配的url
     * @return boolean  true 匹配成功 false 匹配失败
     * @date 2022-10-07 13:06
     */
    public static boolean isMatchUrl(String urlPattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(urlPattern, url);
    }

    /**
     * Description: 判断url是否匹配指定url规则列表中的任意一个
     *
     * @param url         指定url
     * @param urlPatterns 指定url规则列表
     * @return boolean true 匹配成功 false 匹配失败
     * @date 2022-10-07 13:05
     */
    public static boolean isAnyMatchUrl(String url, List<String> urlPatterns) {
        if (StrUtil.isNotBlank(url) && CollUtil.isNotEmpty(urlPatterns)) {
            for (String urlPattern : urlPatterns) {
                if (isMatchUrl(urlPattern, url)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Description: 判断url是否匹配指定url规则数组中的任意一个
     *
     * @param url         指定url
     * @param urlPatterns 指定url规则数组
     * @return boolean true 匹配成功 false 匹配失败
     * @date 2022-10-07 13:07
     */
    public static boolean isAnyMatchUrl(String url, String... urlPatterns) {
        if (StrUtil.isNotBlank(url) && ArrayUtil.isNotEmpty(urlPatterns)) {
            for (String urlPattern : urlPatterns) {
                if (isMatchUrl(urlPattern, url)) {
                    return true;
                }
            }
        }
        return false;
    }

}
