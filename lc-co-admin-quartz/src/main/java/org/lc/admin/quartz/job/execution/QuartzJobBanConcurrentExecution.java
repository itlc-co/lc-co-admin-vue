package org.lc.admin.quartz.job.execution;

import org.lc.admin.quartz.entities.entity.QuartzJob;
import org.lc.admin.quartz.job.AbstractQuartzJobBean;
import org.lc.admin.quartz.utils.QuartzJobInvokeUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 禁止并发执行任务执行器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 14:03
 */
@DisallowConcurrentExecution
public class QuartzJobBanConcurrentExecution extends AbstractQuartzJobBean {

    public static final Logger log = LoggerFactory.getLogger(QuartzJobBanConcurrentExecution.class);

    /**
     * Description: 执行逻辑
     *
     * @param context   上下文
     * @param quartzJob 任务
     * @throws Exception 异常
     * @date 2022-10-11 14:03
     */
    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob quartzJob) throws Exception {
        log.info("do execute :{}", this.getClass().getName());
        // 执行任务逻辑方法
        QuartzJobInvokeUtils.invokeMethod(quartzJob);
    }

}
