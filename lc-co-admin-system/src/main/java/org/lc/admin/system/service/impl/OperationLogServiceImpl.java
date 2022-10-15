package org.lc.admin.system.service.impl;

import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.system.entities.bo.OperationLogBo;
import org.lc.admin.system.entities.entity.OperationLog;
import org.lc.admin.system.entities.request.OperationLogRequest;
import org.lc.admin.system.mapper.OperationLogMapper;
import org.lc.admin.system.service.OperationLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 操作日志service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 19:01
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {


    @Resource
    private OperationLogMapper operationLogMapper;


    /**
     * Description: 清空操作日志数据
     *
     * @return {@link Integer } 记录数
     * @date 2022-09-22 19:57
     */
    @Override
    public Integer cleanOperationLog() {
        return this.operationLogMapper.cleanOperationLog();
    }

    /**
     * Description: 根据操作日志实体添加操作日志数据
     *
     * @param operationLog 操作日志实体
     * @return {@link Integer } 记录数
     * @date 2022-10-02 21:06
     */
    @Override
    public Integer insertOperationLog(OperationLog operationLog) {
        return this.operationLogMapper.insertOperationLog(operationLog);
    }

    /**
     * Description: 查询操作日志bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link OperationLogBo }> 操作日志bo列表数据
     * @date 2022-09-23 22:38
     */
    @Override
    public List<OperationLogBo> selectOperationLogBoList(OperationLogRequest requestParam) {
        return this.operationLogMapper.selectOperationLogBoList(requestParam);
    }

    /**
     * Description: 根据请求参数导出操作记录列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-10-11 17:36
     */
    @Override
    public void export(OperationLogRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, selectOperationLogBoList(requestParam), OperationLogBo.class, "日志记录信息");
    }

    /**
     * Description: 通过操作日志id删除操作日志数据
     *
     * @param operationId 操作日志id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:39
     */
    @Override
    public Integer deleteOperationLogById(Long operationId) {
        return this.operationLogMapper.deleteOperationLogById(operationId);
    }

    /**
     * Description: 通过操作日志id列表删除操作日志数据
     *
     * @param operationIds 操作日志id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-22 19:53
     */
    @Override
    public Integer deleteOperationLogByIds(List<Long> operationIds) {
        return this.operationLogMapper.deleteOperationLogByIds(operationIds);
    }

    /**
     * Description: 查询操作日志列表
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link OperationLog }> 操作日志列表
     * @date 2022-09-22 19:01
     */
    @Override
    public List<OperationLog> selectOperationLogList(OperationLogRequest requestParam) {
        return this.operationLogMapper.selectOperationLogList(requestParam);
    }


}
