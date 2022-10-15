package org.lc.admin.framework.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.base.pool.HttpRequestMethod;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.enums.OperateStatus;
import org.lc.admin.common.entities.enums.OperatorType;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.exec.AuthException;
import org.lc.admin.common.exec.ServiceException;
import org.lc.admin.common.utils.*;
import org.lc.admin.common.utils.server.AddressUtils;
import org.lc.admin.common.utils.server.IpUtils;
import org.lc.admin.common.utils.servlet.ServletUtils;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.framework.async.service.AsyncTaskService;
import org.lc.admin.system.entities.entity.OperationLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

/**
 * Description: 操作日志aop切面处理
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 21:40
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private AsyncTaskService asyncTaskService;


    /**
     * Description: 执行完方法返回后执行
     *
     * @param joinPoint 切入点
     * @param log       日志注解
     * @param response  结果集响应
     * @date 2022-10-02 17:51
     */
    @AfterReturning(pointcut = "@annotation(log)", returning = "response")
    public void doAfterReturning(JoinPoint joinPoint, Log log, ResultResponse response) {
        handleSuccessLog(joinPoint, log, response);
    }


    /**
     * Description: 执行完方法抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param log       日志注解
     * @param e         异常
     * @date 2022-10-02 17:52
     */
    @AfterThrowing(pointcut = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, Exception e) {
        handleErrorLog(joinPoint, log, e);
    }

    /**
     * Description: 处理操作成功日志
     *
     * @param joinPoint 织入点
     * @param log       日志注解
     * @param response  结构集响应
     * @date 2022-10-02 21:23
     */
    protected void handleSuccessLog(JoinPoint joinPoint, Log log, ResultResponse response) {
        this.handleLog(joinPoint, log, response, null);
    }

    /**
     * Description: 处理日志
     *
     * @param joinPoint 织入点
     * @param log       日志注解
     * @param response  结果集响应
     * @param e         异常
     * @date 2022-10-02 21:23
     */
    protected void handleLog(JoinPoint joinPoint, Log log, ResultResponse response, Exception e) {

        // 获取用户详情
        UserDetail userDetail = SecurityUtils.getUserDetail();

        if (ObjectUtil.isNull(userDetail)) {
            // 用户详情为null抛出认证异常
            throw new AuthException(StatusMsg.AUTH_ERROR);
        }

        // 获取类名，方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        // 获取当前请求
        HttpServletRequest request = ServletUtils.getRequest();


        if (ObjectUtil.isNull(request)) {
            // 请求为null抛出服务异常
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }

        // 获取请求ip
        String ip = IpUtils.getIpAddr(request);

        OperationLog operationLog = OperationLog
                .builder()
                // 操作ip
                .operationIp(ip)
                // 操作url
                .operationUrl(request.getRequestURI())
                // 操作人名称
                .operatorName(userDetail.getUsername())
                // 操作方法名 类名.方法名()
                .method(className + "." + methodName + "()")
                // 请求方法
                .requestMethod(request.getMethod())
                // 操作地址
                .operationLocation(AddressUtils.getLocationByIp(ip))
                // 部门名称
                .deptName(userDetail.getDeptName())
                .build();

        // 处理设置注解上的参数 (请求参数，结果集响应，模块等等)
        setAnnotationParameters(joinPoint, log, operationLog, response);

        // 设置操作状态以及失败情况下的异常消息
        setStatusMessage(e, operationLog);

        // 异步将操作记录入库
        asyncTaskService.addOperationLog(operationLog);
    }

    /**
     * Description: 设置状态信息
     *
     * @param e            异常
     * @param operationLog 操作日志
     * @date 2022-10-02 21:22
     */
    private void setStatusMessage(Exception e, OperationLog operationLog) {
        // 默认操作状态成功，异常消息为null
        int status = OperateStatus.SUCCESS.ordinal();
        String msg = null;

        if (ObjectUtil.isNotNull(e)) {
            // 如果异常不为null则操作状态为异常和异常消息
            status = OperateStatus.FAIL.ordinal();
            msg = e.getMessage();
        }

        // 设置异常消息以及状态
        operationLog.setErrorMsg(msg)
                .setStatus(status);
    }


    /**
     * Description: 设置注释上的参数信息 （请求参数，结果集响应，模块等等）
     *
     * @param joinPoint    织入点
     * @param log          日志注解
     * @param operationLog 操作日志
     * @param response     结果集响应
     * @date 2022-10-02 20:51
     */
    private void setAnnotationParameters(JoinPoint joinPoint, Log log, OperationLog operationLog, ResultResponse response) {

        // 获取log注解参数
        boolean isSaveOperationParam = log.isSaveOperationParam();
        boolean isSaveResultResponse = log.isSaveResultResponse();
        String module = log.module();
        OperatorType operatorType = log.operatorType();
        BusinessType businessType = log.businessType();

        // 设置操作记录实体信息
        operationLog.setOperationModule(module)
                // 操作类型
                .setBusinessType(businessType.ordinal())
                // 操作者类型
                .setOperatorType(operatorType.ordinal());

        if (isSaveOperationParam) {
            // 开启保存参数则设置操作参数信息
            setOperationParam(joinPoint, operationLog);
        }

        if (isSaveResultResponse && ObjectUtil.isNotNull(response)) {
            // 开启保存结果集响应并且结果集响应不为null则设置操作结果集响应
            setResultResponse(operationLog, response);
        }
    }

    /**
     * Description: 设置结果集响应
     *
     * @param operationLog 操作日志
     * @param response     结果集响应
     * @date 2022-10-02 20:45
     */
    private void setResultResponse(OperationLog operationLog, ResultResponse response) {
        operationLog.setResultResponse(JSONUtil.toJsonStr(response));
    }

    /**
     * Description: 设置操作参数
     *
     * @param joinPoint    织入点
     * @param operationLog 操作日志
     * @date 2022-10-02 20:44
     */
    private void setOperationParam(JoinPoint joinPoint, OperationLog operationLog) {
        // 获取请求
        HttpServletRequest request = ServletUtils.getRequest();
        AssertUtils.isNotNull(request);
        // 请求方法
        String method = request.getMethod();
        String params = StrUtil.EMPTY;
        if (StrUtil.equalsAnyIgnoreCase(method, HttpRequestMethod.POST, HttpRequestMethod.PUT)) {
            // put，post 则操作参数为织入方法的参数即请求体中的参数
            params = getRequestBody(joinPoint);
        } else {
            // 其他请求方法操作参数为请求中的参数
            params = getRequestParams();
        }
        // 设置操作参数
        operationLog.setOperationParam(params);
    }

    /**
     * Description: 获取请求参数
     *
     * @return {@link String } 请求参数字符串
     * @date 2022-10-02 20:39
     */
    private String getRequestParams() {
        // 获取请求
        HttpServletRequest request = ServletUtils.getRequest();
        AssertUtils.isNotNull(request);

        // 封装请求参数map
        Map<String, String> paramMap = MapUtil.newHashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            paramMap.put(paramName, request.getParameter(paramName));
        }

        // 请求参数map转换json
        return JSONUtil.toJsonStr(paramMap, JSONConfig.create().setIgnoreNullValue(true).setDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Description: 获取请求体数据
     *
     * @param joinPoint 织入点
     * @return {@link String } 请求体json数据
     * @date 2022-10-02 20:39
     */
    private String getRequestBody(JoinPoint joinPoint) {
        Object[] args = ArrayUtil.filter(joinPoint.getArgs(), (arg) -> {
            Class<?> clazz = arg.getClass();
            if (clazz.isArray()) {
                // 数组类型判断是否保留 非文件请求参数
                return !clazz.getComponentType().isAssignableFrom(MultipartFile.class);
            } else if (Collection.class.isAssignableFrom(clazz)) {
                // 集合类型判断是否保留 非文件请求参数
                return !CollUtil.contains((Collection) arg, (object) -> (object instanceof MultipartFile));
            } else if (Map.class.isAssignableFrom(clazz)) {
                // map类型判断是否保留 非文件请求参数
                return !CollUtil.contains(((Map) arg).values(), (object) -> (object instanceof MultipartFile));
            }
            // 非MultipartFile 文件参数 ，HttpServletRequest 参数，HttpServletResponse 参数，BindingResult 参数
            return !(arg instanceof MultipartFile || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse
                    || arg instanceof BindingResult);
        });
        return JSONUtil.toJsonStr(args, JSONConfig.create().setIgnoreNullValue(true));
    }

    /**
     * Description: 处理操作异常日志
     *
     * @param joinPoint 织入点
     * @param log       日志注解
     * @param e         异常
     * @date 2022-10-02 20:40
     */
    protected void handleErrorLog(JoinPoint joinPoint, Log log, Exception e) {
        this.handleLog(joinPoint, log, null, e);
    }


}
