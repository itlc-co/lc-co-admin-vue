package org.lc.admin.framework.async.service;

import org.lc.admin.system.entities.entity.OperationLog;
import org.lc.admin.system.entities.request.AccessRecordRequest;

/**
 * Description: 异步任务service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 22:37
 */
public interface AsyncTaskService {
    /**
     * Description: 根据请求参数添加访问记录异步任务
     *
     * @param requestParam 请求参数
     * @date 2022-09-28 10:00
     */
    void addAccessRecord(AccessRecordRequest requestParam);

    /**
     * Description: 根据操作日志添加操作日志数据
     *
     * @param operationLog 操作日志
     * @date 2022-10-02 21:44
     */
    void addOperationLog(OperationLog operationLog);
}
