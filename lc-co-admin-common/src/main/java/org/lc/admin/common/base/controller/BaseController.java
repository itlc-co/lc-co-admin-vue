package org.lc.admin.common.base.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.common.utils.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Description: controller 控制器基类
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  17:57
 */
public abstract class BaseController {

    /**
     * 返回成功数据
     */
    public static ResultResponse success(Object data) {
        return ResultResponse.successData(data);
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 响应请求分页数据
     */
    protected TableData getDataTable(List<?> list) {
        return TableData.success(list, PageInfo.of(list).getTotal());
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected ResultResponse toResponse(Integer rows) {
        return rows > 0 ? success() : fail();
    }

    /**
     * 响应返回结果
     *
     * @param data data数据
     * @return 响应结果
     */
    protected ResultResponse toResponse(Map<String, Object> data) {
        return MapUtil.isNotEmpty(data) ? success().put(data) : fail();
    }

    /**
     * 响应返回结果
     *
     * @param data data数据
     * @return 响应结果
     */
    protected ResultResponse toResponse(Object data) {
        return ObjectUtil.isNotNull(data) ? success(data) : fail();
    }


    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected ResultResponse toResponse(boolean result) {
        return result ? success() : fail();
    }

    /**
     * 返回成功
     */
    public ResultResponse success() {
        return ResultResponse.success();
    }

    /**
     * 返回失败消息
     */
    public ResultResponse error() {
        return ResultResponse.error();
    }

    /**
     * 返回成功消息
     */
    public ResultResponse success(String message) {
        return ResultResponse.success(message);
    }

    /**
     * 返回失败消息
     */
    public ResultResponse error(String message) {
        return ResultResponse.error(message);
    }

    /**
     * 返回错误码消息
     */
    public ResultResponse error(Integer code, String message) {
        return ResultResponse.error(code, message);
    }


    /**
     * 返回失败
     */
    public ResultResponse fail() {
        return ResultResponse.fail();
    }

    /**
     * 返回失败消息
     */
    public ResultResponse fail(String msg) {
        return ResultResponse.fail(msg);
    }

    /**
     * 返回失败消息状态码
     */
    public ResultResponse fail(String message, Integer code) {
        return ResultResponse.fail(message, code);
    }

    /**
     * 返回失败消息状态码
     */
    public ResultResponse fail(StatusMsg statusMsg) {
        return ResultResponse.fail(statusMsg.getMsg(), statusMsg.getCode());
    }

    /**
     * Description: 请求重定向
     *
     * @param url 重定向url
     * @return {@link String }
     * @date 2022-09-22 16:34
     */
    public String redirect(String url) {
        return StrUtil.format("redirect:{}", url);
    }

    /**
     * Description: 获取当前登录用户id
     *
     * @return {@link Long } 用户id
     * @date 2022-09-14 09:34
     */
    protected Long getUserId() {
        return SecurityUtils.getUserId();
    }

    /**
     * Description: 获取当前登录用户详细
     *
     * @return {@link UserDetail } 用户详情
     * @date 2022-09-20 16:24
     */
    protected UserDetail getUserDetail() {
        return SecurityUtils.getUserDetail();
    }
}
