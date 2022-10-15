package org.lc.admin.system.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Description: 操作日志Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-23 09:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("operationLogBo")
public class OperationLogBo implements Serializable {

    /**
     * 操作日志id
     */
    @Excel(name = "操作序号", width = 10)
    private Long operationId;

    /**
     * 操作模块
     */
    @Excel(name = "操作模块", orderNum = "1", width = 15)
    private String operationModule;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @Excel(name = "业务类型", orderNum = "2", width = 10, replace = {"其他_0", "新增_1", "修改_2", "删除_3", "授权_4", "导出_5", "导入_6"})
    private Integer businessType;

    /**
     * 请求方法
     */
    @Excel(name = "请求方法", orderNum = "3", width = 10)
    private String method;

    /**
     * 请求方式
     */
    @Excel(name = "请求方式", orderNum = "3", width = 10)
    private String requestMethod;

    /**
     * 操作者类别（0其它 1后台用户 2手机端用户）
     */
    @Excel(name = "操作者类别", orderNum = "4", width = 15, replace = {"其他_0", "后台用户_1", "手机端用户_2"})
    private Integer operatorType;

    /**
     * 操作人员名称
     */
    @Excel(name = "操作人员", orderNum = "5", width = 10)
    private String operatorName;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称", orderNum = "6", width = 10)
    private String deptName;

    /**
     * 请求url
     */
    @Excel(name = "操作URL", orderNum = "7", width = 20)
    private String operationUrl;

    /**
     * 操作地址
     */
    @Excel(name = "操作IP", orderNum = "8", width = 25)
    private String operationIp;

    /**
     * 操作地点
     */
    @Excel(name = "操作地址", orderNum = "9", width = 25)
    private String operationLocation;

    /**
     * 请求参数
     */
    @Excel(name = "操作参数", orderNum = "10")
    private String operationParam;

    /**
     * 返回参数
     */
    @Excel(name = "响应结果", orderNum = "11")
    private String resultResponse;

    /**
     * 错误消息
     */
    @Excel(name = "错误消息", orderNum = "12", width = 15)
    private String errorMsg;

    /**
     * 操作时间
     */
    @Excel(name = "操作时间", orderNum = "13", exportFormat = "yyyy-MM-dd HH:mm:ss", databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationTime;

    /**
     * 操作状态
     */
    @Excel(name = "操作者类别", orderNum = "14", width = 10, replace = {"正常_0", "异常_1"})
    private Integer operationStatus;


}
