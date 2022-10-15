package org.lc.admin.common.utils;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.pool.StrConstantsPool;

import java.math.RoundingMode;
import java.sql.Struct;
import java.util.List;

/**
 * Description: 转换工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-26 14:38
 */
public class ConvertUtils {


    /**
     * Description: b转换mb
     *
     * @param size 大小
     * @return double
     * @date 2022-09-26 14:38
     */
    public static double convertMb(long size) {
        return NumberUtil.div((float) size, 1024f * 1024f, 2, RoundingMode.DOWN);
    }

    /**
     * Description: b转换gb
     *
     * @param size 大小
     * @return double
     * @date 2022-09-26 14:38
     */
    public static double convertGb(long size) {
        return NumberUtil.div((float) size, 1024f * 1024f * 1024f, 2, RoundingMode.DOWN);
    }

    /**
     * Description: 转换为大小容量字符串  mb gb kb b 单位
     *
     * @param size 大小
     * @return {@link String } 大小容量字符串
     * @date 2022-09-26 14:48
     */
    public static String convertSizeStr(long size) {
        // 大小容量字符串
        String sizeStr;
        // 大小容量进制转换
        long b = 1L;
        long kb = 1024 * b;
        long mb = 1024 * kb;
        long gb = 1024 * mb;
        if (size >= gb) {
            // gb单位大小容量
            sizeStr = StrUtil.format("{} GB", NumberUtil.div((float) size, gb, 1, RoundingMode.DOWN));
        } else if (size >= mb) {
            // mb单位大小容量
            sizeStr = StrUtil.format("{} MB", NumberUtil.div((float) size, mb, 1, RoundingMode.DOWN));
        } else if (size >= kb) {
            // kb单位大小容量
            sizeStr = StrUtil.format("{} KB", NumberUtil.div((float) size, kb, 1, RoundingMode.DOWN));
        } else {
            // b单位大小容量
            sizeStr = StrUtil.format("{} B", NumberUtil.div((float) size, b, 1, RoundingMode.DOWN));
        }
        return sizeStr;
    }

    /**
     * Description: 将字符串转化为字符串列表
     *
     * @param strs 字符串
     * @return {@link List }<{@link String }> 字符串列表
     * @date 2022-10-12 22:49
     */
    public static List<String> toStrList(String str) {
        return StrUtil.split(str, StrConstantsPool.COMMA_SEPARATOR);
    }
}
