package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 角色异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-19 21:23
 */
public class RoleException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "系统角色";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6223678213134567892L;

    public RoleException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.ROLE_ERROR.getCode(), msg, args);
    }

    public RoleException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public RoleException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.ROLE_ERROR.getCode(), msg, args, throwable);
    }

    public RoleException(Integer code, String msg) {
        this(code, msg, (Object) null);
    }


    public RoleException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public RoleException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public RoleException() {
        super(MODULE_NAME, StatusMsg.ROLE_ERROR, null);
    }

}

