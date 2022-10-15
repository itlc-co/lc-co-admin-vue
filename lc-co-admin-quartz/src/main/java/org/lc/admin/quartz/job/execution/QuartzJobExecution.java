package org.lc.admin.quartz.job.execution;

import org.lc.admin.quartz.entities.entity.QuartzJob;
import org.lc.admin.quartz.job.AbstractQuartzJobBean;
import org.lc.admin.quartz.utils.QuartzJobInvokeUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 允许并发执行的执行器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-10 08:46
 */
public class QuartzJobExecution extends AbstractQuartzJobBean {

    public static final Logger log = LoggerFactory.getLogger(QuartzJobExecution.class);

    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob quartzJob) throws Exception {
        log.info("do execute :{}", this.getClass().getName());
        QuartzJobInvokeUtils.invokeMethod(quartzJob);
    }

}
