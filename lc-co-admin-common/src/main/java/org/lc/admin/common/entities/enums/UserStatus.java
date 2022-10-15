package org.lc.admin.common.entities.enums;

/**
 * Description: 用户状态枚举
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:10
 */
public enum UserStatus {

    /**
     * 正常，停用，删除
     */
    NORMAL(0, "正常"),
    DISABLE(1, "停用"),
    DELETED(2, "删除");

    private final Integer code;
    private final String info;

    UserStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}
