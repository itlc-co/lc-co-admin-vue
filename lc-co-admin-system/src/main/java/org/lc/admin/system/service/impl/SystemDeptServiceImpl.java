package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.annotation.DataScope;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.entities.TreeSelect;
import org.lc.admin.common.entities.entity.SystemDept;
import org.lc.admin.common.entities.entity.SystemRole;
import org.lc.admin.common.exec.DeptException;
import org.lc.admin.common.pool.DeptConstantsPool;
import org.lc.admin.common.utils.system.AuthUserUtils;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.request.SystemDeptRequest;
import org.lc.admin.system.mapper.SystemDeptMapper;
import org.lc.admin.system.service.SystemDeptService;
import org.lc.admin.system.service.SystemRoleService;
import org.lc.admin.system.service.SystemUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 系统部门service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 16:34
 */
@Service
public class SystemDeptServiceImpl implements SystemDeptService {

    @Resource
    private SystemDeptMapper deptMapper;

    @Resource
    private SystemUserService userService;

    @Resource
    private SystemRoleService roleService;

    /**
     * Description: 根据部门id查询部门数据
     *
     * @param deptId 部门id
     * @return {@link SystemDept } 部门数据
     * @date 2022-09-19 15:37
     */
    @Override
    public SystemDept selectDeptById(Long deptId) {
        return this.deptMapper.selectDeptById(deptId);
    }

    /**
     * Description: 更新子部门祖级列表
     *
     * @param parentId     父部门id
     * @param oldAncestors 修改前祖级列表
     * @param newAncestors 修改后祖级列表
     * @return {@link Integer } 记录数
     * @date 2022-09-19 20:27
     */
    @Override
    public Integer updateChildrenDeptAncestors(Long parentId, String oldAncestors, String newAncestors) {
        return this.deptMapper.updateChildrenDeptAncestors(parentId, oldAncestors, newAncestors);
    }

    /**
     * Description: 检查是否具有指定部门id的数据范围权限
     *
     * @param deptId 部门id
     * @return boolean true 具有 false 不具有
     * @date 2022-10-04 10:53
     */
    @Override
    public boolean checkDeptDataScope(Long deptId) {
        // 默认允许
        boolean flag = true;
        // 登录用户非admin才需要校验
        if (!AuthUserUtils.isAdmin(SecurityUtils.getUserId())) {

            // 构造查询参数
            SystemDeptRequest requestParam = new SystemDeptRequest();
            requestParam.setDeptId(deptId);

            // 根据部门id查询部门列表数据
            List<SystemDept> depts = this.selectDeptList(requestParam);

            // 部门列表数据转换为部门id集合
            Set<Long> deptIds = depts.stream().map(SystemDept::getId).distinct().filter(ObjectUtil::isNotNull).collect(Collectors.toSet());

            // 部门id集合是否为空
            // 空表示没有数据权限返回false 反之亦然
            flag = CollUtil.isNotEmpty(deptIds);

        }
        return flag;
    }

    /**
     * Description: 根据角色id查询部门id列表数据
     *
     * @param roleId 角色id
     * @return {@link List }<{@link Long }> 部门id列表数
     * @date 2022-09-23 21:27
     */
    @Override
    public List<Long> selectDeptIdsByRoleId(Long roleId) {
        SystemRole role = this.roleService.selectRoleById(roleId);
        return this.deptMapper.selectDeptIdsByRole(role);
    }

    /**
     * Description: 构建部门选择树数据
     *
     * @return {@link List }<{@link TreeSelect }> 部门选择树数据
     * @date 2022-09-20 08:54
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect() {
        return this.buildDeptTreeSelect(this.selectDeptList(new SystemDeptRequest()));
    }

    /**
     * Description: 根据角色id查询角色部门选择树数据
     *
     * @param roleId 角色id
     * @return {@link Map }<{@link String }, {@link Object }> 角色部门选择树map数据
     * @date 2022-09-20 08:51
     */
    @Override
    public Map<String, Object> roleDeptTreeSelect(Long roleId) {
        Map<String, Object> resultMap = MapUtil.newHashMap();
        // 已存在部门id列表
        resultMap.put("checkedKeys", this.selectDeptIdsByRoleId(roleId));
        // 所有部门树
        resultMap.put("depts", this.buildDeptTreeSelect());
        return resultMap;
    }

    /**
     * Description: 根据部门id查询子部门数量
     *
     * @param deptId 部门id
     * @return {@link Long } 记录数
     * @date 2022-09-19 21:18
     */
    @Override
    public Long selectCntChildDeptById(Long deptId) {
        return this.deptMapper.selectCntChildDeptById(deptId);
    }

    /**
     * Description: 根据部门id列表删除部门数据
     *
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:30
     */
    @Override
    public Integer deleteDeptByIds(List<Long> deptIds) {
        deptIds.forEach((deptId) -> {
            // 检查是否存在子部门
            if (this.checkExistChildDeptById(deptId)) {
                throw new DeptException(StatusMsg.DEPT_EXIST_CHILDREN_DEPT);
            }
            // 检查是否存在用户
            if (this.checkExistUserById(deptId)) {
                throw new DeptException(StatusMsg.DEPT_EXIST_USER);
            }
        });
        return this.deptMapper.deleteDeptByIds(deptIds);
    }

