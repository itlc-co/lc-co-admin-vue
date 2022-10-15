package org.lc.admin.common.base.entities.enums;

import lombok.Builder;
import org.lc.admin.common.base.pool.StatusCode;

import java.io.Serializable;

/**
 * Description: 状态消息枚举
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  17:53
 */
public enum StatusMsg implements Serializable {

    /**
     * 成功
     */
    SUCCESS(StatusCode.SUCCESS_CODE, "success"),
    /**
     * 失败
     */
    FAIL(StatusCode.FAIL_CODE, "fail"),
    /**
     * 500 异常
     */
    ERROR(StatusCode.ERROR_CODE, "system error"),
    /**
     * 404（not found）
     */
    NOT_FOUND(404, "404 not found"),
    /**
     * 405  （method not allowed）
     */
    NOT_ALLOW(405, "405 method not allowed"),
    /**
     * 405  （method not allowed）
     */
    ACCESS_DENIED(403, "403 access denied"),
    /**
     * 服务异常
     */
    SERVICE_ERROR(StatusCode.SERVICE_ERROR_CODE, "service服务异常"),


    /**
     * auth认证异常
     */
    AUTH_ERROR(2020, "用户认证异常"),
    /**
     * token错误
     */
    AUTH_TOKEN_ERROR(2121, "token错误"),
    /**
     * 未认证
     */
    NOT_AUTH_ERROR(2222, "用户未认证"),
    /**
     * 认证用户密码错误
     */
    AUTH_PASSWORD_ERROR(2323, "认证用户密码错误"),
    /**
     * 用户认证存在并发访问异常
     */
    AUTH_USER_CONCURRENT_ACCESS_ERROR(2424, "用户认证存在并发访问异常"),
    /**
     * 2525 用户已被锁定
     */
    AUTH_USER_LOCKED(2525, "用户已被锁定"),
    /**
     * 系统验证码uuid异常
     **/
    AUTH_CODE_KEY_CRASH(2626, "系统验证码uuid异常"),
    /**
     * 验证码已经失效，请重新获取
     */
    AUTH_CODE_FAILURE(2727, "验证码失效"),
    /**
     * 无效验证码
     */
    AUTH_CODE_INVALID(2828, "验证码无效"),
    /**
     * 验证码错误
     */
    AUTH_CODE_VERIFICATION(2929, "验证码错误"),
    /**
     * 认证密码超过最大重试次数
     */
    AUTH_PASSWORD_EXCEEDED_MAX_RETRY_COUNT(3030, "密码错误次数超过最多允许次数"),
    /**
     * 认证密码错误次数
     */
    AUTH_PASSWORD_RETRY_COUNT(3131, "密码错误{}次"),


    /**
     * 用户异常
     */
    USER_ERROR(4040, "user用户模块异常"),
    /**
     * 用户删除
     */
    USER_DELETED(4141, "用户已被删除"),
    /**
     * 用户不存在
     */
    USER_NOT_FOUND(4242, "用户不存在"),
    /**
     * 用户手机号码存在
     */
    USER_PHONE_NUMBER_EXIST(4343, "用户手机号码已存在"),
    /**
     * 用户电子邮件存在
     */
    USER_EMAIL_EXIST(4444, "用户邮箱已存在"),
    /**
     * 用户存在
     */
    USER_EXIST(4545, "用户已存在"),
    /**
     * 用户名存在
     */
    USER_NAME_EXIST(4646, "用户名称已存在"),
    /**
     * 用户重置pwd参数错误
     */
    USER_RESET_PWD_PARAM_ERROR(4747, "新旧密码为空或者新旧密码一致"),
    /**
     * 用户旧密码错误
     */
    USER_OLD_PASSWORD_ERROR(4848, "用户旧密码错误"),
    /**
     * 用户停用
     */
    USER_DISABLE(4949, "用户已被停用"),
    /**
     * 两个密码不不一致
     */
    USER_TWO_PASSWORDS_NOT_EQUALS(5050, "两次密码不一致"),
    /**
     * 当前登录用户无法被删除
     */
    LOGIN_USER_NOT_DELETE(5151, "当前登录用户无法被删除"),
    /**
     * 没有权限访问用户数据
     */
    USER_NOT_ACCESS_DATA(5252, "没有权限访问用户数据"),
    /**
     * 没有权限访问用户数据
     */
    USER_NOT_ACCESS_ADMIN(5353, "不允许访问admin用户"),


