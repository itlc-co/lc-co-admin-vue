package org.lc.admin.common.entities.enums;

/**
 * Description: 用户状态枚举
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:09
 */
public enum OnlineStatus {

    /**
     * 用户状态
     */
    on_line("在线"), off_line("离线");

    private final String info;

    OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
