package org.lc.admin.quartz.service;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.quartz.entities.bo.QuartzJobBo;
import org.lc.admin.quartz.entities.entity.QuartzJob;
import org.lc.admin.quartz.entities.request.QuartzJobRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 定时任务service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:09
 */
public interface QuartzJobService {

    /**
     * Description: 检查目标调用字符串是否合理
     *
     * @param requestParam 请求参数
     * @return {@link StatusMsg } 状态消息 null:合理  not null:不合理
     * @date 2022-10-12 14:15
     */
    StatusMsg checkJobInvokeTarget(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务请求参数添加任务数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:01
     */
    Integer addQuartzJob(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务名称与任务组名查询任务数据
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-12 19:20
     */
    QuartzJob selectQuartzJobByNameAndGroup(String jobName, String jobGroup);

    /**
     * Description: 根据任务请求参数插入任务数据
     *
     * @param requestParam 请求参数
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-11 13:12
     */
    QuartzJob insertQuartzJob(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务数据插入并返回任务数据
     *
     * @param quartzJob 任务数据
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-11 13:13
     */
    QuartzJob insertQuartzJob(QuartzJob quartzJob);

    /**
     * Description: 根据任务id查询任务数据
     *
     * @param jobId 任务id
     * @return {@link QuartzJob } quartz任务数据
     * @date 2022-10-11 12:56
     */
    QuartzJob selectQuartzJobById(Long jobId);

    /**
     * Description: 根据任务请求参数编辑任务数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:30
     */
    Integer editQuartzJob(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务请求参数修改并返回任务数据
     *
     * @param requestParam 请求参数
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-11 13:31
     */
    QuartzJob updateQuartzJob(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务数据修改并返回任务数据
     *
     * @param quartzJob 任务数据
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-11 13:31
     */
    QuartzJob updateQuartzJob(QuartzJob quartzJob);

    /**
     * Description: 根据任务请求参数修改任务状态
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:34
     */
    Integer changeStatus(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务请求参数查询任务数据列表
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJob }> 任务数据列表
     * @date 2022-10-11 12:58
     */
    List<QuartzJob> selectQuartzJobList(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务id列表查询任务列表数据
     *
     * @param jobIds 任务id列表
     * @return {@link List }<{@link QuartzJob }> 任务列表数据
     * @date 2022-10-12 19:20
     */
    List<QuartzJob> selectQuartzJobListByIds(List<Long> jobIds);

    /**
     * Description: 根据任务请求参数运行任务
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:35
     */
    Integer run(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务id列表删除任务列表数据
     *
     * @param jobIds 任务id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:37
     */
    Integer deleteQuartzJobByIds(List<Long> jobIds);

    /**
     * Description: 根据任务id删除任务列表数据
     *
     * @param jobId 任务id
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:37
     */
    Integer deleteQuartzJobById(Long jobId);

    /**
     * Description: 根据请求参数导出任务bo列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-11 17:21
     */
    void export(QuartzJobRequest requestParam, HttpServletResponse response) throws IOException;

    /**
     * Description: 根据请求参数查询任务bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobBo }>  任务bo列表数据
     * @date 2022-10-11 17:22
     */
    List<QuartzJobBo> selectQuartzJobBoList(QuartzJobRequest requestParam);
}
