package org.lc.admin.quartz.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 任务bo实体
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 17:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("quartzJobBo")
public class QuartzJobBo implements Serializable {

    /**
     * 任务id
     */
    @Excel(name = "任务编号", width = 15)
    private Long jobId;

    /**
     * 任务名称
     */
    @Excel(name = "任务名称", orderNum = "1", width = 25)
    private String jobName;

    /**
     * 任务组名
     */
    @Excel(name = "任务组名", orderNum = "2", width = 20)
    private String jobGroup;

    /**
     * 任务调用目标
     */
    @Excel(name = "任务调用目标字符串(beanName.methodName())", orderNum = "3", width = 50)
    private String jobInvokeTarget;

    /**
     * 是否并发
     */
    @Excel(name = "是否并发", orderNum = "4", width = 15, replace = {"0_否", "1_是"})
    private Integer isConcurrent;

    /**
     * cron定时表达式
     */
    @Excel(name = "cron定时表达式", orderNum = "5", width = 35)
    private String cronExpression;

    /**
     * 调度失败策略
     */
    @Excel(name = "调度失败策略", orderNum = "6", width = 25, replace = {"0_默认", "1_立即触发执行", "2_触发一次执行", "3_不触发立即执行"})
    private Character misfirePolicy;

    /**
     * 任务调用目标参数
     */
    @Excel(name = "任务调用目标参数", orderNum = "7", width = 50)
    private String jobInvokeParams;

    /**
     * 任务状态
     */
    @Excel(name = "任务状态", orderNum = "8", width = 15, replace = {"0_启动", "1_暂停"})
    private Integer status;

}
