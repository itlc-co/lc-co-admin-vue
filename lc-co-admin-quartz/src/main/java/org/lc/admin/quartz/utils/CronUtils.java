package org.lc.admin.quartz.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Description: cron定时表达式工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-10 22:31
 */
public class CronUtils {
    /**
     * Description: 获取下一次执行任务的日期时间
     *
     * @param cronExpression cron表达式
     * @return {@link Date } 日期时间
     * @date 2022-10-10 22:35
     */
    public static Date getNextExecutionDateTime(String cronExpression) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Description: 获取下一次执行时间
     *
     * @param cronExpression cron定时表达式
     * @return {@link LocalDateTime } 下一次执行时间
     * @date 2022-10-12 13:40
     */
    public static LocalDateTime getNextExecution(String cronExpression) {
        return LocalDateTimeUtil.of(getNextExecutionDateTime(cronExpression));
    }


}
