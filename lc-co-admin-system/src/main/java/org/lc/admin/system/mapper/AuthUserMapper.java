package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.common.entities.entity.AuthUser;

/**
 * Description: 认证用户mapper接口
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  21:25
 */
public interface AuthUserMapper {

    /**
     * Description: 根据用户名查询认证用户数据
     *
     * @param userName 用户名
     * @return {@link AuthUser } 认证用户数据
     * @date 2022-09-01 21:25
     */
    AuthUser selectAuthUserByUsername(@Param("userName") String userName);
}
