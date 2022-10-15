package org.lc.admin.common.exec;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.exec.BaseException;

/**
 * Description: 代码生成器异常
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:03
 */
public class GeneratorException extends BaseException {

    /**
     * 模块名
     */
    private static final String MODULE_NAME = "代码生成器";

    /**
     * 序列号
     */
    private static final long serialVersionUID = 320201200401025304L;

    public GeneratorException(String msg, Object... args) {
        super(MODULE_NAME, StatusMsg.GENERATOR_ERROR.getCode(), msg, args);
    }

    public GeneratorException(String msg, Throwable throwable, Object... args) {
        super(MODULE_NAME, StatusMsg.GENERATOR_ERROR.getCode(), msg, args, throwable);
    }

    public GeneratorException(Integer code, String msg) {
        this(MODULE_NAME, code, msg, null);
    }

    public GeneratorException(Integer code, String msg, Object... args) {
        super(MODULE_NAME, code, msg, args);
    }

    public GeneratorException(StatusMsg statusMsg) {
        super(MODULE_NAME, statusMsg, null);
    }

    public GeneratorException(StatusMsg statusMsg, Object... args) {
        super(MODULE_NAME, statusMsg, args);
    }

    public GeneratorException() {
        super(MODULE_NAME, StatusMsg.GENERATOR_ERROR, null);
    }


}
