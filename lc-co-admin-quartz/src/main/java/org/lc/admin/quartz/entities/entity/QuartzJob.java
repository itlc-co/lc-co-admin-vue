package org.lc.admin.quartz.entities.entity;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;
import org.lc.admin.quartz.utils.CronUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Description: 定时任务
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class QuartzJob extends BaseEntity {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务调用目标
     */
    private String jobInvokeTarget;

    /**
     * 是否并发
     */
    private Integer isConcurrent;

    /**
     * cron定时表达式
     */
    private String cronExpression;

    /**
     * 调度失败策略
     */
    private Character misfirePolicy;

    /**
     * 任务调用目标参数
     */
    private String jobInvokeParams;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getNextValidTime() {
        return StrUtil.isNotBlank(cronExpression) ? CronUtils.getNextExecution(cronExpression) : null;
    }

}
