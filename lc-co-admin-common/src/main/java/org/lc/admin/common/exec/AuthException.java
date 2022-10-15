package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 认证授权异常
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:41
 */
public class AuthException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "用户认证";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 17324513323123L;

    public AuthException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.AUTH_ERROR.getCode(), msg, args);
    }

    public AuthException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public AuthException(BaseException exception) {
        this(exception.getCode(), exception.getMessage());
    }

    public AuthException(BaseException exception, Object... args) {
        this(exception.getCode(), exception.getMessage(), args);
    }

    public AuthException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public AuthException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }


    public AuthException() {
        super(MODULE_NAME, StatusMsg.AUTH_ERROR, null);
    }

}

