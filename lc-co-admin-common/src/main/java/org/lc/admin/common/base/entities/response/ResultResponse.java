package org.lc.admin.common.base.entities.response;

import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.pool.ResponseConstantsPool;
import org.lc.admin.common.base.pool.StatusCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 结果集响应
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  17:58
 */
public class ResultResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 2342134123421342134L;


    /**
     * 初始化一个新创建的 ResultResponse 对象，使其表示一个空消息。
     */
    public ResultResponse() {
    }

    /**
     * 初始化一个新创建的 ResultResponse 对象
     *
     * @param code 状态码
     * @param msg  返回消息
     */
    public ResultResponse(Integer code, String msg) {
        super.put(ResponseConstantsPool.CODE_TAG, code);
        super.put(ResponseConstantsPool.MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 ResultResponse 对象
     *
     * @param code 状态码
     * @param msg  返回消息
     * @param data 数据对象
     */
    public ResultResponse(Integer code, String msg, Object data) {
        super.put(ResponseConstantsPool.CODE_TAG, code);
        super.put(ResponseConstantsPool.MSG_TAG, msg);
        if (ObjectUtil.isNotNull(data)) {
            super.put(ResponseConstantsPool.DATA_TAG, data);
        }
    }


    /**
     * 初始化一个新创建的 ResultResponse 对象
     *
     * @param statusMsg 状态码消息实体对象
     * @param data      数据对象
     */
    public ResultResponse(StatusMsg statusMsg, Object data) {
        Integer code = null;
        String msg = null;
        if (ObjectUtil.isNotNull(statusMsg)) {
            code = statusMsg.getCode();
            msg = statusMsg.getMsg();
        }
        super.put(ResponseConstantsPool.CODE_TAG, code);
        super.put(ResponseConstantsPool.MSG_TAG, msg);
        if (ObjectUtil.isNotNull(data)) {
            super.put(ResponseConstantsPool.DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息带data数据
     *
     * @return 成功消息
     */
    public static ResultResponse successData() {
        return successData(StatusMsg.SUCCESS, null);
    }

    /**
     * 返回成功消息不带data数据
     *
     * @return 成功消息
     */
    public static ResultResponse success() {
        return success(StatusMsg.SUCCESS);
    }

    /**
     * 返回成功消息不带data数据
     *
     * @param statusMsg 状态消息
     * @return 成功消息
     */
    public static ResultResponse success(StatusMsg statusMsg) {
        return success(statusMsg.getCode(), statusMsg.getMsg());
    }

    /**
     * 返回成功消息不带data数据
     *
     * @param code 状态码
     * @param msg  消息
     * @return 成功消息
     */
    public static ResultResponse success(Integer code, String msg) {
        return new ResultResponse(code, msg);
    }


    /**
     * 返回成功消息
     *
     * @param msg 消息
     * @return 成功消息
     */
    public static ResultResponse success(String msg) {
        return new ResultResponse(StatusMsg.SUCCESS, msg);
    }


    /**
     * 返回成功数据
     *
     * @param data 数据对象
     * @return 成功消息
     */
    public static ResultResponse successData(Object data) {
        return successData(StatusMsg.SUCCESS, data);
    }

    /**
     * 返回成功结果集
     *
     * @param msg 消息
     * @return 成功结果集
     */
    public static ResultResponse successData(String msg) {
        return successData(msg, null);
    }

    /**
     * 返回成功结果集
     *
     * @param msg  消息
     * @param data 数据对象
     * @return 成功结果集
     */
    public static ResultResponse successData(String msg, Object data) {
        return successData(StatusCode.SUCCESS_CODE, msg, data);
    }

    /**
     * 返回成功结果集
     *
     * @param code 状态码
     * @param msg  消息
     * @param data 数据对象
     * @return 成功结果集
     */
    public static ResultResponse successData(Integer code, String msg, Object data) {
        return new ResultResponse(code, msg, data);
    }

    /**
     * 返回成功结果集
     *
     * @param statusMsg 状态消息对象
     * @param data      数据对象
     * @return 结果集
     */
    public static ResultResponse successData(StatusMsg statusMsg, Object data) {
        return new ResultResponse(statusMsg, data);
    }

    /**
     * 返回成功结果集无data
     *
     * @param statusMsg 状态消息对象
     * @return 结果集
     */
    public static ResultResponse successData(StatusMsg statusMsg) {
        return successData(statusMsg, null);
    }


    /**
     * 返回异常结果集
     *
     * @return 异常消息
     */
    public static ResultResponse error() {
        return error(StatusMsg.ERROR, null);
    }

    /**
     * 返回异常数据
     *
     * @param data 数据对象
     * @return 异常消息
     */
    public static ResultResponse error(Object data) {
        return error(StatusMsg.ERROR, data);
    }

    /**
     * 返回异常结果集
     *
     * @param msg 消息
     * @return 异常结果集
     */
    public static ResultResponse error(String msg) {
        return error(msg, null);
    }

    /**
     * 返回异常结果集
     *
     * @param msg  消息
     * @param data 数据对象
     * @return 异常结果集
     */
    public static ResultResponse error(String msg, Object data) {
        return error(StatusCode.ERROR_CODE, msg, data);
    }


    /**
     * 返回参数异常结果集
     *
     * @param msg 消息
     * @return 异常结果集
     */
    public static ResultResponse paramValidateError(String msg) {
        return error(StatusCode.PARAM_VALIDATE_ERROR, msg);
    }


    /**
     * 返回异常结果集
     *
     * @param code 状态码
     * @param msg  消息
     * @param data 数据对象
     * @return 异常结果集
     */
    public static ResultResponse error(Integer code, String msg, Object data) {
        return new ResultResponse(code, msg, data);
    }

    /**
     * 返回异常结果集
     *
     * @param code 状态码
     * @param msg  消息
     * @return 异常结果集
     */
    public static ResultResponse error(Integer code, String msg) {
        return error(code, msg, null);
    }

    /**
     * 返回异常结果集
     *
     * @param statusMsg 状态消息对象
     * @param data      数据对象
     * @return 结果集
     */
    public static ResultResponse error(StatusMsg statusMsg, Object data) {
        return new ResultResponse(statusMsg, data);
    }

    /**
     * 返回异常结果集无data
     *
     * @param statusMsg 状态消息对象
     * @return 结果集
     */
    public static ResultResponse error(StatusMsg statusMsg) {
        return error(statusMsg, null);
    }


    /**
     * 返回失败结果集
     *
     * @return 失败消息
     */
    public static ResultResponse fail() {
        return fail(StatusMsg.FAIL, null);
    }

    /**
     * 返回失败数据
     *
     * @param data 数据对象
     * @return 失败消息
     */
    public static ResultResponse fail(Object data) {
        return fail(StatusMsg.FAIL, data);
    }

    /**
     * 返回失败结果集
     *
     * @param msg 消息
     * @return 失败结果集
     */
    public static ResultResponse fail(String msg) {
        return fail(msg, null);
    }

    /**
     * 返回失败结果集
     *
     * @param msg  消息
     * @param data 数据对象
     * @return 失败结果集
     */
    public static ResultResponse fail(String msg, Object data) {
        return fail(StatusCode.FAIL_CODE, msg, data);
    }


    /**
     * 返回失败结果集
     *
     * @param code 状态码
     * @param msg  消息
     * @param data 数据对象
     * @return 失败结果集
     */
    public static ResultResponse fail(Integer code, String msg, Object data) {
        return new ResultResponse(code, msg, data);
    }

    /**
     * 返回失败结果集
     *
     * @param statusMsg 状态消息对象
     * @param data      数据对象
     * @return 结果集
     */
    public static ResultResponse fail(StatusMsg statusMsg, Object data) {
        return new ResultResponse(statusMsg, data);
    }

    /**
     * 返回失败结果集无data
     *
     * @param statusMsg 状态消息对象
     * @return 结果集
     */
    public static ResultResponse fail(StatusMsg statusMsg) {
        return fail(statusMsg, null);
    }


    /**
     * 获取指定key的value值
     *
     * @param key 键
     * @return value 值
     */
    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public ResultResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * Description: put 参数map中所有键值对 （方便链式调用）
     *
     * @param map 参数map
     * @return org.lc.admin.common.base.entities.response.ResultResponse 结果集响应
     * @date 2022-09-01 22:14
     */
    public ResultResponse put(Map<? extends String, ?> map) {
        super.putAll(map);
        return this;
    }

}
