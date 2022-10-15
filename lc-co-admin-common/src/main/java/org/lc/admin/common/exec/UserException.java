package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 用户异常
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:43
 */
public class UserException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "系统用户";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 622332213134567892L;

    public UserException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.USER_ERROR.getCode(), msg, args);
    }

    public UserException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.USER_ERROR.getCode(), msg, args, throwable);
    }

    public UserException(Integer code, String msg) {
        this(MODULE_NAME, code, msg, null);
    }

    public UserException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public UserException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public UserException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public UserException() {
        super(MODULE_NAME, StatusMsg.USER_ERROR, null);
    }

}

