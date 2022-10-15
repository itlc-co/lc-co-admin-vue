package org.lc.admin.common.annotation;

import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

import java.lang.annotation.*;

/**
 * Description: 请求限流注解
 * <p>默认60秒内限制允许2次来自所有客户端的请求</p>
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-05 21:16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestRateLimiter {

    /**
     * Description: 速度类型
     * <p>OVERALL             所有客户端加总限流</p>
     * </p>PER_CLIENT          每个客户端单独计算流量</p>
     *
     * @return {@link RateType } 速度类型
     * @date 2022-10-05 21:20
     */
    RateType rateType() default RateType.OVERALL;

    /**
     * Description: 单位时间 默认60
     *
     * @return long 单位时间
     * @date 2022-10-05 21:23
     */
    long interval() default 60L;

    /**
     * Description:访问数默认2次
     *
     * @return long  访问数
     * @date 2022-10-05 21:22
     */
    long rate() default 2L;

    /**
     * Description: 时间单位 默认秒
     *
     * @return {@link RateIntervalUnit } 限流时间单位
     * @date 2022-10-05 21:23
     */
    RateIntervalUnit unit() default RateIntervalUnit.SECONDS;
}
