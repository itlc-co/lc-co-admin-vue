package org.lc.admin.system.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 字典数据Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 00:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("systemDictDataBo")
public class SystemDictDataBo implements Serializable {

    /**
     * 字典数据id
     */
    @Excel(name = "数据id", width = 10)
    private Long dataId;

    /**
     * 字典标签
     */
    @Excel(name = "字典标签", orderNum = "1", width = 15)
    private String dictLabel;

    /**
     * 字典键值
     */
    @Excel(name = "字典值", orderNum = "2", width = 10)
    private String dictValue;

    /**
     * 字典类型
     */
    @Excel(name = "字典类型", orderNum = "3", width = 10)
    private String dictType;

    @Excel(name = "字典模块", orderNum = "4", width = 15)
    private String dictModule;

    /**
     * 样式属性（其他样式扩展）
     */
    @Excel(name = "样式属性", orderNum = "5", width = 15)
    private String cssClass;

    /**
     * 表格字典样式
     */
    @Excel(name = "字典样式", orderNum = "6", width = 15)
    private String listClass;

    /**
     * 是否默认（true是 false否）
     */
    @Excel(name = "默认", orderNum = "7", width = 15, replace = {"是_true", "否_false"})
    private Boolean isDefault;


    @Excel(name = "数据状态", orderNum = "8", width = 10, replace = {"正常_0", "停用_1"})
    private Integer dataStatus;

}
