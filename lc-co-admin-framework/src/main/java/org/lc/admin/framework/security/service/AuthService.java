package org.lc.admin.framework.security.service;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.exec.UserException;
import org.lc.admin.common.pool.UserConstantsPool;
import org.lc.admin.common.utils.*;
import org.lc.admin.common.utils.server.AddressUtils;
import org.lc.admin.common.utils.server.IpUtils;
import org.lc.admin.common.utils.servlet.ServletUtils;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.framework.security.context.AuthenticationContext;
import org.lc.admin.framework.utils.CaptchaUtils;
import org.lc.admin.system.entities.request.AuthRequest;
import org.lc.admin.system.entities.request.RegisterRequest;
import org.lc.admin.system.service.AuthUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * Description: 身份验证service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-04 18:22
 */
@Service
public class AuthService {


    @Resource
    private AuthUserService authUserService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RouterService routerService;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisCacheService redisCacheService;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * Description: 根据用户名查询认证用户数据
     *
     * @param userName 用户名
     * @return {@link AuthUser } 认证用户数据
     * @date 2022-09-23 22:00
     */
    public AuthUser loadAuthUserByUsername(String userName) {
        return authUserService.selectAuthUserByUsername(userName);
    }

    /**
     * Description: 创建验证码
     *
     * @return java.util.Map<java.lang.String, java.lang.Object> data字段结果集map
     * @date 2022-09-01 22:25
     */
    public Map<String, Object> createCaptcha() {
        //  结果集map data字段
        Map<String, Object> map = new HashMap<>(2);

        // 生成验证码图片对象
        AbstractCaptcha captcha = CaptchaUtils.buildCaptcha();

        // 进行图片的base64编码
        String src = captcha.getImageBase64();
        map.put("src", src);

        // 获取(验证码的code)
        String value = captcha.getCode();

        // uuid(验证码的唯一标识)
        String uuid = IdUtil.fastSimpleUUID();
        map.put("uuid", uuid);

        // 保存验证码至redis缓存中
        this.saveCaptchaCode(uuid, value);

        return map;
    }


    /**
     * Description: 保存验证码至redis用于认证
     *
     * @param uuid  验证码唯一标识
     * @param value 验证码value
     * @date 2022-08-05 23:30
     */
    private void saveCaptchaCode(String uuid, String value) {
        this.redisCacheService.saveCaptchaCode(uuid, value);
    }

    /**
     * Description: 用户认证登录
     *
     * @param requestParam 请求体
     * @return java.lang.String token 字符串
     * @date 2022-09-02 17:41
     */
    public String login(AuthRequest requestParam) {

        // 用户认证参数（用户名与密码）
        String username = requestParam.getUsername();
        String password = requestParam.getPassword();

        // 用户验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        AuthenticationContext.setAuthentication(authenticationToken);
        // 该方法将调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 封装用户详情实例信息
        setUserInfo(user);

        // 记录用户登录信息 system_user表中数据
        recordAuthUserLoginInfo(user.getUserId());

        // 生成token
        return this.tokenService.createToken(user);
    }

    /**
     * Description: 根据用户id记录身份验证用户登录信息
     *
     * @param userId 用户id
     * @date 2022-09-05 22:34
     */
    private void recordAuthUserLoginInfo(Long userId) {
        SystemUser systemUser = SystemUser.builder()
                .id(userId)
                .loginIp(IpUtils.getIpAddr(ServletUtils.getRequest()))
                .loginTime(LocalDateTime.now())
                .build();
        this.authUserService.recordAuthUserLoginInfo(systemUser);
    }

    /**
     * Description: 设置用户信息
     *
     * @param user 用户
     * @date 2022-09-05 14:14
     */
    private void setUserInfo(UserDetail user) {
        // 每次用户认证通过后生成一个唯一性uuid  等价token
        user.setUuid(IdUtil.fastSimpleUUID());
        this.setUserAgent(user);
    }

    /**
     * Description: 设置用户代理 （浏览器以及系统信息）
     *
     * @param user 用户
     * @date 2022-09-05 14:47
     */
    private void setUserAgent(UserDetail user) {
        // 获取当前请求
        HttpServletRequest request = ServletUtils.getRequest();
        AssertUtils.isNotNull(request);
        // 解析userAgent
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        // 获取用户ip
        String ip = IpUtils.getIpAddr(request);
        // 封装用户信息
        user.setIpaddr(ip);
        user.setLoginLocation(AddressUtils.getLocationByIp(ip));
        user.setBrowser(StrUtil.format("{} {}", userAgent.getBrowser().getName(), userAgent.getVersion()));
        user.setOs(StrUtil.format(userAgent.getOs().getName()));
    }


    /**
     * Description: 获取认证用户信息
     *
     * @return {@link ResultResponse } 响应结果集
     * @date 2022-09-07 21:57
     */
    public ResultResponse getInfo() {
        ResultResponse response = null;
        // 获取用户详情实例
        UserDetail userDetail = SecurityUtils.getUserDetail();
        AuthUser user = ObjectUtil.isNull(userDetail) ? null : userDetail.getUser();
        if (ObjectUtil.isNotNull(userDetail) && ObjectUtil.isNotNull(user)) {
            response = ResultResponse.success();
            // 封装响应结果集
            // 忽略密码，随机盐等字段
            response.put("user", BeanUtil.copyProperties(user, AuthUser.class, UserConstantsPool.IGNORE_PROPERTIES));
            // 角色权限集合
            response.put("roles", this.permissionService.getRolePermissionByUser(user));
            // 菜单权限集合
            response.put("permissions", this.permissionService.getMenuPermissionByUser(user));
        }
        return response;
    }

    /**
     * Description: 获取路由数据
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-24 11:11
     */
    public ResultResponse getRouters() {
        ResultResponse response = null;
        // 获取认证用户id
        Long userId = SecurityUtils.getUserId();
        if (ObjectUtil.isNotNull(userId)) {
            response = ResultResponse.success();
            // 封装响应结果集
            response.put("routers", this.routerService.buildRouters(userId));
        }
        return response;
    }

    /**
     * Description: 注册
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-24 11:12
     */
    public Integer register(RegisterRequest requestParam) {
        // 检查两次密码是否正确
        if (!this.checkTwoPwdEquals(requestParam)) {
            throw new UserException(StatusMsg.USER_TWO_PASSWORDS_NOT_EQUALS);
        }
        // 检查用户名称唯一性
        if (!this.checkUserNameUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_NAME_EXIST);
        }
        return authUserService.register(requestParam);
    }

    /**
     * Description: 检查用户名称唯一性
     *
     * @param requestParam 用户注册请求
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-22 11:24
     */
    private boolean checkUserNameUnique(RegisterRequest requestParam) {
        return this.authUserService.selectCntUserByUserName(requestParam.getUsername()) <= 0;
    }

    /**
     * Description: 检查两次密码是否一致
     *
     * @param requestParam 用户注册请求参数
     * @return boolean true 一致 false 不一致
     * @date 2022-09-22 11:18
     */
    private boolean checkTwoPwdEquals(RegisterRequest requestParam) {
        String password = requestParam.getPassword();
        String againPassword = requestParam.getAgainPassword();
        return StrUtil.equalsIgnoreCase(password, againPassword);
    }


}
