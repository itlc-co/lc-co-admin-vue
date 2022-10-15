package org.lc.admin.system.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 角色Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-15 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget("systemRoleBo")
public class SystemRoleBo implements Serializable {

    /**
     * 角色id
     */
    @Excel(name = "编号", width = 10)
    private Long roleId;

    /**
     * 角色名称
     */
    @Excel(name = "名称", orderNum = "1", width = 15)
    private String roleName;

    /**
     * 角色权限
     */
    @Excel(name = "标识", orderNum = "2", width = 15)
    private String roleKey;

    /**
     * 角色状态
     */
    @Excel(name = "状态", orderNum = "3", replace = {"启用_0", "停用_1"}, width = 10)
    private Integer roleStatus;

    /**
     * 数据范围
     */
    @Excel(name = "数据范围", orderNum = "4", replace = {"所有数据权限_1", "自定义数据权限_2", "本部门数据权限_3", "本部门及以下数据权限_4", "仅本人数据权限_5"}, width = 30)
    private Integer dataScope;


}
