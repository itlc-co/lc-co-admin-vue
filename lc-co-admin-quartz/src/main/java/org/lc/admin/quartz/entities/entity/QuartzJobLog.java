package org.lc.admin.quartz.entities.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 定时任务日志
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
public class QuartzJobLog extends BaseEntity {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    private String jobInvokeTarget;

    /**
     * 日志信息
     */
    private String jobMessage;

    /**
     * 任务运行时间
     */
    private Long jobRunTime;
}
