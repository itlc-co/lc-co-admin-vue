package org.lc.admin.system.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 部门Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-15 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("systemDeptBo")
public class SystemDeptBo implements Serializable {

    /**
     * 部门id
     */
    @Excel(name = "编号",orderNum = "9",width = 10,needMerge = true)
    private Long deptId;

    /**
     * 部门名称
     */
    @Excel(name = "名称", orderNum = "10",width = 15,needMerge = true)
    private String deptName;

    /**
     * 负责人
     */
    @Excel(name = "负责人", orderNum = "11",width = 15,needMerge = true)
    private String leader;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话", orderNum = "12",width = 25,needMerge = true)
    private String phone;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱", orderNum = "13",width = 25,needMerge = true)
    private String email;

    /**
     * 状态
     */
    @Excel(name = "状态", orderNum = "14",replace = {"启用_0", "停用_1"},width = 10,needMerge = true)
    private Integer deptStatus;


}
