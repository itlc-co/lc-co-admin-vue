package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 字典异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-19 21:23
 */
public class DictException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "系统字典";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6223342789345097892L;

    public DictException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.DICT_ERROR.getCode(), msg, args);
    }

    public DictException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public DictException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.DICT_ERROR.getCode(), msg, args, throwable);
    }

    public DictException(Integer code, String msg) {
        this(code, msg, (Object) null);
    }


    public DictException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public DictException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public DictException() {
        super(MODULE_NAME, StatusMsg.DICT_ERROR, null);
    }

}

