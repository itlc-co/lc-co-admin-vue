package org.lc.admin.common.utils.server;

import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 * Description: html文本工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-08 19:42
 */
public class HtmlUtils {

    /**
     * Description: 清除html文本
     *
     * @param level 清除类型标记
     * @param str   html文本字符串
     * @return {@link String } 清除后的字符串
     * @date 2022-10-07 11:39
     */
    public static String clean(String level, String str) {
        // 默认空白字符串
        String value = StrUtil.EMPTY;
        switch (level) {
            case "none":
                // none会清除所有HTML标签，仅保留文本节点。
                value = StrUtil.trim(Jsoup.clean(str, Safelist.none()));
                break;
            case "simpleText":
                // simpleText会保留b, em, i, strong, u 标签，除此之外的所有HTML标签都会被清除。
                value = StrUtil.trim(Jsoup.clean(str, Safelist.simpleText()));
                break;
            case "basic":
                // basic会保留a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, span, strike, strong, sub, sup, u, ul 和其适当的属性标签
                // 除此之外的所有HTML标签都会被清除，不允许出现图片(img tag)
                // 允许其指定http, https, ftp, mailto 且在超链接中强制追加rel=nofollow属性
                value = StrUtil.trim(Jsoup.clean(str, Safelist.basic()));
                break;
            case "basicWithImages":
                // basicWithImages会保留basic中允许出现的标签的同时也允许出现图片(img tag)和img的相关适当属性，
                // 且其src允许其指定 http 或 https
                value = StrUtil.trim(Jsoup.clean(str, Safelist.basicWithImages()));
                break;
            case "relaxed":
                // relaxed会保留 a, b, blockquote, br, caption, cite, code, col, colgroup, dd, div, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, span, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul 标签，
                // 除此之外的所有HTML标签都会被清除，且在超链接中不会强制追加rel=nofollow属性
                value = StrUtil.trim(Jsoup.clean(str, Safelist.relaxed()));
                break;
            default:
                break;
        }
        return value;
    }
}
