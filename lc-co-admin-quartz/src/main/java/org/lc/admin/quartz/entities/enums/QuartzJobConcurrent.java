package org.lc.admin.quartz.entities.enums;

/**
 * Description: job并发枚举
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 22:06
 */
public enum QuartzJobConcurrent {


    /**
     * 允许并发
     */
    ALLOW(0),

    /**
     * 禁止并发
     */
    BAN(1);

    private final Integer value;

    private QuartzJobConcurrent(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }


}
