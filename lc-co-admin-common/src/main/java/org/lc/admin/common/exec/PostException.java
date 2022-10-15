package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 岗位异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-19 21:23
 */
public class PostException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "系统岗位";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 622334278934567892L;

    public PostException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.POST_ERROR.getCode(), msg, args);
    }

    public PostException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public PostException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.POST_ERROR.getCode(), msg, args, throwable);
    }

    public PostException(Integer code, String msg) {
        this(code, msg, (Object) null);
    }


    public PostException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public PostException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public PostException() {
        super(MODULE_NAME, StatusMsg.POST_ERROR, null);
    }

}

