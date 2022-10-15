package org.lc.admin.quartz.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Description: 定时任务日志bo实体
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class QuartzJobLogBo implements Serializable {

    /**
     * 任务日志id
     */
    @Excel(name = "任务日志序号", width = 15)
    private Long jobLogId;

    /**
     * 任务名称
     */
    @Excel(name = "任务名称", orderNum = "1", width = 25)
    private String jobName;

    /**
     * 任务组名
     */
    @Excel(name = "任务组名", orderNum = "2", width = 25)
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @Excel(name = "调用目标字符串", orderNum = "3", width = 25)
    private String jobInvokeTarget;

    /**
     * 日志消息
     */
    @Excel(name = "任务日志信息", orderNum = "4", width = 30)
    private String jobMessage;

    /**
     * 任务运行时间
     */
    @Excel(name = "任务耗时", orderNum = "5", width = 25, suffix = "ms")
    private Long jobRunTime;

    /**
     * 任务状态
     */
    @Excel(name = "任务状态", orderNum = "6", width = 15, replace = {"0_成功", "1_失败"})
    private Integer status;
}
