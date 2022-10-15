package org.lc.admin.quartz.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.pool.StrConstantsPool;
import org.lc.admin.common.pool.JobConstantPool;
import org.lc.admin.common.pool.ScheduleConstantsPool;
import org.lc.admin.quartz.entities.entity.QuartzJob;
import org.lc.admin.quartz.entities.enums.QuartzJobConcurrent;
import org.lc.admin.quartz.entities.enums.QuartzJobStatus;
import org.lc.admin.quartz.job.execution.QuartzJobBanConcurrentExecution;
import org.lc.admin.quartz.job.execution.QuartzJobExecution;
import org.quartz.*;

import java.util.List;

/**
 * Description: 调度器工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-10 22:37
 */
public class SchedulerUtils {

    /**
     * Description: 创建ScheduleJob任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartzJob任务
     * @throws SchedulerException 调度异常
     * @date 2022-10-10 22:37
     */
    public static void createScheduleJob(Scheduler scheduler, QuartzJob quartzJob) throws SchedulerException {

        // job任务id以及分组数据
        Long jobId = quartzJob.getId();
        String jobGroup = quartzJob.getJobGroup();

        // job任务执行类默认QuartzJobExecution 允许并行执行
        Class<? extends Job> jobClass = QuartzJobExecution.class;
        if (ObjectUtil.equals(quartzJob.getIsConcurrent(), QuartzJobConcurrent.BAN.getValue())) {
            // job任务类设置为禁止并行执行
            jobClass = QuartzJobBanConcurrentExecution.class;
        }

        // 封装JobDetail
        JobDetail jobDetail = JobBuilder.newJob()
                // jobKey =
                .withIdentity(getJobKey(jobId, jobGroup))
                // jobClass
                .ofType(jobClass)
                .build();

        // 封装trigger为定时任务实现
        CronTrigger trigger = TriggerBuilder.newTrigger()
                // 配置job
                .forJob(jobDetail)
                // triggerKey =
                .withIdentity(getTriggerKey(jobId, jobGroup))
                // 配置调度器并设置调度失败策略
                .withSchedule(setCronScheduleMisfirePolicy(quartzJob))
                .build();

        // 放入job数据参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstantsPool.QUARTZ_JOB_PROPERTIES, quartzJob);

        // 判断是否存在当前job
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            // 防止创建时存在数据问题
            // 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        } else {
            // 不存在则抛出异常
            throw new SchedulerException(StrUtil.format(StatusMsg.QUARTZ_JOB_NOT_EXISTS_TEMPLATE.getMsg(), quartzJob.getJobName()));
        }

        // 判断任务是否过期
        if (ObjectUtil.isNotNull(CronUtils.getNextExecutionDateTime(quartzJob.getCronExpression()))) {
            // 执行调度任务
            scheduler.scheduleJob(jobDetail, trigger);
        }

