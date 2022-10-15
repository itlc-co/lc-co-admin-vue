package org.lc.admin.quartz.job;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.lc.admin.common.base.pool.Message;
import org.lc.admin.quartz.entities.entity.QuartzJob;
import org.lc.admin.quartz.entities.entity.QuartzJobLog;
import org.lc.admin.quartz.service.QuartzJobLogService;
import org.lc.admin.quartz.utils.QuartzJobThreadLocal;
import org.lc.admin.common.pool.ScheduleConstantsPool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Description: 抽象任务bean
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:54
 */
public abstract class AbstractQuartzJobBean extends QuartzJobBean {

    public static final Logger log = LoggerFactory.getLogger(AbstractQuartzJobBean.class);

    /**
     * Description: 执行器内部
     *
     * @param context 上下文
     * @date 2022-10-12 19:23
     */
    @Override
    protected void executeInternal(JobExecutionContext context) {
        // 任务执行上下文获取数据map
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        // 获取数据map的任务数据
        QuartzJob quartzJob = BeanUtil.toBean(jobDataMap.get(ScheduleConstantsPool.QUARTZ_JOB_PROPERTIES), QuartzJob.class);

        Exception exception = null;
        try {
            // before处理
            before();
            if (ObjectUtil.isNotNull(quartzJob)) {
                // 任务不为null则执行具体任务逻辑
                doExecute(context, quartzJob);
            }
        } catch (Exception e) {
            exception = e;
        } finally {
            // after处理
            after(context, quartzJob, exception);
        }
    }

    /**
     * Description: 执行后处理
     *
     * @param context   上下文
     * @param quartzJob 任务
     * @param exception 异常
     * @date 2022-10-12 19:23
     */
    protected void after(JobExecutionContext context, QuartzJob quartzJob, Exception exception) {
        log.info("quartz job after");
        // 启动时间戳
        Long quartzTimestamp = QuartzJobThreadLocal.getQuartzTimestamp();
        // bean实体转换 任务实体转任务日志实体
        QuartzJobLog quartzJobLog = BeanUtil.toBean(quartzJob, QuartzJobLog.class, CopyOptions.create().setIgnoreProperties("id"));

        // 设置任务运行时长
        quartzJobLog.setJobRunTime(System.currentTimeMillis() - quartzTimestamp);

        // 判断是否发生异常设置任务消息，任务状态
        String jobMessage = ObjectUtil.isNull(exception) ? Message.QUARTZ_JOB_SUCCESS : exception.getMessage();
        Integer status = ObjectUtil.isNull(exception) ? 0 : 1;
        quartzJobLog.setJobMessage(jobMessage);
        quartzJobLog.setStatus(status);

        // 获取spring bean实例执行插入任务数据
        SpringUtil.getBean(QuartzJobLogService.class).addQuartzJobLog(quartzJobLog);

        // 任务本地线程中异常任务启动时间戳
        QuartzJobThreadLocal.clearQuartzTimestamp();

    }

    /**
     * Description: 执行前处理
     *
     * @date 2022-10-11 13:57
     */
    protected void before() {
        log.info("quartz job before");
        // 任务本地线程中设置启动时间用户计算耗时
        QuartzJobThreadLocal.setQuartzTimestamp(System.currentTimeMillis());
    }

    /**
     * Description: 执行任务逻辑抽象方法
     *
     * @param context   上下文
     * @param quartzJob 任务
     * @throws Exception 异常
     * @date 2022-10-11 13:58
     */
    protected abstract void doExecute(JobExecutionContext context, QuartzJob quartzJob) throws Exception;
}
