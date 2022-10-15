package org.lc.admin.system.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 配置Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-23 09:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("systemConfigBo")
public class SystemConfigBo implements Serializable {

    /**
     * 配置序号
     */
    @Excel(name = "配置序号", width = 10)
    private Long configId;

    /**
     * 配置名称
     */
    @Excel(name = "配置名称", orderNum = "1", width = 35)
    private String configName;

    /**
     * 配置键名
     */
    @Excel(name = "配置键名", orderNum = "2", width = 25)
    private String configKey;

    /**
     * 配置值
     */
    @Excel(name = "配置值", orderNum = "3", width = 15)
    private String configValue;

    /**
     * 配置模块
     */
    @Excel(name = "配置模块", orderNum = "4", width = 15)
    private String configModule;

    /**
     * 系统内置（true是 false否）
     */
    @Excel(name = "系统内置", orderNum = "5", replace = {"是_Y", "否_N"}, width = 10)
    private Character isBuiltin;

}
