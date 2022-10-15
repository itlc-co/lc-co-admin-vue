package org.lc.admin.common.exec;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.pool.StatusCode;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Description: 服务异常
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:42
 */
public  class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 62233201234567892L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(String message) {
        this.code = StatusCode.SERVICE_ERROR_CODE;
        this.message = message;
    }

    public ServiceException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(StatusMsg statusMsg) {
        this(statusMsg.getCode(), statusMsg.getMsg());
    }

    public String getDetailMessage() {
        return StrUtil.join("\n", Arrays.stream(super.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()));
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    public ServiceException setDetailMessage() {
        this.detailMessage = StrUtil.join("\n", Arrays.stream(super.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()));
        return this;
    }

    public ServiceException setDetailMessage(Exception e) {
        this.detailMessage = StrUtil.join("\n", Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()));
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getCode() {
        return code;
    }
}

