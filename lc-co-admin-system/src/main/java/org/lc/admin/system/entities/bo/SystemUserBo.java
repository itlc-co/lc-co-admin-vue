package org.lc.admin.system.entities.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 用户Bo
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-15 10:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelTarget(value = "systemUserBo")
public class SystemUserBo implements Serializable {

    /**
     * 用户id
     */
    @Excel(name = "用户编号", needMerge = true, width = 10)
    private Long userId;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称", orderNum = "1", width = 15, needMerge = true)
    private String userName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称", orderNum = "2", width = 15, needMerge = true)
    private String nickName;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱", orderNum = "3", width = 25, needMerge = true)
    private String email;

    /**
     * 用户手机号码
     */
    @Excel(name = "用户手机号码", orderNum = "4", width = 25, needMerge = true)
    private String phoneNumber;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", replace = {"男_0", "女_1"}, orderNum = "5", width = 10, needMerge = true)
    private Integer sex;

    /**
     * 用户状态
     */
    @Excel(name = "用户状态", replace = {"启用_0", "停用_1"}, orderNum = "6", width = 10, needMerge = true)
    private Integer status;

    /**
     * 用户登录ip
     */
    @Excel(name = "用户最后登录ip", orderNum = "7", width = 25, needMerge = true)
    private String loginIp;

    /**
     * 用户登录时间
     */
    @Excel(name = "用户最后登录时间", orderNum = "8", width = 25, needMerge = true, exportFormat = "yyyy-MM-dd HH:mm:ss", databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    /**
     * 备注
     */
    @Excel(name = "备注", needMerge = true, width = 25)
    private String remark;

    /**
     * 用户所属部门 (一对一)
     */
    @ExcelEntity(id = "systemDeptBo", name = "部门信息", show = true)
    private SystemDeptBo dept;

    /**
     * 用户角色 (一对多)
     */
    @ExcelCollection(id = "systemRoleBo", name = "角色信息", orderNum = "15")
    private List<SystemRoleBo> roles;

    /**
     * 用户岗位 (一对多)
     */
    @ExcelCollection(id = "systemPostBo", name = "岗位信息", orderNum = "16")
    private List<SystemPostBo> posts;

    /**
     * 部门id
     */
    @Excel(name = "部门编号", isColumnHidden = true)
    private Long deptId;


}
