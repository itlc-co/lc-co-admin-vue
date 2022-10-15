package org.lc.admin.quartz.utils;

/**
 * Description: 任务本地线程
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 14:19
 */
public class QuartzJobThreadLocal {

    /**
     * 本地线程变量 任务启动时间戳
     */
    private static final ThreadLocal<Long> CURRENT_QUARTZ_TIMESTAMP = new ThreadLocal<>();


    /**
     * Description: 获取任务启动时间戳
     *
     * @return {@link Long } 任务启动时间戳
     * @date 2022-10-11 14:20
     */
    public static Long getQuartzTimestamp() {
        return CURRENT_QUARTZ_TIMESTAMP.get();
    }

    /**
     * Description: 设置任务启动时间戳
     *
     * @param timeStamp 时间戳
     * @date 2022-10-11 14:20
     */
    public static void setQuartzTimestamp(Long timeStamp) {
        CURRENT_QUARTZ_TIMESTAMP.set(timeStamp);
    }

    /**
     * Description: 清理任务启动时间戳
     *
     * @date 2022-10-11 14:20
     */
    public static void clearQuartzTimestamp() {
        CURRENT_QUARTZ_TIMESTAMP.remove();
    }


}
