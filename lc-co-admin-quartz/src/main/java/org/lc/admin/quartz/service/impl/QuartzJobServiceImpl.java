package org.lc.admin.quartz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.exec.QuartzJobException;
import org.lc.admin.common.exec.ServiceException;
import org.lc.admin.common.pool.JobConstantPool;
import org.lc.admin.common.pool.UserConstantsPool;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.quartz.entities.bo.QuartzJobBo;
import org.lc.admin.quartz.entities.entity.QuartzJob;
import org.lc.admin.quartz.entities.enums.QuartzJobStatus;
import org.lc.admin.quartz.entities.request.QuartzJobRequest;
import org.lc.admin.quartz.mapper.QuartzJobMapper;
import org.lc.admin.quartz.service.QuartzJobService;
import org.lc.admin.quartz.utils.SchedulerUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 定时任务service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:13
 */
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    @Resource
    private QuartzJobMapper quartzJobMapper;

    @Resource
    private Scheduler scheduler;

    /**
     * Description: 根据任务请求参数添加任务数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:09
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addQuartzJob(QuartzJobRequest requestParam) {
        // 根据请求参数插入job任务数据并返回job任务
        QuartzJob quartzJob = insertQuartzJob(requestParam);

        // job任务不为空则插入成功设置row为1
        int row = ObjectUtil.isNotNull(quartzJob) ? 1 : 0;

        if (row > 0) {
            // row大于0则创建ScheduleJob任务
            try {
                SchedulerUtils.createScheduleJob(scheduler, quartzJob);
            } catch (SchedulerException schedulerException) {
                RuntimeException e = new ServiceException(schedulerException.getMessage());
                if (StrUtil.equalsIgnoreCase(schedulerException.getMessage(), StatusMsg.QUARTZ_JOB_NOT_EXISTS.getMsg())) {
                    // 不存在当前任务异常
                    e = new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_EXISTS);
                }
                throw e;
            }
        }
        return row;
    }

    /**
     * Description: 检查目标调用字符串是否合理
     *
     * @param requestParam 请求参数
     * @return {@link StatusMsg } 状态消息 null:合理  not null:不合理
     * @date 2022-10-12 14:13
     */
    @Override
    public StatusMsg checkJobInvokeTarget(QuartzJobRequest requestParam) {
        StatusMsg statusMsg = null;
        String jobInvokeTarget = requestParam.getJobInvokeTarget();
        if (StrUtil.containsAnyIgnoreCase(jobInvokeTarget, JobConstantPool.REMOTE_INVOKE_PRE_STR)) {
            // 是否为远程调用
            statusMsg = StatusMsg.QUARTZ_JOB_BAN_REMOTE_INVOKE;
        } else if (StrUtil.containsAnyIgnoreCase(jobInvokeTarget, JobConstantPool.JOB_ERROR_STR)) {
            // 是否存在违规字符串
            statusMsg = StatusMsg.QUARTZ_JOB_INVOKE_TARGET_ERROR;
        } else if (!SchedulerUtils.isWhiteListContains(jobInvokeTarget)) {
            // 是否不在白名单内
            statusMsg = StatusMsg.QUARTZ_JOB_NOT_CONTAINS_WHITELIST;
        }
        return statusMsg;
    }

    /**
     * Description: 根据任务请求参数插入任务数据
     *
     * @param requestParam 请求参数
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-11 13:13
     */
    @Override
    public QuartzJob insertQuartzJob(QuartzJobRequest requestParam) {
        if (checkJobUnique(requestParam)) {
            throw new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_UNIQUE);
        }
        // bean实体转换 job任务请求参数实体装job任务实体
        QuartzJob quartzJob = BeanUtil.toBean(requestParam, QuartzJob.class);
        // 设置创建、修改者
        quartzJob.setCreateBy(ObjectUtil.defaultIfNull(SecurityUtils.getUserName(), UserConstantsPool.DEFAULT_AUTH_USER_NAME));
        quartzJob.setUpdateBy(ObjectUtil.defaultIfNull(SecurityUtils.getUserName(), UserConstantsPool.DEFAULT_AUTH_USER_NAME));
        // 根据job任务插入job任务数据
        return insertQuartzJob(quartzJob);
    }

    /**
     * Description: 根据请求参数检查任务唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-10-12 19:21
     */
    private boolean checkJobUnique(QuartzJobRequest requestParam) {
        // 获取jobId 不存在则为-1
        Long jobId = ObjectUtil.defaultIfNull(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getJobId()), -1L);
        // 根据任务名称和任务组名确定任务数据
        QuartzJob quartzJob = selectQuartzJobByNameAndGroup(requestParam.getJobName(), requestParam.getJobGroup());
        // 判断唯一性
        return ObjectUtil.isNotNull(quartzJob) && !ObjectUtil.equals(jobId, quartzJob.getId());
    }

    /**
     * Description: 根据任务名称与任务组名查询任务数据
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-12 19:19
     */
    @Override
    public QuartzJob selectQuartzJobByNameAndGroup(String jobName, String jobGroup) {
        return quartzJobMapper.selectQuartzJobByNameAndGroup(jobName, jobGroup);
    }

    /**
     * Description: 根据任务数据插入并返回任务数据
     *
     * @param quartzJob 任务数据
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-11 13:13
     */
    @Override
    public QuartzJob insertQuartzJob(QuartzJob quartzJob) {
        // 根据job任务插入job任务数据返回job任务
        return quartzJobMapper.insertQuartzJob(quartzJob) > 0 ? selectQuartzJobById(quartzJob.getId()) : null;
    }

    /**
     * Description: 根据任务id查询任务数据
     *
     * @param jobId 任务id
     * @return {@link QuartzJob } quartz任务数据
     * @date 2022-10-11 12:57
     */
    @Override
    public QuartzJob selectQuartzJobById(Long jobId) {
        // 根据jobId查询job任务数据
        return quartzJobMapper.selectQuartzJobById(jobId);
    }

    /**
     * Description: 根据任务请求参数编辑任务数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:30
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer editQuartzJob(QuartzJobRequest requestParam) {

        // 检查任务唯一性
        if (checkJobUnique(requestParam)) {
            throw new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_UNIQUE);
        }

        // 根据jobId查询旧job任务
        QuartzJob oldQuartzJob = selectQuartzJobById(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getJobId()));

        // 根据请求参数修改旧job任务后返回新job任务
        QuartzJob newQuartzJob = updateQuartzJob(requestParam);
        int row = ObjectUtil.isNotNull(newQuartzJob) ? 1 : 0;

        if (row > 0) {
            // 修改记录数大于0则修改ScheduleJob任务
            try {
                SchedulerUtils.updateScheduleJob(scheduler, oldQuartzJob, newQuartzJob);
            } catch (SchedulerException schedulerException) {
                RuntimeException e = new ServiceException(schedulerException.getMessage());
                if (StrUtil.equalsIgnoreCase(schedulerException.getMessage(), StatusMsg.QUARTZ_JOB_NOT_EXISTS.getMsg())) {
                    // 不存在当前任务异常
                    e = new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_EXISTS);
                }
                throw e;
            }
        }
        return row;
    }

    /**
     * Description: 根据任务请求参数修改并返回任务数据
     *
     * @param requestParam 请求参数
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-11 13:31
     */
    @Override
    public QuartzJob updateQuartzJob(QuartzJobRequest requestParam) {
        // bean实体转换 job任务请求参数实体装job任务实体
        QuartzJob quartzJob = BeanUtil.toBean(requestParam, QuartzJob.class);
        // 设置jobId
        quartzJob.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getJobId()));
        // 设置修改者
        quartzJob.setUpdateBy(ObjectUtil.defaultIfNull(SecurityUtils.getUserName(), UserConstantsPool.DEFAULT_AUTH_USER_NAME));
        // 修改job任务数据
        return updateQuartzJob(quartzJob);
    }


    /**
     * Description: 根据任务数据修改并返回任务数据
     *
     * @param quartzJob 任务数据
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-11 13:32
     */
    @Override
    public QuartzJob updateQuartzJob(QuartzJob quartzJob) {
        // 根据job任务修改job任务数据返回修改后的job任务
        return quartzJobMapper.updateQuartzJob(quartzJob) > 0 ? selectQuartzJobById(quartzJob.getId()) : null;
    }

    /**
     * Description: 根据任务请求参数修改任务状态
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:34
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer changeStatus(QuartzJobRequest requestParam) {
        // 根据请求参数获取jobId
        Long jobId = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getJobId());
        // 根据jobId查询job任务
        QuartzJob quartzJob = selectQuartzJobById(jobId);
        // job任务为null则不存在当前任务
        if (ObjectUtil.isNull(quartzJob)) {
            throw new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_EXISTS);
        }
        // 设置为job任务状态
        quartzJob.setStatus(ObjectUtil.defaultIfNull(requestParam.getStatus(), QuartzJobStatus.START.getValue()));
        // 修改job任务返回记录数
        Integer row = quartzJobMapper.updateQuartzJob(quartzJob);
        if (row > 0) {
            // 修改记录数大于0则修改ScheduleJob任务状态
            try {
                SchedulerUtils.changeStatusScheduleJob(scheduler, quartzJob);
            } catch (SchedulerException schedulerException) {
                RuntimeException e = new ServiceException(schedulerException.getMessage());
                if (StrUtil.equalsIgnoreCase(schedulerException.getMessage(), StatusMsg.QUARTZ_JOB_NOT_EXISTS.getMsg())) {
                    // 不存在当前任务异常
                    e = new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_EXISTS);
                }
                throw e;
            }
        }
        return row;
    }

    /**
     * Description: 根据任务请求参数查询任务数据列表
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJob }> 任务数据列表
     * @date 2022-10-11 12:59
     */
    @Override
    public List<QuartzJob> selectQuartzJobList(QuartzJobRequest requestParam) {
        // 根据请求参数插值job任务列表数据
        return quartzJobMapper.selectQuartzJobList(requestParam);
    }

    /**
     * Description: 根据任务id列表查询任务列表数据
     *
     * @param jobIds 任务id列表
     * @return {@link List }<{@link QuartzJob }> 任务列表数据
     * @date 2022-10-11 13:43
     */
    @Override
    public List<QuartzJob> selectQuartzJobListByIds(List<Long> jobIds) {
        return quartzJobMapper.selectQuartzJobListByIds(jobIds);
    }

    /**
     * Description: 根据任务请求参数运行任务
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:36
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer run(QuartzJobRequest requestParam) {
        // 根据请求参数获取jobId
        Long jobId = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getJobId());
        // 根据jobId查询job任务
        QuartzJob quartzJob = selectQuartzJobById(jobId);
        // job任务为null则不存在当前任务
        if (ObjectUtil.isNull(quartzJob)) {
            throw new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_EXISTS);
        }
        // 设置为job任务为启动状态
        quartzJob.setStatus(QuartzJobStatus.START.getValue());
        // 修改job任务返回记录数
        Integer row = quartzJobMapper.updateQuartzJob(quartzJob);
        if (row > 0) {
            // 记录数大于0则运行ScheduleJob任务
            try {
                SchedulerUtils.runScheduleJob(scheduler, quartzJob);
            } catch (SchedulerException schedulerException) {
                RuntimeException e = new ServiceException(schedulerException.getMessage());
                if (StrUtil.equalsIgnoreCase(schedulerException.getMessage(), StatusMsg.QUARTZ_JOB_NOT_EXISTS.getMsg())) {
                    // 不存在当前任务异常
                    e = new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_EXISTS);
                }
                throw e;
            }
        }
        return row;
    }

    /**
     * Description: 根据任务id列表删除任务列表数据
     *
     * @param jobIds 任务id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:38
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteQuartzJobByIds(List<Long> jobIds) {
        // 根据任务id列表删除任务数据
        Integer row = quartzJobMapper.deleteQuartzJobByIds(jobIds);
        if (row > 0) {
            // 根据任务id列表查询任务数据后删除ScheduleJob任务数据
            List<QuartzJob> quartzJobs = selectQuartzJobListByIds(jobIds);
            try {
                SchedulerUtils.deleteScheduleJobs(scheduler, quartzJobs);
            } catch (SchedulerException schedulerException) {
                RuntimeException e = new ServiceException(schedulerException.getMessage());
                if (StrUtil.equalsIgnoreCase(schedulerException.getMessage(), StatusMsg.QUARTZ_JOB_NOT_EXISTS.getMsg())) {
                    // 不存在当前任务异常
                    e = new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_EXISTS);
                }
                throw e;
            }
        }
        return row;
    }

    /**
     * Description: 根据任务id删除任务列表数据
     *
     * @param jobId 任务id
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:39
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteQuartzJobById(Long jobId) {
        // 根据任务id删除任务数据
        Integer row = quartzJobMapper.deleteQuartzJobById(jobId);
        if (row > 0) {
            // 根据任务id查询任务数据后删除ScheduleJob任务数据
            QuartzJob quartzJob = selectQuartzJobById(jobId);
            try {
                SchedulerUtils.deleteScheduleJob(scheduler, quartzJob);
            } catch (SchedulerException schedulerException) {
                RuntimeException e = new ServiceException(schedulerException.getMessage());
                if (StrUtil.equalsIgnoreCase(schedulerException.getMessage(), StatusMsg.QUARTZ_JOB_NOT_EXISTS.getMsg())) {
                    // 不存在当前任务异常
                    e = new QuartzJobException(StatusMsg.QUARTZ_JOB_NOT_EXISTS);
                }
                throw e;
            }
        }
        return row;
    }

    /**
     * Description: 根据请求参数导出任务bo列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-11 17:21
     */
    @Override
    public void export(QuartzJobRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, selectQuartzJobBoList(requestParam), QuartzJobBo.class, "任务信息");
    }

    /**
     * Description: 根据请求参数查询任务bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobBo }>  任务bo列表数据
     * @date 2022-10-11 17:22
     * @date 2022-10-11 17:23
     */
    @Override
    public List<QuartzJobBo> selectQuartzJobBoList(QuartzJobRequest requestParam) {
        return quartzJobMapper.selectQuartzJobBoList(requestParam);
    }

}