    /**
     * 部门异常
     */
    DEPT_ERROR(6060, "dept部门模块异常"),
    /**
     * 部门已存在异常
     */
    DEPT_EXIST(6161, "部门已存在"),
    /**
     * 部门停用
     */
    DEPT_DISABLE(6262, "部门已停用"),
    /**
     * 部门不存在
     */
    DEPT_NOT_EXIST(6363, "部门不存在"),
    /**
     * 部门未停用或者含有未停用的子部门
     */
    DEPT_SUBDEPT_DISABLE(6464, "部门未停用或者含有未停用的子部门"),
    /**
     * 部门存在子部门
     */
    DEPT_EXIST_CHILDREN_DEPT(6565, "该部门存在子部门"),
    /**
     * 部门存在用户
     */
    DEPT_EXIST_USER(6666, "该部门存在用户"),
    /**
     * 没有权限访问部门数据
     */
    DEPT_NOT_ACCESS_DATA(6767, "没有权限访问部门数据"),


    /**
     * 角色模块异常
     */
    ROLE_ERROR(7070, "角色模块异常"),
    /**
     * 角色名称已存在
     */
    ROLE_NAME_NOT_UNIQUE(7171, "角色名称已存在"),
    /**
     * 角色名称已存在
     */
    ROLE_KEY_NOT_UNIQUE(7272, "角色名称已存在"),
    /**
     * 不允许访问admin角色
     */
    ROLE_NOT_ACCESS_ADMIN(7373, "不允许访问admin角色"),
    /**
     * 没有权限访问角色数据
     */
    ROLE_NOT_ACCESS_DATA(7474, "没有权限访问角色数据"),


    /**
     * 菜单模块异常
     */
    MENU_ERROR(8080, "菜单模块异常"),
    /**
     * 菜单名称已存在
     */
    MENU_NAME_NOT_UNIQUE(8181, "菜单名称已存在"),
    /**
     * 外链菜单地址错误
     */
    MENU_FRAME_PATH_FAIL(8282, "外链菜单地址错误"),
    /**
     * 父级菜单为自身菜单
     */
    MENU_PARENT_MENU_ERROR(8383, "父级菜单为自身菜单"),
    /**
     * 该菜单存在子菜单
     */
    MENU_EXIST_SUBMENU(8484, "该菜单存在子菜单"),
    /**
     * 该菜单已分配角色
     */
    MENU_EXIST_ROLE(8585, "该菜单已分配角色"),


    /**
     * 岗位模块异常
     */
    POST_ERROR(9090, "岗位模块异常"),
    /**
     * 岗位名称已存在
     */
    POST_NAME_NOT_UNIQUE(9191, "岗位名称已存在"),
    /**
     * 岗位编码已存在
     */
    POST_CODE_NOT_UNIQUE(9292, "岗位编码已存在"),
    /**
     * 岗位已分配用户
     */
    POST_EXIST_USER(9393, "岗位已分配用户"),


    /**
     * 字典模块异常
     */
    DICT_ERROR(100100, "字典模块异常"),
    /**
     * 字典名称已存在
     */
    DICT_TYPE_NOT_UNIQUE(101101, "字典名称已存在"),
    /**
     * 字典存在数据值
     */
    DICT_EXIST_DATA(102102, "字典存在数据值"),
    /**
     * 字典数据已存在
     */
    DICT_DATA_EXIST(103103, "字典数据已存在"),


