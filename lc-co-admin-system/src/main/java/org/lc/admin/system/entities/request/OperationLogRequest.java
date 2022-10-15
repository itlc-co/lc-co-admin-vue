package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;

/**
 * Description: 操作日志请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationLogRequest extends BaseRequest {

    /**
     * 操作模块
     */
    private String operationModule;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;

    /**
     * 操作人员
     */
    private String operatorName;


}