    /**
     * Description: 根据部门id删除部门数据
     *
     * @param deptId 部门id
     * @return {@link Integer } 记录数
     * @date 2022-09-19 21:03
     */
    @Override
    public Integer deleteDeptById(Long deptId) {
        // 检查是否存在子部门
        if (this.checkExistChildDeptById(deptId)) {
            throw new DeptException(StatusMsg.DEPT_EXIST_CHILDREN_DEPT);
        }
        // 检查是否存在用户
        if (this.checkExistUserById(deptId)) {
            throw new DeptException(StatusMsg.DEPT_EXIST_USER);
        }
        return this.deptMapper.deleteDeptById(deptId);
    }

    /**
     * Description: 检查该部门是否存在用户
     *
     * @param deptId 部门id
     * @return boolean true 存在用户 false 不存在用户
     * @date 2022-09-19 21:10
     */
    private boolean checkExistUserById(Long deptId) {
        return this.userService.selectCntUserByDeptId(deptId) > 0L;
    }

    /**
     * Description: 检查该部门是否存在子部门
     *
     * @param deptId 部门id
     * @return boolean true 存在子部门 false 不存在子部门
     * @date 2022-09-19 21:10
     */
    private boolean checkExistChildDeptById(Long deptId) {
        return this.selectCntChildDeptById(deptId) > 0L;
    }

