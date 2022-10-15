package org.lc.admin.quartz.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.quartz.entities.bo.QuartzJobLogBo;
import org.lc.admin.quartz.entities.entity.QuartzJobLog;
import org.lc.admin.quartz.entities.request.QuartzJobLogRequest;

import java.util.List;

/**
 * Description: 定时任务日志mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 14:18
 */
public interface QuartzJobLogMapper {
    /**
     * Description: 根据任务日志插入任务日志数据
     *
     * @param quartzJobLog 任务日志
     * @return {@link Integer } 记录数
     * @date 2022-10-11 14:19
     */
    Integer insertQuartzJobLog(QuartzJobLog quartzJobLog);

    /**
     * Description: 根据请求参数查询任务日志列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobLog }> 任务日志列表数据
     * @date 2022-10-12 13:24
     */
    List<QuartzJobLog> selectQuartzJobLogList(QuartzJobLogRequest requestParam);

    /**
     * Description: 根据任务日志id查询任务日志数据
     *
     * @param jobLogId 任务日志id
     * @return {@link QuartzJobLog } 任务日志数据
     * @date 2022-10-12 13:27
     */
    QuartzJobLog selectQuartzJobLogById(@Param("jobLogId") Long jobLogId);

    /**
     * Description: 根据任务日志id列表删除任务日志列表数据
     *
     * @param jobLogIds 任务日志id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-12 13:31
     */
    Integer deleteQuartzJobLogByIds(@Param("jobLogIds") List<Long> jobLogIds);

    /**
     * Description: 清空任务日志数据接口
     *
     * @date 2022-10-12 13:33
     */
    void cleanQuartJobLog();

    /**
     * Description: 根据任务日志id删除任务日志数据
     *
     * @param jobLogId 任务日志id
     * @return {@link Integer } 记录数
     * @date 2022-10-12 13:33
     */
    Integer deleteQuartzJobLogById(@Param("jobLogId") Long jobLogId);

    /**
     * Description: 根据请求参数查询任务日志bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobLogBo }> 任务日志bo列表数据
     * @date 2022-10-12 13:35
     */
    List<QuartzJobLogBo> selectQuartzJobLogBoList(QuartzJobLogRequest requestParam);
}
