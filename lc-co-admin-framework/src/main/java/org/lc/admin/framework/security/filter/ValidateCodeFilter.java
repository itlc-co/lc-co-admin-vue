package org.lc.admin.framework.security.filter;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.pool.HttpRequestMethod;
import org.lc.admin.common.exec.AuthException;
import org.lc.admin.common.pool.AuthConstantsPool;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 验证码校验过滤器
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-02  18:12
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    /**
     * 需要校验验证码的请求uri数组
     */
    public static final String[] REQUEST_URI = {"/auth/login", "/auth/register"};

    /**
     * 请求参数uuid标识
     */
    public static final String REQUEST_PARAM_UUID = "uuid";

    /**
     * 请求参数验证码code标识
     */
    public static final String REQUEST_PARAM_CODE = "code";

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 验证码过滤器内部实现
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException servlet异常
     * @throws IOException      io异常
     * @date 2022-10-02 15:35
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 需要校验验证码的url拦截校验
        if (checkIsValidateCodeRequest(request)) {
            // 默认检验成功消息与状态码
            String msg = AuthConstantsPool.DEFAULT_VALIDATE_CAPTCHA_MSG;
            Integer code = AuthConstantsPool.DEFAULT_VALIDATE_CAPTCHA_CODE;

            try {
                // 校验验证码
                validateCode(request);
            } catch (AuthException authException) {

                // 异常则重置消息与状态码
                code = authException.getCode();
                msg = authException.getMessage();
            }
            // 设置请求属性状态码与消息
            request.setAttribute("code", code);
            request.setAttribute("msg", msg);
        }
        // 不需要校验验证码放行
        filterChain.doFilter(request, response);
    }

    /**
     * Description: 检查是否需要校验验证码
     *
     * @param request 请求
     * @return boolean
     * @date 2022-09-24 11:04
     */
    private boolean checkIsValidateCodeRequest(HttpServletRequest request) {
        // 请求uri与请求方法
        String uri = request.getRequestURI();
        String method = request.getMethod();
        // post请求并且为需要校验验证码的uri
        return StrUtil.equalsAnyIgnoreCase(uri, REQUEST_URI) && method.equalsIgnoreCase(HttpRequestMethod.POST);
    }

    /**
     * Description: 校验验证码
     *
     * @param request 请求
     * @throws AuthException 身份验证异常
     * @date 2022-09-24 11:04
     */
    private void validateCode(HttpServletRequest request) throws AuthException {

        // 获取请求参数 uuid以及code
        String uuid = request.getParameter(REQUEST_PARAM_UUID);
        String code = request.getParameter(REQUEST_PARAM_CODE);

        if (StrUtil.hasBlank(code, uuid)) {
            // 如果参数为null 验证码无效用于异常响应
            throw new AuthException(StatusMsg.AUTH_CODE_INVALID);
        }

        // 获取uuid对应的验证码并且删除（表示使用过）
        String realCode = this.getAndDeleteCaptchaCode(uuid);
        if (StrUtil.isBlank(realCode)) {
            // 如果不存在redis key 验证码失效
            throw new AuthException(StatusMsg.AUTH_CODE_FAILURE);
        }

        if (!StrUtil.equalsIgnoreCase(realCode, code)) {
            // 验证码不一致抛出认证异常
            throw new AuthException(StatusMsg.AUTH_CODE_VERIFICATION);
        }

    }

    /**
     * Description: 根据uuid查询并且删除验证码缓存
     *
     * @param uuid uuid 唯一标识
     * @return {@link String } 验证码code
     * @date 2022-10-02 15:36
     */
    private String getAndDeleteCaptchaCode(String uuid) {
        return this.redisCacheService.getAndDeleteCaptchaCode(uuid);
    }

}
