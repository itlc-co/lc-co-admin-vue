package org.lc.admin.framework.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.framework.service.UserOnlineService;
import org.lc.admin.system.entities.entity.UserOnline;
import org.lc.admin.system.entities.request.UserOnlineRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 在线用户service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 09:25
 */
@Service
public class UserOnlineServiceImpl implements UserOnlineService {

    public static final Logger log = LoggerFactory.getLogger(UserOnlineServiceImpl.class);

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 根据用户会话请求参数查询用户会话列表数据
     *
     * @param requestParam 用户会话请求参数
     * @return {@link List }<{@link UserOnline }> 用户会话列表数据
     * @date 2022-10-02 15:20
     */
    @Override
    public List<UserOnline> list(UserOnlineRequest requestParam) {
        return this.redisCacheService.getUserDetailList().stream()
                // 转换为UserOnline会话管理实体实例m
                .map((userDetail) ->
                        UserOnline.builder()
                                .browser(userDetail.getBrowser())
                                .loginTime(LocalDateTimeUtil.of(userDetail.getLoginTime()))
                                .deptName(userDetail.getDeptName())
                                .userName(userDetail.getUsername())
                                .ipaddr(userDetail.getIpaddr())
                                .uuid(userDetail.getUuid())
                                .os(userDetail.getOs())
                                .loginLocation(userDetail.getLoginLocation())
                                .build()
                )
                // 根据请求参数保留符合的实例
                .filter((userOnline) -> {
                    // 保留标记位默认true
                    boolean flag = true;

                    // 用户名称，ip地址，登录地址请求参数
                    String userNameParam = requestParam.getUserName();
                    String ipaddrParam = requestParam.getIpaddr();
                    String loginLocationParam = requestParam.getLoginLocation();

                    // userOnline实例中的用户名称，ip地址，登录地址
                    String userName = userOnline.getUserName();
                    String ipaddr = userOnline.getIpaddr();
                    String loginLocation = userOnline.getLoginLocation();

                    // 根据请求参数保留符合的userOnline实例
                    if (StrUtil.isAllNotBlank(userNameParam)) {
                        // 用户名称不为空白的情况
                        if (StrUtil.isAllNotBlank(ipaddrParam, loginLocationParam)) {
                            // IP地址，登录地址都不为空白的情况
                            flag = StrUtil.contains(userName, userNameParam) && StrUtil.contains(ipaddr, ipaddrParam) && StrUtil.contains(loginLocation, loginLocationParam);
                        } else if (StrUtil.isAllNotBlank(ipaddrParam)) {
                            // IP地址不为空白的情况
                            flag = StrUtil.contains(userName, userNameParam) && StrUtil.contains(ipaddr, ipaddrParam);
                        } else if (StrUtil.isAllNotBlank(loginLocationParam)) {
                            // 登录地址不为空白的情况
                            flag = StrUtil.contains(userName, userNameParam) && StrUtil.contains(loginLocation, loginLocationParam);
                        } else {
                            // 用户名称不为空白,IP地址，登录地址都空白的请情况
                            flag = StrUtil.contains(userName, userNameParam);
                        }
                    } else if (StrUtil.isAllNotBlank(ipaddrParam)) {
                        // 用户名称为空白,ip地址不为空白的情况
                        if (StrUtil.isAllNotBlank(loginLocationParam)) {
                            // 登录地址不为空白的情况
                            flag = StrUtil.contains(ipaddr, ipaddrParam) && StrUtil.contains(loginLocation, loginLocationParam);
                        } else {
                            // 登录地址为空白的情况
                            flag = StrUtil.contains(ipaddr, ipaddrParam);
                        }
                    } else if (StrUtil.isAllNotBlank(loginLocationParam)) {
                        // 用户名称为空白,地址为空白,登录地址不为空白的情况
                        flag = StrUtil.contains(loginLocation, loginLocationParam);
                    }
                    return flag;
                }).collect(Collectors.toList());
    }

    /**
     * Description: 根据uuid删除用户详情即会话
     *
     * @param uuid uuid 唯一标识
     * @return boolean true 存在删除成功 false 不存在删除失败
     * @date 2022-10-02 15:20
     */
    @Override
    public boolean deleteUserDetail(String uuid) {
        return this.redisCacheService.deleteUserDetail(uuid);
    }


}
