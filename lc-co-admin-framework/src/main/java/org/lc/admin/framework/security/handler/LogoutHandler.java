package org.lc.admin.framework.security.handler;

import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.base.pool.Message;
import org.lc.admin.common.entities.enums.AccessRecordStatus;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.utils.servlet.ServletUtils;
import org.lc.admin.framework.async.service.AsyncTaskService;
import org.lc.admin.framework.security.service.TokenService;
import org.lc.admin.system.entities.request.AccessRecordRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 退出认证处理类
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-29  18:13
 */
@Component
public class LogoutHandler implements LogoutSuccessHandler {

    @Resource
    private TokenService tokenService;

    @Resource
    private AsyncTaskService asyncTaskService;

    /**
     * Description: 成功退出处理
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 认证实例
     * @date 2022-08-29 18:18
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取用户详情
        UserDetail userDetail = this.tokenService.getUserDetail(request);
        if (ObjectUtil.isNotNull(userDetail)) {
            // 删除用户缓存记录
            this.tokenService.createToken(userDetail);
            // 记录用户退出日志
            this.asyncTaskService.addAccessRecord(new AccessRecordRequest(userDetail.getUsername(), AccessRecordStatus.ACCESS_LOGOUT));
        }
        ServletUtils.renderString(response, ResultResponse.success(Message.LOGOUT_SUCCESS));
    }

}
