package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 菜单异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-19 21:23
 */
public class MenuException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "系统菜单";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 622334278934567892L;

    public MenuException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.MENU_ERROR.getCode(), msg, args);
    }

    public MenuException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public MenuException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.MENU_ERROR.getCode(), msg, args, throwable);
    }

    public MenuException(Integer code, String msg) {
        this(code, msg, (Object) null);
    }


    public MenuException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public MenuException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public MenuException() {
        super(MODULE_NAME, StatusMsg.MENU_ERROR, null);
    }

}

