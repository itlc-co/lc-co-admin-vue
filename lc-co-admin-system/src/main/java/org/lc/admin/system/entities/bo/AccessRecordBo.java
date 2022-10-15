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
 * Description: 访问记录Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-23 09:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("accessRecordBo")
public class AccessRecordBo implements Serializable {

    /**
     * 序号id
     */
    @Excel(name = "序号")
    private Long accessId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号", orderNum = "1", width = 15)
    private String userName;

    /**
     * 登录状态 0成功 1失败
     */
    @Excel(name = "登录状态", orderNum = "2", width = 10, replace = {"成功_0", "失败_1"})
    private Integer accessStatus;

    /**
     * IP地址
     */
    @Excel(name = "IP地址", orderNum = "3", width = 15)
    private String ipaddr;

    /**
     * 地点
     */
    @Excel(name = "访问地址", orderNum = "5", width = 20)
    private String accessLocation;

    /**
     * 浏览器类型
     */
    @Excel(name = "浏览器", orderNum = "6", width = 20)
    private String browser;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统", orderNum = "7", width = 45)
    private String os;

    /**
     * 提示消息
     */
    @Excel(name = "提示消息", orderNum = "8", width = 20)
    private String msg;

    /**
     * 访问时间
     */
    @Excel(name = "访问时间", width = 25, orderNum = "9", exportFormat = "yyyy-MM-dd HH:mm:ss", databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessTime;

}
