package org.lc.admin.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.lc.admin.system.entities.entity.SystemRoleDept;
import org.lc.admin.system.mapper.SystemRoleDeptMapper;
import org.lc.admin.system.service.SystemRoleDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 系统角色部门关联service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-20 13:19
 */
@Service
public class SystemRoleDeptServiceImpl implements SystemRoleDeptService {

    public static final Logger log = LoggerFactory.getLogger(SystemRoleDeptServiceImpl.class);


    @Resource
    private SystemRoleDeptMapper roleDeptMapper;


    /**
     * Description: 插入角色部门关联列表数据
     *
     * @param roleDepts 角色部门关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:09
     */
    @Override
    public Integer insertRoleDept(List<SystemRoleDept> roleDepts) {
        return this.roleDeptMapper.insertRoleDept(roleDepts);
    }

    /**
     * Description: 插入角色部门关联数据
     *
     * @param roleId  角色id
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:05
     */
    @Override
    public Integer insertRoleDept(Long roleId, List<Long> deptIds) {
        Integer result = 1;
        List<SystemRoleDept> roleDepts = deptIds.stream().map((deptId) -> SystemRoleDept.builder().deptId(deptId).roleId(roleId).build()).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(roleDepts)) {
            result = this.insertRoleDept(roleDepts);
        }
        return result;
    }

    /**
     * Description: 根据角色id删除角色部门角色关联数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:02
     */
    @Override
    public Integer deleteRoleDeptByRoleId(Long roleId) {
        return this.roleDeptMapper.deleteRoleDeptByRoleId(roleId);
    }

    /**
     * Description: 根据角色id与部门id列表更新角色部门关联数据
     *
     * @param roleId  角色id
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 18:55
     */
    @Override
    public Integer updateRoleDeptByRoleId(Long roleId, List<Long> deptIds) {
        // 删除角色与部门关联信息
        this.deleteRoleDeptByRoleId(roleId);
        // 新增角色和部门信息（数据权限）
        return this.insertRoleDept(roleId, deptIds);
    }

    /**
     * Description: 根据角色id列表删除角色部门关联数据
     *
     * @param roleIds 角色id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:19
     */
    @Override
    public Integer deleteRoleDepts(List<Long> roleIds) {
        return this.roleDeptMapper.deleteRoleDepts(roleIds);
    }
}
