package org.lc.admin.quartz.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;

/**
 * Description: 定时任务调度日志请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 13:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartzJobLogRequest extends BaseRequest {

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

}
