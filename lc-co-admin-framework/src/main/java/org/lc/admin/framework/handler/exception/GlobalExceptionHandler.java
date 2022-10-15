package org.lc.admin.framework.handler.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.base.exec.BaseException;
import org.lc.admin.common.base.pool.Message;
import org.lc.admin.common.base.pool.StatusCode;
import org.lc.admin.common.exec.DemoModeException;
import org.lc.admin.common.exec.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 全局异常处理
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:44
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 4XX异常日志模板
     */
    public static final String LOG_4XX_TEMPLATE = "请求地址:[{}],发生{}异常,异常信息:[{\"code\":{},\"message\":{}}].";
    /**
     * 自定义异常日志模板
     */
    public static final String LOG_CUSTOM_TEMPLATE = "请求地址:{},发生自定义{}异常[异常码:{},异常消息:{}].";
    /**
     * 自定义模块异常日志模板
     */
    public static final String LOG_CUSTOM_MODULE_TEMPLATE = "请求地址:{},发生自定义{}模块异常[异常码:{},异常消息:{}].";

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Description: 捕获处理404找不到异常
     *
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-09-06 10:08
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultResponse noHandlerFoundException(HttpServletRequest request) {
        // 请求地址uri
        String uri = request.getRequestURI();
        // 异常状态码与消息
        String message = StrUtil.format(Message.NOT_FOUND, uri);
        Integer code = StatusCode.NOT_FOUND;
        // 记录404异常日志
        log.error(StrUtil.format(LOG_4XX_TEMPLATE, uri, 404, code, message));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }

    /**
     * Description: 捕获处理405方法不被允许异常
     *
     * @param request 请求
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-09-06 10:08
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultResponse httpRequestMethodNotSupportedException(HttpServletRequest request) {
        // 请求地址uri
        String uri = request.getRequestURI();
        // 异常状态码与消息
        String message = StrUtil.format(Message.METHOD_NOT_ALLOWED, uri, request.getMethod());
        Integer code = StatusCode.METHOD_NOT_ALLOWED;
        // 记录405异常日志
        log.error(StrUtil.format(LOG_4XX_TEMPLATE, uri, 405, code, message));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }

    /**
     * Description: 拦截处理权限不足异常
     *
     * @param request 请求
     * @return {@link ResultResponse }  异常结果集响应
     * @date 2022-10-04 21:16
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResultResponse accessDeniedException(HttpServletRequest request) {
        // 获取请求地址
        String uri = request.getRequestURI();
        // 获取权限检验异常消息以及异常状态码
        Integer code = StatusCode.ACCESS_DENIED;
        String message = StrUtil.format(Message.ACCESS_DENIED, uri);
        // 记录异常日志
        log.error(StrUtil.format(LOG_4XX_TEMPLATE, uri, 403, code, message));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }

    /**
     * Description: 拦截处理自定义参数校验异常
     *
     * @param request 请求
     * @param e       参数校验异常
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-10-04 20:09
     */
    @ExceptionHandler(BindException.class)
    public ResultResponse bindException(HttpServletRequest request, BindException e) {
        // 获取请求地址
        String uri = request.getRequestURI();
        // 获取参数验证异常消息,异常状态码
        List<ObjectError> errors = e.getAllErrors();
        String message = CollUtil.isNotEmpty(errors) && ObjectUtil.isNotEmpty(errors.get(0)) ? errors.get(0).getDefaultMessage() : StrUtil.EMPTY;
        Integer code = StatusCode.PARAM_VALIDATE_ERROR;
        // 记录异常日志
        log.error(StrUtil.format(LOG_CUSTOM_TEMPLATE, uri, "参数校验", code, message));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }

    /**
     * Description: 拦截处理自定义方法参数校验异常
     *
     * @param request 请求
     * @param e       方法参数校验异常
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-10-04 20:09
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        // 获取请求地址
        String uri = request.getRequestURI();
        // 获取参数验证异常消息,异常状态码
        BindingResult bindingResult = e.getBindingResult();
        String message = ObjectUtil.isNotEmpty(bindingResult) && ObjectUtil.isNotNull(bindingResult.getFieldError()) ? bindingResult.getFieldError().getDefaultMessage() : StrUtil.EMPTY;
        Integer code = StatusCode.PARAM_VALIDATE_ERROR;
        // 记录异常日志
        log.error(StrUtil.format(LOG_CUSTOM_TEMPLATE, uri, "方法参数校验", code, message));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }


    /**
     * Description: 捕获处理系统未知异常
     *
     * @param e 未知异常
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-09-06 10:07
     */
    @ExceptionHandler(Exception.class)
    public ResultResponse exception(Exception e, HttpServletRequest request) {
        // 请求地址uri
        String uri = request.getRequestURI();
        // 异常状态码与消息
        String message = e.getMessage();
        Integer code = StatusCode.ERROR_CODE;
        String detailMessage = StrUtil.join(StrUtil.LF, Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()));
        // 记录异常日志
        log.error(StrUtil.format("请求地址:[{}],发生系统未知异常,异常信息:[{\"code\":{},\"message\":{}}].\n堆栈信息:\n[{}]", uri, code, message, detailMessage));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }

    /**
     * Description: 拦截处理未知的运行时异常
     *
     * @param e       运行时异常
     * @param request 请求
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-09-06 10:07
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultResponse runtimeException(HttpServletRequest request, RuntimeException e) {
        // 获取请求地址
        String uri = request.getRequestURI();
        // 获取异常状态码，消息，以及堆栈信息
        Integer code = StatusCode.ERROR_CODE;
        String message = e.getMessage();
        String detailMessage = StrUtil.join(StrUtil.LF, Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()));
        // 记录异常日志
        log.error(StrUtil.format("请求地址:[{}],发生系统未知运行时异常,异常信息:[{\"code\":{},\"message\":{}}].\n堆栈信息:\n[{}]", uri, code, message, detailMessage));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }

    /**
     * Description: 拦截处理系统服务异常
     *
     * @param e       服务异常
     * @param request 请求
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-09-06 10:06
     */
    @ExceptionHandler(ServiceException.class)
    public ResultResponse serviceException(HttpServletRequest request, ServiceException e) {
        // 获取请求地址
        String uri = request.getRequestURI();
        // 获取异常状态码，消息，以及堆栈信息
        Integer code = e.getCode();
        String message = e.getMessage();
        String detailMessage = e.getDetailMessage();
        // 记录异常日志
        log.error(StrUtil.format("请求地址:[{}],发生系统服务异常,异常信息:[{\"code\":{},\"message\":{}}].\n堆栈信息:\n[{}]", uri, code, message, detailMessage));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }

    /**
     * Description: 拦截处理模块异常
     *
     * @param e       自定义模块异常
     * @param request 请求
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-09-06 10:07
     */
    @ExceptionHandler(BaseException.class)
    public ResultResponse baseException(BaseException e, HttpServletRequest request) {
        // 获取请求地址
        String uri = request.getRequestURI();
        // 获取异常状态码，消息以及模块名
        Integer code = e.getCode();
        String message = e.getMessage();
        String module = e.getModule();
        // 记录异常日志
        log.error(StrUtil.format(LOG_CUSTOM_MODULE_TEMPLATE, uri, module, code, message));
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }

    /**
     * Description: 演示模式异常
     *
     * @param request 请求
     * @return {@link ResultResponse } 异常结果集响应
     * @date 2022-10-04 21:15
     */
    @ExceptionHandler(DemoModeException.class)
    public ResultResponse demoModeException(HttpServletRequest request) {
        // 获取请求地址
        String uri = request.getRequestURI();
        // 获取异常状态码，消息
        Integer code = StatusCode.DEMO_MODE_CODE;
        String message = StrUtil.format(Message.DEMO_MODE_MESSAGE, uri);
        // 记录异常日志
        log.error(message);
        // 返回异常结果集响应
        return ResultResponse.error(code, message);
    }


}
