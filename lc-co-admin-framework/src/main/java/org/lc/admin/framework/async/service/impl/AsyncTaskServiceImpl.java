package org.lc.admin.framework.async.service.impl;

import org.lc.admin.framework.async.service.AsyncTaskService;
import org.lc.admin.system.entities.entity.OperationLog;
import org.lc.admin.system.entities.request.AccessRecordRequest;
import org.lc.admin.system.service.AccessRecordService;
import org.lc.admin.system.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description: 异步任务service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 22:37
 */
@Service
@Async("asyncTackExecutor")
public class AsyncTaskServiceImpl implements AsyncTaskService {

    public static final Logger log = LoggerFactory.getLogger(AsyncTaskServiceImpl.class);

    @Resource
    private AccessRecordService accessRecordService;


    @Resource
    private OperationLogService operationLogService;

    /**
     * Description: 根据操作日志实体添加操作日志数据
     *
     * @param operationLog 操作日志
     * @date 2022-10-02 21:05
     */
    @Override
    public void addOperationLog(OperationLog operationLog) {
        this.operationLogService.insertOperationLog(operationLog);
    }

    /**
     * Description: 根据请求参数添加访问记录异步任务
     *
     * @param requestParam 请求参数
     * @date 2022-09-27 23:56
     */
    @Override
    public void addAccessRecord(AccessRecordRequest requestParam) {
        this.accessRecordService.add(requestParam);
    }


}
