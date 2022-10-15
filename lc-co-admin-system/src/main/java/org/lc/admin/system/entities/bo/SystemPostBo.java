package org.lc.admin.system.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 岗位Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-15 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("systemPostBo")
public class SystemPostBo implements Serializable {


    /**
     * 岗位id
     */
    @Excel(name = "编号", width = 10)
    private Long postId;

    /**
     * 岗位名称
     */
    @Excel(name = "名称", orderNum = "1",width = 15)
    private String postName;

    /**
     * 岗位编码
     */
    @Excel(name = "编码", orderNum = "2",width = 15)
    private String postCode;

    /**
     * 岗位状态
     */
    @Excel(name = "状态", orderNum = "3",replace = {"启用_0","停用_1"},width = 10)
    private Integer postStatus;


}
