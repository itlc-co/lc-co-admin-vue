package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 配置异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-19 21:23
 */
public class ConfigException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "系统配置";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6223342789789567892L;

    public ConfigException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.CONFIG_ERROR.getCode(), msg, args);
    }

    public ConfigException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public ConfigException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.CONFIG_ERROR.getCode(), msg, args, throwable);
    }

    public ConfigException(Integer code, String msg) {
        this(code, msg, (Object) null);
    }


    public ConfigException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public ConfigException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public ConfigException() {
        super(MODULE_NAME, StatusMsg.CONFIG_ERROR, null);
    }

}

