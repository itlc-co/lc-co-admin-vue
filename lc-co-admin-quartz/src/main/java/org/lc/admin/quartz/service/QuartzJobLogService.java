package org.lc.admin.quartz.service;

import org.lc.admin.quartz.entities.bo.QuartzJobLogBo;
import org.lc.admin.quartz.entities.entity.QuartzJobLog;
import org.lc.admin.quartz.entities.request.QuartzJobLogRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 定时任务日志service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 13:37
 */
public interface QuartzJobLogService {

    /**
     * Description: 根据任务日志添加任务日志数
     *
     * @param quartzJobLog 任务日志
     * @return {@link Integer } 记录数
     * @date 2022-10-12 13:36
     */
    Integer addQuartzJobLog(QuartzJobLog quartzJobLog);

    /**
     * Description: 根据请求参数查询任务日志列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobLog }> 任务日志列表数据
     * @date 2022-10-12 13:22
     */
    List<QuartzJobLog> selectQuartzJobLogList(QuartzJobLogRequest requestParam);

    /**
     * Description: 根据任务日志id查询任务日志数据
     *
     * @param jobLogId 任务日志id
     * @return {@link QuartzJobLog } 任务日志数据
     * @date 2022-10-12 13:25
     */
    QuartzJobLog selectQuartzJobLogById(Long jobLogId);

    /**
     * Description: 根据任务日志id列表删除任务日志列表数据
     *
     * @param jobLogIds 任务日志id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-12 13:29
     */
    Integer deleteQuartzJobLogByIds(List<Long> jobLogIds);

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
    Integer deleteQuartzJobLogById(Long jobLogId);

    /**
     * Description: 根据请求参数查询任务日志bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobLogBo }> 任务日志bo列表数据
     * @date 2022-10-12 13:35
     */
    List<QuartzJobLogBo> selectQuartzJobLogBoList(QuartzJobLogRequest requestParam);

    /**
     * Description: 根据请求参数导出任务日志列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-12 13:34
     */
    void export(QuartzJobLogRequest requestParam, HttpServletResponse response) throws IOException;
}