    /**
     * Description: 根据部门id列表更新部门正常状态
     *
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-19 20:53
     */
    @Override
    public Integer updateDeptStatusNormal(List<Long> deptIds) {
        return this.deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * Description: 更新系统部门
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-19 19:42
     */
    @Override
    public Integer updateDept(SystemDeptRequest requestParam) {

        // bean实体转换
        SystemDept dept = BeanUtil.toBean(requestParam, SystemDept.class);
        // 修改后父部门，修改前该部门
        SystemDept newParentDept = this.selectDeptById(dept.getParentId());
        SystemDept oldDept = this.selectDeptById(dept.getId());

        // 修改子部门Ancestors祖级链路
        if (ObjectUtil.isNotNull(newParentDept) && ObjectUtil.isNotNull(oldDept)) {
            // 修改后的祖级链路
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getId();
            // 修改前的祖级链路
            String oldAncestors = oldDept.getAncestors();
            // 设置修改后的祖级链路
            dept.setAncestors(newAncestors);
            // 设置修改后子部门的祖级链路
            this.updateChildrenDeptAncestors(dept.getId(), oldAncestors, newAncestors);
        }

        // 修改本部门信息
        Integer row = this.deptMapper.updateDept(dept);

        // 如果该部门是启用状态，则启用该部门的所有上级部门（不包括顶级部门）
        if (ObjectUtil.equals(DeptConstantsPool.DEPT_NORMAL_STATUS, dept.getStatus()) && StrUtil.isNotBlank(dept.getAncestors()) && !StrUtil.equals("0", dept.getAncestors())) {
            this.updateParentDeptStatusNormal(dept.getAncestors());
        }
        return row;
    }

    /**
     * Description: 更新父部门状态正常
     *
     * @param ancestors 祖级列表
     * @return {@link Integer } 记录数
     * @date 2022-09-19 20:52
     */
    private Integer updateParentDeptStatusNormal(String ancestors) {
        return this.updateDeptStatusNormal(StrUtil.split(ancestors, ",").stream().map(Convert::toLong).collect(Collectors.toList()));
    }

    /**
     * Description: 根据部门id查询部门下正常状态下的子部门的数量
     *
     * @param deptId 部门id
     * @return {@link Integer } 记录数
     * @date 2022-09-19 19:36
     */
    @Override
    public Integer selectCntNormalChildrenDeptById(Long deptId) {
        return this.deptMapper.selectCntNormalChildrenDeptById(deptId);
    }

    /**
     * Description: 编辑部门数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:15
     */
    @Override
    public Integer editDept(SystemDeptRequest requestParam) {
        // 检查部门名称是否唯一
        if (this.checkDeptNameUnique(requestParam)) {
            throw new DeptException(StatusMsg.DEPT_EXIST);
        }
        // 检查该部门是否停用并且包含未停用的子部门
        if (this.checkChangeDept(requestParam)) {
            throw new DeptException(StatusMsg.DEPT_SUBDEPT_DISABLE);
        }
        return this.updateDept(requestParam);
    }

    /**
     * Description: 检查是否允许更改部门
     *
     * @param requestParam 请求参数
     * @return boolean true 不允许 false 允许
     * @date 2022-09-19 19:26
     */
    private boolean checkChangeDept(SystemDeptRequest requestParam) {
        // 该部门停用并且包含未停用的子部门
        return DeptConstantsPool.DEPT_DISABLE_STATUS.intValue() == requestParam.getStatus().intValue() && this.selectCntNormalChildrenDeptById(requestParam.getDeptId()) > 0;
    }

    /**
     * Description: 添加部门数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-19 16:07
     */
    @Override
    public Integer addDept(SystemDeptRequest requestParam) {
        Long parentId = requestParam.getParentId();
        // 检查部门名称是否唯一
        if (this.checkDeptNameUnique(requestParam)) {
            throw new DeptException(StatusMsg.DEPT_EXIST);
        }

        // 父部门实体
        SystemDept parentDept = this.deptMapper.selectDeptById(parentId);

        // 检查父部门存在并且是否启用
        if (ObjectUtil.isNull(parentDept)) {
            StatusMsg deptNotExist = StatusMsg.DEPT_NOT_EXIST;
            throw new DeptException(deptNotExist.getCode(), "父" + deptNotExist.getMsg());
        }
        if (ObjectUtil.equals(DeptConstantsPool.DEPT_DISABLE_STATUS, parentDept.getStatus())) {
            StatusMsg deptDisable = StatusMsg.DEPT_DISABLE;
            throw new DeptException(deptDisable.getCode(), "父" + deptDisable.getMsg());
        }

        // bean实体转换
        SystemDept dept = BeanUtil.toBean(requestParam, SystemDept.class);

        // 配置系统部门实体创建修改者等信息
        String userName = SecurityUtils.getUserName();
        dept.setCreateBy(userName);
        dept.setUpdateBy(userName);
        dept.setAncestors(parentDept.getAncestors() + "," + parentId);

        return this.deptMapper.insertDept(dept);
    }

    /**
     * Description: 检查部门名称唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-19 20:56
     */
    private boolean checkDeptNameUnique(SystemDeptRequest requestParam) {
        return this.checkDeptNameUnique(ObjectUtil.defaultIfNull(requestParam.getDeptId(), requestParam.getId()), requestParam.getDeptName(), requestParam.getParentId());
    }

    /**
     * Description: 检查父部门状态
     *
     * @param deptId 部门id
     * @return boolean true 正常 false 停用
     * @date 2022-09-19 20:56
     */
    private boolean checkParentDeptStatus(Long deptId) {
        return ObjectUtil.equals(DeptConstantsPool.DEPT_NORMAL_STATUS, this.deptMapper.selectDeptStatusById(deptId));
    }

    /**
     * Description: 检查部门唯一性
     *
     * @param deptId   部门id
     * @param deptName 部门名称
     * @param parentId 父部门id
     * @return boolean
     * @date 2022-09-19 20:56
     */
    private boolean checkDeptNameUnique(Long deptId, String deptName, Long parentId) {
        Long uniqueId = this.deptMapper.selectUniqueDeptId(deptName, parentId);
        return (ObjectUtil.isNotNull(uniqueId) && uniqueId.longValue() != deptId.longValue());
    }

    /**
     * Description: 查询排除指定部门id的部门及其父子部门的部门列表数据
     *
     * @param deptId 部门id
     * @return {@link List }<{@link SystemDept }> 排除后的部门列表数据
     * @date 2022-09-19 15:47
     */
    @Override
    public List<SystemDept> selectDeptListExcludeById(Long deptId) {
        return this.deptMapper.selectDeptListExcludeById(deptId);
    }

    /**
     * Description: 构建部门选择树数据
     *
     * @param deptList 部门列表数据
     * @return {@link List }<{@link TreeSelect }> 部门选择树数据
     * @date 2022-09-11 16:50
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SystemDept> deptList) {
        return this.toTreeSelect(this.buildDeptTree(deptList));
    }

    /**
     * Description: 转换部门树为部门选择树数据
     *
     * @param deptTrees 部门树数据
     * @return {@link List }<{@link TreeSelect }> 部门选择树数据
     * @date 2022-09-11 16:54
     */
    private List<TreeSelect> toTreeSelect(List<SystemDept> deptTrees) {
        return deptTrees.stream().map((dept) -> TreeSelect.builder().children(toTreeSelect(dept.getChildren())).id(dept.getId()).label(dept.getDeptName()).build()).collect(Collectors.toList());
    }

    /**
     * Description: 构建部门树数据
     *
     * @param deptList 部门列表
     * @return {@link List }<{@link SystemDept }> 部门树数据
     * @date 2022-09-11 16:54
     */
    private List<SystemDept> buildDeptTree(List<SystemDept> deptList) {
        // 部门按照父部门id分组
        Map<Long, List<SystemDept>> deptGroup = deptList.stream().collect(Collectors.groupingBy(SystemDept::getParentId));
        return deptList.stream()
                // 设置一级部门的子部门列表
                .map((dept) -> dept.setChildren(deptGroup.getOrDefault(dept.getId(), ListUtil.list(false))))
                // 保留顶级部门
                .filter((dept) -> dept.getParentId() == 0)
                .collect(Collectors.toList());
    }

    /**
     * Description: 查询部门列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDept }> 部门列表数据
     * @date 2022-09-11 16:35
     */
    @Override
    @DataScope
    public List<SystemDept> selectDeptList(SystemDeptRequest requestParam) {
        return deptMapper.selectDeptList(requestParam);
    }

}
