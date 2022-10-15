package org.lc.admin.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Description: 日期工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:53
 */
public class DateUtils {


    /**
     * Description: 获取两者日期之间的差距 （?天?小时?分?秒）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@link String }（?天?小时?分?秒）
     * @date 2022-10-02 15:55
     */
    public static String getDateBetween(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        // 相差的天数
        long days = duration.toDays();
        // 相差的小时数
        long hours = duration.toHours();
        // 相差的分钟数
        long minutes = duration.toMinutes();
        // 相差秒数
        long seconds = duration.toMillis() / 1000;
        return StrUtil.format("{}天{}小时{}分{}秒", days, hours, minutes, seconds);
    }


    /**
     * Description: 获取服务器启动时间
     *
     * @return {@link LocalDateTime } 服务器启动时间
     * @date 2022-10-02 15:55
     */
    public static LocalDateTime getServerStartTime() {
        return LocalDateTimeUtil.of(ManagementFactory.getRuntimeMXBean().getStartTime(), ZoneId.systemDefault());
    }


}
