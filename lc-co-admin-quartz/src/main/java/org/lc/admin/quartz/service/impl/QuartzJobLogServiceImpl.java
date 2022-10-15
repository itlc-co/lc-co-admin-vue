package org.lc.admin.quartz.service.impl;

import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.quartz.entities.bo.QuartzJobLogBo;
import org.lc.admin.quartz.entities.entity.QuartzJobLog;
import org.lc.admin.quartz.entities.request.QuartzJobLogRequest;
import org.lc.admin.quartz.mapper.QuartzJobLogMapper;
import org.lc.admin.quartz.service.QuartzJobLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 定时任务日志service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 13:38
 */
@Service
public class QuartzJobLogServiceImpl implements QuartzJobLogService {

    @Resource
    private QuartzJobLogMapper quartzJobLogMapper;

    /**
     * Description: 根据任务日志添加任务日志数
     *
     * @param quartzJobLog 任务日志
     * @return {@link Integer } 记录数
     * @date 2022-10-12 13:36
     */
    @Override
    public Integer addQuartzJobLog(QuartzJobLog quartzJobLog) {
        return quartzJobLogMapper.insertQuartzJobLog(quartzJobLog);
    }

    /**
     * Description: 根据请求参数查询任务日志列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobLog }> 任务日志列表数据
     * @date 2022-10-12 13:23
     */
    @Override
    public List<QuartzJobLog> selectQuartzJobLogList(QuartzJobLogRequest requestParam) {
        return quartzJobLogMapper.selectQuartzJobLogList(requestParam);
    }

    /**
     * Description: 根据任务日志id查询任务日志数据
     *
     * @param jobLogId 任务日志id
     * @return {@link QuartzJobLog } 任务日志数据
     * @date 2022-10-12 13:27
     */
    @Override
    public QuartzJobLog selectQuartzJobLogById(Long jobLogId) {
        return quartzJobLogMapper.selectQuartzJobLogById(jobLogId);
    }

    /**
     * Description: 根据任务日志id列表删除任务日志列表数据
     *
     * @param jobLogIds 任务日志id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-12 13:30
     */
    @Override
    public Integer deleteQuartzJobLogByIds(List<Long> jobLogIds) {
        return quartzJobLogMapper.deleteQuartzJobLogByIds(jobLogIds);
    }

    /**
     * Description: 根据任务日志id删除任务日志数据
     *
     * @param jobLogId 任务日志id
     * @return {@link Integer } 记录数
     * @date 2022-10-12 13:33
     */
    @Override
    public Integer deleteQuartzJobLogById(Long jobLogId) {
        return quartzJobLogMapper.deleteQuartzJobLogById(jobLogId);
    }

    /**
     * Description: 清空任务日志数据接口
     *
     * @date 2022-10-12 13:34
     */
    @Override
    public void cleanQuartJobLog() {
        quartzJobLogMapper.cleanQuartJobLog();
    }

    /**
     * Description: 根据请求参数查询任务日志bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link QuartzJobLogBo }> 任务日志bo列表数据
     * @date 2022-10-12 13:35
     */
    @Override
    public List<QuartzJobLogBo> selectQuartzJobLogBoList(QuartzJobLogRequest requestParam) {
        return quartzJobLogMapper.selectQuartzJobLogBoList(requestParam);
    }

    /**
     * Description: 根据请求参数导出任务日志列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-12 13:34
     */
    @Override
    public void export(QuartzJobLogRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, selectQuartzJobLogBoList(requestParam), QuartzJobLogBo.class, "任务日志信息");
    }

}
