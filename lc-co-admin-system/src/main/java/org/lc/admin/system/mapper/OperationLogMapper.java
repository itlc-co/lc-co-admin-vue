package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.bo.OperationLogBo;
import org.lc.admin.system.entities.entity.OperationLog;
import org.lc.admin.system.entities.request.OperationLogRequest;

import java.util.List;

/**
 * Description: 操作日志mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 19:03
 */
public interface OperationLogMapper {
    /**
     * Description: 查询操作日志列表
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link OperationLog }> 操作日志列表
     * @date 2022-09-22 19:04
     */
    List<OperationLog> selectOperationLogList(OperationLogRequest requestParam);

    /**
     * Description: 通过操作日志id列表删除操作日志数据
     *
     * @param operationIds 操作日志id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-22 19:53
     */
    Integer deleteOperationLogByIds(@Param("operationIds") List<Long> operationIds);

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
     * @date 2022-09-23 22:38
     */
    List<OperationLogBo> selectOperationLogBoList(OperationLogRequest requestParam);

    /**
     * Description: 根据操作日志实体添加操作日志数据
     *
     * @param operationLog 操作日志实体
     * @return {@link Integer } 记录数
     * @date 2022-10-02 21:06
     */
    Integer insertOperationLog(OperationLog operationLog);

    /**
     * Description: 通过操作日志id删除操作日志数据
     *
     * @param operationId 操作日志id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:39
     */
    Integer deleteOperationLogById(@Param("operationId") Long operationId);
}
