package org.lc.admin.common.base.exec;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;

/**
 * Description: 异常基类
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  17:52
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String msg;

    public BaseException(String module, Integer code, String msg, Object[] args) {
        this(module, code, msg, args, null);
    }

    public BaseException(String module, StatusMsg statusMsg, Object[] args) {
        this(module, statusMsg.getCode(), statusMsg.getMsg(), args);
    }

    public BaseException(String module, StatusMsg statusMsg, Object[] args, Throwable e) {
        this(module, statusMsg.getCode(), statusMsg.getMsg(), args, e);
    }

    public BaseException(String module, Integer code, String msg, Object[] args, Throwable e) {
        super(msg, e);
        this.module = module;
        this.code = code;
        this.args = args;
        this.msg = msg;
    }

    public String getModule() {
        return module;
    }

    public Integer getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String getMessage() {
        return StrUtil.format(msg, args);
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getMsg() {
        return getMessage();
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
