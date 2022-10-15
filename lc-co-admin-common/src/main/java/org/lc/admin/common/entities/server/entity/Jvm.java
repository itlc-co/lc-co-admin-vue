package org.lc.admin.common.entities.server.entity;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.lc.admin.common.utils.DateUtils;
import org.lc.admin.common.utils.server.ServerUtils;

import java.time.LocalDateTime;

/**
 * Description: jvm
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-26 15:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Jvm {


    /**
     * JVM名称
     */
    private String name;


    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;


    /**
     * JVM使用内存(M)
     */
    private double used;

    /**
     * JVM内存使用率
     */
    private double usage;


    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;


    /**
     * JVM启动时间
     */
    private LocalDateTime startTime;


    /**
     * JVM运行时间
     */
    private String runTime;


    /**
     * JVM输入参数
     */
    private String inputArgs;


    /**
     * Description: JVM启动的本地时间
     *
     * @return {@link LocalDateTime } 本地时间
     * @date 2022-09-26 15:08
     */
    public LocalDateTime getStartTime() {
        return ServerUtils.getServerStartLocalDateTime();
    }

    /**
     * Description: 运行时间字符串（?天?小时?分钟）
     *
     * @return {@link String } 时间字符串
     * @date 2022-09-26 15:08
     */
    public String getRunTime() {
        return DateUtils.getDateBetween(LocalDateTimeUtil.now(), ServerUtils.getServerStartLocalDateTime());
    }


}
