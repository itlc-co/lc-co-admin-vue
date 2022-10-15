package org.lc.admin.system.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 字典类型Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-23 09:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("systemDictTypeBo")
public class SystemDictTypeBo implements Serializable {

    /**
     * 字典id
     */
    @Excel(name = "字典编号", width = 10)
    private Long dictId;

    /**
     * 字典类型名称
     */
    @Excel(name = "字典名称", width = 15)
    private String dictName;

    /**
     * 字典类型
     */
    @Excel(name = "字典类型", width = 25)
    private String dictType;

    @Excel(name = "字典模块", width = 25)
    private String dictModule;

    /**
     * 字典状态
     */
    @Excel(name = "字典状态", replace = {"启用_0", "停用_1"}, width = 10)
    private Integer dictStatus;

}
