package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 部门异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-19 21:23
 */
public class DeptException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "系统部门";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 622334213134567892L;

    public DeptException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.DEPT_ERROR.getCode(), msg, args);
    }

    public DeptException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public DeptException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.DEPT_ERROR.getCode(), msg, args, throwable);
    }

    public DeptException(Integer code, String msg) {
        this(code, msg, (Object) null);
    }


    public DeptException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public DeptException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public DeptException() {
        super(MODULE_NAME, StatusMsg.DEPT_ERROR, null);
    }

}

