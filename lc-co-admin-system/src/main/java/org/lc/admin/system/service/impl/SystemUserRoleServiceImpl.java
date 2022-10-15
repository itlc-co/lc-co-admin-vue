package org.lc.admin.system.service.impl;

import org.lc.admin.common.annotation.DataSource;
import org.lc.admin.common.entities.enums.DataSourceName;
import org.lc.admin.system.entities.entity.SystemUserRole;
import org.lc.admin.system.mapper.SystemUserRoleMapper;
import org.lc.admin.system.service.SystemUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 系统用户角色关联service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 19:26
 */
@Service
public class SystemUserRoleServiceImpl implements SystemUserRoleService {

    @Resource
    private SystemUserRoleMapper userRoleMapper;


    /**
     * Description: 根据角色id查询用户数量
     *
     * @param roleId 角色id
     * @return {@link Integer } 用户数量
     * @date 2022-09-20 13:44
     */
    @Override
    public Integer selectCntUserRoleByRoleId(Long roleId) {
        return this.userRoleMapper.selectCntUserRoleByRoleId(roleId);
    }

    /**
     * Description: 根据角色id与用户id列表删除角色用户关联数据
     *
     * @param roleId  角色id
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:44
     */
    @Override
    public Integer deleteUserRoles(Long roleId, List<Long> userIds) {
        return this.userRoleMapper.deleteUserRoles(roleId, userIds);
    }

    /**
     * Description: 根据角色id与用户id删除角色用户关联数据
     *
     * @param userRole 角色用户关联数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:44
     */
    @Override
    public Integer deleteUserRole(SystemUserRole userRole) {
        return this.userRoleMapper.deleteUserRole(userRole);
    }

    /**
     * Description: 根据用户id列表删除用户角色关联数据
     *
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:54
     */
    @Override
    public Integer deleteUserRolesByUserIds(List<Long> userIds) {
        return this.userRoleMapper.deleteUserRolesByUserIds(userIds);
    }

    /**
     * Description: 根据用户id删除用户角色关联数据
     *
     * @param userId 用户id
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:17
     */
    @Override
    public Integer deleteUserRolesByUserId(Long userId) {
        return this.userRoleMapper.deleteUserRolesByUserId(userId);
    }

    /**
     * Description: 添加用户角色列表关联数据
     *
     * @param userRoles 用户角色列表关联数据
     * @return {@link Integer } 记录数
     * @date 2022-09-13 19:27
     */
    @Override
    public Integer batchUserRole(List<SystemUserRole> userRoles) {
        return this.userRoleMapper.batchUserRole(userRoles);
    }


}
