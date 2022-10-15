package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 文件异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-19 21:23
 */
public class FileException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "文件";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1242373289345097892L;

    public FileException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.FILE_ERROR.getCode(), msg, args);
    }

    public FileException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public FileException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.FILE_ERROR.getCode(), msg, args, throwable);
    }

    public FileException(Integer code, String msg) {
        this(code, msg, (Object) null);
    }


    public FileException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public FileException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public FileException() {
        super(MODULE_NAME, StatusMsg.FILE_ERROR, null);
    }

}

