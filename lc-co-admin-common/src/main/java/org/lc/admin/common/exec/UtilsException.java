package org.lc.admin.common.exec;

/**
 * Description:  工具类异常
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:23
 */
public class UtilsException extends RuntimeException {

    private static final long serialVersionUID = 1247656239189014183L;

    public UtilsException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilsException(String message) {
        super(message);
    }

    public UtilsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
