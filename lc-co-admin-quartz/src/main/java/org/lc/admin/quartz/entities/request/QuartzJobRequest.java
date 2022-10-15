package org.lc.admin.quartz.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.lc.admin.common.base.entities.request.BaseRequest;
import org.lc.admin.common.validated.annotation.Cron;
import org.lc.admin.common.validated.annotation.InvokeTarget;
import org.lc.admin.common.validated.annotation.Json;
import org.lc.admin.common.validated.group.Insert;
import org.lc.admin.common.validated.group.Update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description: 定时任务请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartzJobRequest extends BaseRequest {


    /**
     * 任务id
     */
    @NotNull(message = "任务id不能为null", groups = {Update.class})
    @Min(value = 1L, message = "任务id最小为1")
    private Long jobId;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空白", groups = {Insert.class})
    private String jobName;

    /**
     * 任务组名
     */
    @NotBlank(message = "任务组名不能为空白", groups = {Insert.class})
    private String jobGroup;

    /**
     * 任务调用目标
     */
    @InvokeTarget(groups = {Insert.class, Update.class})
    @NotBlank(groups = Integer.class)
    private String jobInvokeTarget;

    /**
     * 任务调用目标参数
     */
    @Json(groups = {Insert.class, Update.class})
    private String jobInvokeParams;

    /**
     * 是否并发（0 false 1 true）
     */
    @Range(max = 1, min = 0, message = "是否并发标记只允许0,1", groups = Insert.class)
    private Integer isConcurrent;

    /**
     * cron定时表达式
     */
    @Cron(groups = {Insert.class, Update.class})
    private String cronExpression;

    /**
     * 调度失败策略
     */
    @NotNull(groups = Insert.class, message = "错误的调度失败策略字符")
    private Character misfirePolicy;
}