        // 非立即启动则暂停任务
        if (ObjectUtil.equals(quartzJob.getStatus(), QuartzJobStatus.PAUSE.getValue())) {
            scheduler.pauseJob(getJobKey(jobId, jobGroup));
        }

    }

    /**
     * Description: 获取triggerKey触发键 <br/>
     * <p>
     * eq:
     * <pre>
     * getTriggerKey(1,"DEFAULT") = new TriggerKey("TraggerKey@1","DEFAULT")
     * </pre>
     *
     * @param jobId    job任务id
     * @param jobGroup job任务组名
     * @return {@link TriggerKey } triggerKey触发键
     * @date 2022-10-10 22:10
     */
    private static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(StrUtil.format(ScheduleConstantsPool.TRIGGER_KEY_NAME_TEMPLATE, jobId), jobGroup);
    }

    /**
     * Description: 获取Job键  <br/>
     * <p>
     * eq:
     * <pre>
     *  getJobKey(1,"DEFAULT") = new JobKey("JobKey@1","DEFAULT")
     * </pre>
     *
     * @param jobId    job任务Id
     * @param jobGroup job任务组名
     * @return {@link JobKey } job键
     * @date 2022-10-10 22:08
     */
    private static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(StrUtil.format(ScheduleConstantsPool.JOB_KEY_NAME_TEMPLATE, jobId), jobGroup);
    }

    /**
     * Description: 设置定时任务调度失败策略
     *
     * @param quartzJob quartz任务
     * @return {@link CronScheduleBuilder } 定时任务建造者
     * @date 2022-10-10 21:51
     */
    private static CronScheduleBuilder setCronScheduleMisfirePolicy(QuartzJob quartzJob) {
        // 根据任务定时cron表达式创建定时任务调度建造者
        CronScheduleBuilder cb = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());
        // 配置调度失败策略
        char misfirePolicy = quartzJob.getMisfirePolicy();
        switch (misfirePolicy) {
            case ScheduleConstantsPool.MISFIRE_DEFAULT:
                // 默认策略
                return cb;
            case ScheduleConstantsPool.MISFIRE_IGNORE_MISFIRES:
                // 立即触发执行
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstantsPool.MISFIRE_FIRE_AND_PROCEED:
                // 触发一次执行
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstantsPool.MISFIRE_DO_NOTHING:
                // 不触发立即执行
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Description: 修改ScheduleJob任务
     *
     * @param scheduler    调度器
     * @param oldQuartzJob 旧quartzJob任务
     * @param newQuartzJob 新quartzJob任务
     * @throws SchedulerException 调度异常
     * @date 2022-10-11 09:04
     */
    public static void updateScheduleJob(Scheduler scheduler, QuartzJob oldQuartzJob, QuartzJob newQuartzJob) throws SchedulerException {
        // 旧job任务是否存在
        JobKey oldJobKey = getJobKey(oldQuartzJob.getId(), oldQuartzJob.getJobGroup());
        if (scheduler.checkExists(oldJobKey)) {
            // 旧job任务存在则删除 防止存在脏数据
            deleteScheduleJob(scheduler, oldQuartzJob);
        } else {
            // 不存在则抛出异常
            throw new SchedulerException(StrUtil.format(StatusMsg.QUARTZ_JOB_NOT_EXISTS_TEMPLATE.getMsg(), oldQuartzJob.getJobName()));
        }
        // 创建新job任务
        createScheduleJob(scheduler, newQuartzJob);
    }


    /**
     * Description: 改变ScheduleJob任务状态
     *
     * @param scheduler 调度器
     * @param quartzJob quartzJob任务
     * @throws SchedulerException 调度异常
     * @date 2022-10-11 10:44
     */
    public static void changeStatusScheduleJob(Scheduler scheduler, QuartzJob quartzJob) throws SchedulerException {
        // job任务是否存在
        JobKey jobKey = getJobKey(quartzJob.getId(), quartzJob.getJobGroup());
        if (scheduler.checkExists(jobKey)) {
            Integer status = quartzJob.getStatus();
            if (ObjectUtil.equals(QuartzJobStatus.PAUSE.getValue(), status)) {
                // 暂停job任务
                scheduler.pauseJob(jobKey);
            } else if (ObjectUtil.equals(QuartzJobStatus.START.getValue(), status)) {
                // 启动job任务恢复状态
                scheduler.resumeJob(jobKey);
            }
        } else {
            // 不存在则抛出异常
            throw new SchedulerException(StrUtil.format(StatusMsg.QUARTZ_JOB_NOT_EXISTS_TEMPLATE.getMsg(), quartzJob.getJobName()));
        }

    }

    /**
     * Description: 运行ScheduleJob任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartzJob任务
     * @throws SchedulerException 调度异常
     * @date 2022-10-11 10:43
     */
    public static void runScheduleJob(Scheduler scheduler, QuartzJob quartzJob) throws SchedulerException {
        // job的数据map
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstantsPool.QUARTZ_JOB_PROPERTIES, quartzJob);

        // 根据jobId以及job组名获取jobKey
        JobKey jobKey = SchedulerUtils.getJobKey(quartzJob.getId(), quartzJob.getJobGroup());
        if (scheduler.checkExists(jobKey)) {
            // job任务存在则触发运行job任务
            scheduler.triggerJob(jobKey, dataMap);
        } else {
            // 不存在则抛出异常
            throw new SchedulerException(StrUtil.format(StatusMsg.QUARTZ_JOB_NOT_EXISTS_TEMPLATE.getMsg(), quartzJob.getJobName()));
        }
    }

    /**
     * Description: 删除ScheduleJob任务列表
     *
     * @param scheduler  调度器
     * @param quartzJobs quartzJob任务列表
     * @throws SchedulerException 调度异常
     * @date 2022-10-11 12:53
     */
    public static void deleteScheduleJobs(Scheduler scheduler, List<QuartzJob> quartzJobs) throws SchedulerException {
        for (QuartzJob quartzJob : quartzJobs) {
            // 遍历quartzJob任务列表后删除quartzJob任务
            deleteScheduleJob(scheduler, quartzJob);
        }
    }

    /**
     * Description: 删除ScheduleJob任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartzJob任务
     * @throws SchedulerException 调度异常
     * @date 2022-10-11 12:53
     */
    public static void deleteScheduleJob(Scheduler scheduler, QuartzJob quartzJob) throws SchedulerException {
        // 根据jobId以及job组名获取jobKey
        JobKey jobKey = getJobKey(quartzJob.getId(), quartzJob.getJobGroup());
        // 检查是否存在jobKey
        if (scheduler.checkExists(jobKey)) {
            // 存在jobKey则先暂停任务
            scheduler.pauseJob(jobKey);
            // 调整为非计划工作
            scheduler.unscheduleJob(getTriggerKey(quartzJob.getId(), quartzJob.getJobGroup()));
            // 删除任务
            scheduler.deleteJob(jobKey);
        } else {
            // 不存在则抛出异常
            throw new SchedulerException(StrUtil.format(StatusMsg.QUARTZ_JOB_NOT_EXISTS_TEMPLATE.getMsg(), quartzJob.getJobName()));
        }
    }

    /**
     * Description: 判断任务调用目标字符串是否属于白名单
     *
     * @param jobInvokeTarget 调用工作目标
     * @return boolean true 属于白名单 false 不数白名单
     * @date 2022-10-12 13:55
     */
    public static boolean isWhiteListContains(String jobInvokeTarget) {
        // 校验的目标字符串
        String target = jobInvokeTarget;
        // 获取bean名称
        String beanName = StrUtil.subBefore(jobInvokeTarget, StrConstantsPool.POINT_SEPARATOR, true);
        if (StrUtil.count(beanName, StrConstantsPool.POINT_SEPARATOR) <= 1) {
            // `.`字符的数量小于等于1则说明为 spring bean
            Object bean = SpringUtil.getBean(StrUtil.split(jobInvokeTarget, StrConstantsPool.POINT_SEPARATOR).get(0));
            // 获取全类限定名
            target = bean.getClass().getPackage().getName();
        }
        // 判断目标字符串是否属于白名单
        return StrUtil.containsAnyIgnoreCase(target, JobConstantPool.JOB_WHITELIST_STR);
    }


}
