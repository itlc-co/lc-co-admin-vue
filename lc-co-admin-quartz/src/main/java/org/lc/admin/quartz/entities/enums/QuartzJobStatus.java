package org.lc.admin.quartz.entities.enums;

/**
 * Description: 定时任务状态枚举
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-10 22:29
 */
public enum QuartzJobStatus {

    /**
     * 启动
     */
    START(0),
    /**
     * 暂停
     */
    PAUSE(1);

    private final Integer value;

    private QuartzJobStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
