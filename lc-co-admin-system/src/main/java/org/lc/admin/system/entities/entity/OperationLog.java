package org.lc.admin.system.entities.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * Description: 操作日志
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class OperationLog extends BaseEntity {

    /**
     * 操作模块
     */
    private String operationModule;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private Integer operatorType;

    /**
     * 操作人员
     */
    private String operatorName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求url
     */
    private String operationUrl;

    /**
     * 操作地址
     */
    private String operationIp;

    /**
     * 操作地点
     */
    private String operationLocation;

    /**
     * 请求参数
     */
    private String operationParam;

    /**
     * 返回参数
     */
    private String resultResponse;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;


}