    /**
     * 配置模块异常
     */
    CONFIG_ERROR(110110, "配置模块异常"),
    /**
     * 配置键已存在
     */
    CONFIG_KEY_NOT_UNIQUE(111111, "配置键已存在"),
    /**
     * 无法删除内置配置
     */
    CONFIG_BUILTIN_NOT_DELETE(112112, "无法删除内置配置"),
    /**
     * 用户默认密码配置错误
     */
    CONFIG_NOT_DEFAULT_PASSWORD(113113, "用户默认密码配置错误"),


    /**
     * 文件处理相关异常
     */
    FILE_ERROR(120120, "文件处理相关异常"),
    /**
     * 文件名称长度超过系统默认长度
     */
    FILE_UPLOAD_NAME_LEN_TOO_LONG(121121, "文件名称长度超过系统默认长度"),
    /**
     * 文件大小超过系统默认大小
     */
    FILE_UPLOAD_SIZE_TOO_BIG(122122, "文件大小超过系统默认大小"),
    /**
     * 文件后缀扩展名不支持
     */
    FILE_UPLOAD_NAME_SUFFIX_NOT_ALLOW(123123, "文件后缀扩展名不支持"),
    /**
     * 文件为空
     */
    FILE_EMPTY(124124, "文件为空"),
    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(125125, "文件不存在"),
    FILE_NAME_ILLEGAL(126126, "文件名称非法"),

    /**
     * 通告模块异常
     */
    NOTICE_ERROR(130130, "通告模块异常"),
    /**
     * 通告标题已存在
     */
    NOTICE_TIME_NOT_UNIQUE(131131, "通告标题已存在"),


    /**
     * excel导出异常
     */
    EXCEL_EXPORT_ERROR(140140, "excel导出异常"),
    /**
     * excel导入数据空
     */
    EXCEL_IMPORT_EMPTY(141141, "excel导入数据为空"),
    /**
     * excel导入文件为空
     */
    EXCEL_IMPORT_FILE_EMPTY(142142, "excel导入文件为空"),
    /**
     * excel导出导入模板文件异常
     */
    EXCEL_EXPORT_IMPORT_TEMPLATE_ERROR(143143, "excel导出导入模板文件异常"),


    /**
     * 定时任务模块异常
     */
    QUARTZ_JOB_ERROR(150150, "定时任务模块异常"),
    /**
     * 定时任务禁止远程调用
     */
    QUARTZ_JOB_BAN_REMOTE_INVOKE(151151, "定时任务禁止远程调用"),
    /**
     * 定时任务目标调用字符串非白名单内
     */
    QUARTZ_JOB_NOT_CONTAINS_WHITELIST(152153, "定时任务目标调用字符串非白名单内"),
    /**
     * 定时任务目标调用字符串存在违规
     */
    QUARTZ_JOB_INVOKE_TARGET_ERROR(153153, "定时任务目标调用字符串存在违规"),

    /**
     * 定时任务不存在
     */
    QUARTZ_JOB_NOT_EXISTS(154154, "当前定时任务不存在"),
    /**
     * 定时任务不存在
     */
    QUARTZ_JOB_NOT_EXISTS_TEMPLATE(154154, "当前定时任务[{}]不存在"),
    /**
     * 当前定时任务已存在
     */
    QUARTZ_JOB_NOT_UNIQUE(155155, "当前定时任务已存在"),


    /**
     * 代码生成器异常
     */
    GENERATOR_ERROR(160160, "代码生成器异常"),
    /**
     * 未导入表数据异常
     */
    GENERATOR_NOT_IMPORT_ERROR(161161, "未导入表数据"),
    /**
     * 不存在表结构异常
     */
    GENERATOR_NOT_TABLE_ERROR(162162, "不存在表结构");

    /**
     * 状态
     */
    private final Integer code;
    /**
     * 状态信息
     */
    private final String msg;

    /**
     * 构造方法
     *
     * @param code 状态码
     * @param msg  消息
     */
    StatusMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
