package org.lc.admin.framework.service;

import org.lc.admin.system.entities.entity.UserOnline;
import org.lc.admin.system.entities.request.UserOnlineRequest;

import java.util.List;

/**
 * Description: 在线用户service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 09:24
 */
public interface UserOnlineService {


    /**
     * Description: 根据用户会话请求参数查询用户会话列表数据
     *
     * @param requestParam 用户会话请求参数
     * @return {@link List }<{@link UserOnline }> 用户会话列表数据
     * @date 2022-10-02 15:18
     */
    List<UserOnline> list(UserOnlineRequest requestParam);

    /**
     * Description: 根据uuid删除用户详情即会话
     *
     * @param uuid uuid 唯一标识
     * @return boolean true 存在删除成功 false 不存在删除失败
     * @date 2022-10-02 15:19
     */
    boolean deleteUserDetail(String uuid);
}
