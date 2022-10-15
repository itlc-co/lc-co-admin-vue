package org.lc.admin.quartz.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.quartz.entities.bo.QuartzJobBo;
import org.lc.admin.quartz.entities.entity.QuartzJob;
import org.lc.admin.quartz.entities.request.QuartzJobRequest;

import java.util.List;

/**
 * Description: 定时任务mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:43
 */
public interface QuartzJobMapper {

    /**
     * Description: 根据任务数据插入任务数据
     *
     * @param quartzJob 任务数据
     * @return {@link Integer }  记录数
     * @date 2022-10-11 13:14
     */
    Integer insertQuartzJob(QuartzJob quartzJob);

    /**
     * Description: 根据任务id查询任务数据
     *
     * @param jobId 任务id
     * @return {@link QuartzJob } quartz任务数据
     * @date 2022-10-11 12:57
     */
    QuartzJob selectQuartzJobById(@Param("jobId") Long jobId);

    /**
     * Description: 根据任务数据修改任务数据
     *
     * @param quartzJob 任务数据
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:32
     */
    Integer updateQuartzJob(QuartzJob quartzJob);

    /**
     * Description: 根据任务请求参数查询任务数据列表
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJob }> 任务数据列表
     * @date 2022-10-11 12:59
     */
    List<QuartzJob> selectQuartzJobList(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务id列表删除任务列表数据
     *
     * @param jobIds 任务id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:40
     */
    Integer deleteQuartzJobByIds(@Param("jobIds") List<Long> jobIds);

    /**
     * Description: 根据任务id列表查询任务列表数据
     *
     * @param jobIds 任务id列表
     * @return {@link List }<{@link QuartzJob }> 任务列表数据
     * @date 2022-10-11 13:41
     */
    List<QuartzJob> selectQuartzJobListByIds(@Param("jobIds") List<Long> jobIds);

    /**
     * Description: 根据任务id删除任务列表数据
     *
     * @param jobId 任务id
     * @return {@link Integer } 记录数
     * @date 2022-10-11 13:40
     */
    Integer deleteQuartzJobById(@Param("jobId") Long jobId);

    /**
     * Description: 根据请求参数查询任务bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobBo }> 任务bo列表数据
     * @date 2022-10-11 17:23
     */
    List<QuartzJobBo> selectQuartzJobBoList(QuartzJobRequest requestParam);

    /**
     * Description: 根据任务名称与任务组名查询任务数据
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     * @return {@link QuartzJob } 任务数据
     * @date 2022-10-12 19:22
     */
    QuartzJob selectQuartzJobByNameAndGroup(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup);
}
