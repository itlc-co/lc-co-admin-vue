package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 通告异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 21:09
 */
public class NoticeException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "系统通知";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 450101197909083010L;

    public NoticeException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.NOTICE_ERROR.getCode(), msg, args);
    }

    public NoticeException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public NoticeException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.NOTICE_ERROR.getCode(), msg, args, throwable);
    }

    public NoticeException(Integer code, String msg) {
        this(code, msg, (Object) null);
    }


    public NoticeException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public NoticeException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public NoticeException() {
        super(MODULE_NAME, StatusMsg.NOTICE_ERROR, null);
    }

}
