package org.lc.admin.system.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.lc.admin.system.entities.bo.OperationLogBo;
import org.lc.admin.system.entities.entity.OperationLog;
import org.lc.admin.system.entities.request.OperationLogRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Description: 操作日志service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 19:00
 */
public interface OperationLogService {
    /**
     * Description: 查询操作日志列表
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link OperationLog }> 操作日志列表
     * @date 2022-09-22 19:00
     */
    List<OperationLog> selectOperationLogList(OperationLogRequest requestParam);

    /**
     * Description: 通过操作日志id列表删除操作日志数据
     *
     * @param operationIds 操作日志id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-22 19:52
     */
    Integer deleteOperationLogByIds(List<Long> operationIds);

    /**
     * Description: 通过操作日志id删除操作日志数据
     *
     * @param operationId 操作日志id
     * @return {@link Integer } 记录数
     * @date 2022-09-22 19:52
     */
    Integer deleteOperationLogById(Long operationId);

    /**
     * Description: 清空操作日志数据
     *
     * @return {@link Integer } 记录数
     * @date 2022-09-22 19:57
     */
    Integer cleanOperationLog();

    /**
     * Description: 查询操作日志bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link OperationLogBo }> 操作日志bo列表数据
     * @date 2022-09-23 22:36
     */
    List<OperationLogBo> selectOperationLogBoList(OperationLogRequest requestParam);

    /**
     * Description: 根据操作日志实体添加操作日志数据
     *
     * @param operationLog 操作日志实体
     * @return {@link Integer } 记录数
     * @date 2022-10-02 21:04
     */
    Integer insertOperationLog(OperationLog operationLog);

    /**
     * Description: 根据请求参数导出操作记录列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-10-11 17:36
     */
    void export(OperationLogRequest requestParam, HttpServletResponse response) throws IOException;
}
