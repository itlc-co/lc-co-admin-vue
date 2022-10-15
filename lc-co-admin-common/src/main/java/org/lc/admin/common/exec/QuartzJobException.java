package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 定时任务异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:03
 */
public class QuartzJobException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "定时任务";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 410201197310052419L;

    public QuartzJobException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.QUARTZ_JOB_ERROR.getCode(), msg, args);
    }

    public QuartzJobException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.QUARTZ_JOB_ERROR.getCode(), msg, args, throwable);
    }

    public QuartzJobException(Integer code, String msg) {
        this(MODULE_NAME, code, msg, null);
    }

    public QuartzJobException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public QuartzJobException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public QuartzJobException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public QuartzJobException() {
        super(MODULE_NAME, StatusMsg.QUARTZ_JOB_ERROR, null);
    }


}
