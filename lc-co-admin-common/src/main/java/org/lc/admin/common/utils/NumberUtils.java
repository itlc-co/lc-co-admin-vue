package org.lc.admin.common.utils;

import cn.hutool.core.util.NumberUtil;

/**
 * Description: 数字运输工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:53
 */
public class NumberUtils {


    /**
     * Description: 计算百分率
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    准确度
     * @return double 百分率
     * @date 2022-09-26 14:41
     */
    public static double percentage(long dividend, long divisor, int scale) {
        return NumberUtil.mul(NumberUtil.div(dividend, divisor, scale), 100);
    }

    /**
     * Description: 计算百分率保留小数点后两位
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return double 百分率
     * @date 2022-09-26 14:41
     */
    public static double percentageScale2(long dividend, long divisor) {
        return NumberUtil.mul(NumberUtil.div(dividend, divisor, 2), 100);
    }


}
