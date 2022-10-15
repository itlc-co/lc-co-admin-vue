package org.lc.admin.common.pool;

import org.lc.admin.common.base.pool.StrConstantsPool;

/**
 * Description: 任务调度常量池
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-10 21:43
 */
public class ScheduleConstantsPool {


    /**
     * 调度失败默认策略
     */
    public static final char MISFIRE_DEFAULT = '0';
    /**
     * 调度失败立即触发执行
     */
    public static final char MISFIRE_IGNORE_MISFIRES = '1';
    /**
     * 调度失败触发执行一次
     */
    public static final char MISFIRE_FIRE_AND_PROCEED = '2';

    /**
     * 调度失败不触发立即执行
     */
    public static final char MISFIRE_DO_NOTHING = '3';
    /**
     * 定时任务键前缀名称
     */
    public static final String QUARTZ_JOB_KEY_PRE_NAME = "JobKey";
    /**
     * 定时任务触发器键前缀名称
     */
    public static final String QUARTZ_JOB_TRIGGER_KEY_PRE_NAME = "TriggerKey";
    /**
     * 定时任务数据map属性键
     */
    public static final String QUARTZ_JOB_PROPERTIES = "properties";

    /**
     * 任务键名模板
     */
    public static final String JOB_KEY_NAME_TEMPLATE = QUARTZ_JOB_KEY_PRE_NAME + StrConstantsPool.AT_SEPARATOR + "{}";
    /**
     * 触发键名模板
     */
    public static final String TRIGGER_KEY_NAME_TEMPLATE = QUARTZ_JOB_TRIGGER_KEY_PRE_NAME + StrConstantsPool.AT_SEPARATOR + "{}";

}
