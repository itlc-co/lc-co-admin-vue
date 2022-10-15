package org.lc.admin.web.controller.api.system;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.base.exec.BaseException;
import org.lc.admin.common.base.pool.BooleanString;
import org.lc.admin.common.entities.enums.AccessRecordStatus;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.exec.AuthException;
import org.lc.admin.common.exec.ServiceException;
import org.lc.admin.common.pool.AuthConstantsPool;
import org.lc.admin.common.pool.ConfigConstantsPool;
import org.lc.admin.common.pool.TokenConstantsPool;
import org.lc.admin.framework.async.service.AsyncTaskService;
import org.lc.admin.framework.security.service.AuthService;
import org.lc.admin.system.entities.request.AccessRecordRequest;
import org.lc.admin.system.entities.request.AuthRequest;
import org.lc.admin.system.entities.request.RegisterRequest;
import org.lc.admin.system.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Description: 系统认证用户controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-10 17:09
 */
@RestController
@RequestMapping("/auth")
public class SystemAuthUserController extends BaseController {

    public static final String MODULE = "system_auth";

    public static final Logger log = LoggerFactory.getLogger(SystemAuthUserController.class);

    @Resource
    private AuthService authService;

    @Resource
    private SystemConfigService configService;

    @Resource
    private AsyncTaskService asyncTaskService;

    /**
     * Description:
     * 生成图片验证码接口
     *
     * @return {@link ResultResponse } 响应结果集{uuid,src}
     * @date 2022-09-07 21:57
     */
    @GetMapping("/captcha")
    public ResultResponse captcha() {
        // 调用service接口实现生成验图片证码
        return toResponse(this.authService.createCaptcha());
    }

    /**
     * Description: 登录接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } {token}
     * @date 2022-09-07 21:57
     */
    @PostMapping("/login")
    public ResultResponse login(@Validated @RequestBody AuthRequest requestParam, HttpServletRequest request) {
        // 默认响应error
        ResultResponse response = ResultResponse.error();
        // 默认登录成功的记录访问参数
        AccessRecordRequest recordRequest = new AccessRecordRequest(requestParam.getUsername(), AccessRecordStatus.ACCESS_SUCCESS);
        try {
            // 处理验证码校验结果
            String msg = StrUtil.toString(ObjectUtil.defaultIfNull(request.getAttribute("msg"), AuthConstantsPool.DEFAULT_VALIDATE_CAPTCHA_MSG));
            Integer code = Integer.parseInt(StrUtil.toString(ObjectUtil.defaultIfNull(request.getAttribute("code"), AuthConstantsPool.DEFAULT_VALIDATE_CAPTCHA_CODE)));

            if (!StrUtil.equalsIgnoreCase(msg, AuthConstantsPool.DEFAULT_VALIDATE_CAPTCHA_MSG) && !AuthConstantsPool.DEFAULT_VALIDATE_CAPTCHA_CODE.equals(code)) {
                // 非默认成功状态消息则抛出认证异常
                throw new AuthException(code, msg);
            }

            // 调用service接口实现用户登录认证并返回令牌token
            String token = authService.login(requestParam);
            if (StrUtil.isNotBlank(token)) {
                // token不为空白字符串则响应success
                response = ResultResponse.success().put(TokenConstantsPool.TOKEN, token);
            }
        } catch (Exception e) {
            if (e instanceof BaseException) {
                // 强制转换为AuthException认证异常
                BaseException exception = (BaseException) e;
                // 认证异常访问记录参数
                recordRequest = new AccessRecordRequest(requestParam.getUsername(), exception);
                // 手动抛出认证异常便于全局异常捕获
                throw new AuthException(exception);
            } else {
                // 非认证异常访问记录参数
                recordRequest = new AccessRecordRequest(requestParam.getUsername(), AccessRecordStatus.ACCESS_FAIL);
                // 非认证异常抛出服务异常
                throw new ServiceException(StatusMsg.SERVICE_ERROR);
            }
        } finally {
            // 异步任务方式添加用户访问记录数据
            asyncTaskService.addAccessRecord(recordRequest);
        }
        return response;
    }

    /**
     * Description: 查询用户详情数据接口
     *
     * @return {@link ResultResponse } 响应结果集 {user,roles,permissions}
     * @date 2022-09-07 21:57
     */
    @GetMapping("/getInfo")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse getInfo() {
        ResultResponse response = this.authService.getInfo();
        if (ObjectUtil.isNull(response)) {
            // 用户未授权无法获取用户信息
            response = ResultResponse.error(StatusMsg.NOT_AUTH_ERROR);
        }
        return response;
    }

    /**
     * Description: 查询用户路由信息
     *
     * @return {@link ResultResponse } 路由信息响应结果集 {routers}
     * @date 2022-09-08 12:11
     */
    @GetMapping("/getRouters")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse getRouters() {
        ResultResponse response = this.authService.getRouters();
        if (ObjectUtil.isNull(response)) {
            // 用户未授权无法获取系统路由信息
            response = ResultResponse.error(StatusMsg.NOT_AUTH_ERROR);
        }
        return response;
    }

    /**
     * Description: 注册接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse }
     * @date 2022-09-23 13:48
     */
    @PostMapping("/register")
    public ResultResponse register(@RequestBody RegisterRequest requestParam) {
        if (!StrUtil.equalsIgnoreCase(configService.selectConfigValueByKey(ConfigConstantsPool.REGISTER_ENABLE), BooleanString.TRUE)) {
            return error("暂不支持用户注册功能");
        }
        return toResponse(this.authService.register(requestParam));
    }

}
