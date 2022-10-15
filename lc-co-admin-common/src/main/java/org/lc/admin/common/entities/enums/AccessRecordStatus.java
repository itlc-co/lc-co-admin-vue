package org.lc.admin.common.entities.enums;

/**
 * Description: 访问记录状态枚举
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 16:01
 */
public enum AccessRecordStatus {

    /**
     * 访问成功
     */
    ACCESS_SUCCESS(0, "登录成功"),
    /**
     * 访问失败
     */
    ACCESS_FAIL(1, "登录失败"),

    /**
     * 退出登录
     */
    ACCESS_LOGOUT(0, "退出成功");

    private final Integer code;
    private final String msg;


    AccessRecordStatus(Integer code, String msg) {
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
