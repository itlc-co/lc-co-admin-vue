package org.lc.admin.system.service;

import org.lc.admin.common.entities.TreeSelect;
import org.lc.admin.common.entities.entity.SystemDept;
import org.lc.admin.system.entities.request.SystemDeptRequest;

import java.util.List;
import java.util.Map;

/**
 * Description: 系统部门service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 16:30
 */
public interface SystemDeptService {
    /**
     * Description: 查询部门列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDept }> 部门列表数据
     * @date 2022-09-11 16:33
     */
    List<SystemDept> selectDeptList(SystemDeptRequest requestParam);

    /**
     * Description: 构建部门选择树数据
     *
     * @param deptList 部门列表数据
     * @return {@link List }<{@link TreeSelect }> 部门选择树数据
     * @date 2022-09-11 16:48
     */
    List<TreeSelect> buildDeptTreeSelect(List<SystemDept> deptList);

    /**
     * Description: 根据部门id查询部门数据
     *
     * @param deptId 部门id
     * @return {@link SystemDept } 部门数据
     * @date 2022-09-19 15:36
     */
    SystemDept selectDeptById(Long deptId);

    /**
     * Description: 查询排除指定部门id的部门及其父子部门的部门列表数据
     *
     * @param deptId 部门id
     * @return {@link List }<{@link SystemDept }> 排除后的部门列表数据
     * @date 2022-09-19 15:46
     */
    List<SystemDept> selectDeptListExcludeById(Long deptId);

    /**
     * Description: 添加部门数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-19 16:06
     */
    Integer addDept(SystemDeptRequest requestParam);

    /**
     * Description: 编辑部门数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-19 19:34
     */
    Integer editDept(SystemDeptRequest requestParam);

    /**
     * Description: 根据部门id查询部门下正常状态下的子部门的数量
     *
     * @param deptId 部门id
     * @return {@link Integer } 记录数
     * @date 2022-09-19 19:34
     */
    Integer selectCntNormalChildrenDeptById(Long deptId);

    /**
     * Description: 更新部门数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-19 19:42
     */
    Integer updateDept(SystemDeptRequest requestParam);

    /**
     * Description: 更新子部门祖级列表数据
     *
     * @param parentId     父部门id
     * @param oldAncestors 修改前祖级列表
     * @param newAncestors 修改后祖级列表
     * @return {@link Integer } 记录数
     * @date 2022-09-19 20:25
     */
    Integer updateChildrenDeptAncestors(Long parentId, String oldAncestors, String newAncestors);

    /**
     * Description: 根据部门id列表更新部门正常状态
     *
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-19 20:47
     */
    Integer updateDeptStatusNormal(List<Long> deptIds);

    /**
     * Description: 根据部门id删除部门数据
     *
     * @param deptId 部门id
     * @return {@link Integer } 记录数
     * @date 2022-09-19 21:03
     */
    Integer deleteDeptById(Long deptId);

    /**
     * Description: 根据部门id列表删除部门数据
     *
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-19 21:03
     */
    Integer deleteDeptByIds(List<Long> deptIds);

    /**
     * Description: 根据部门id查询子部门数量
     *
     * @param deptId 部门id
     * @return {@link Long } 记录数
     * @date 2022-09-19 21:18
     */
    Long selectCntChildDeptById(Long deptId);

    /**
     * Description: 根据角色id查询角色部门选择树数据
     *
     * @param roleId 角色id
     * @return {@link Map }<{@link String }, {@link Object }> 角色部门选择树map数据
     * @date 2022-09-23 21:08
     */
    Map<String, Object> roleDeptTreeSelect(Long roleId);

    /**
     * Description: 构建部门选择树数据
     *
     * @return {@link List }<{@link TreeSelect }> 部门选择树数据
     * @date 2022-09-20 08:53
     */
    List<TreeSelect> buildDeptTreeSelect();

    /**
     * Description: 根据角色id查询部门id列表数据
     *
     * @param roleId 角色id
     * @return {@link List }<{@link Long }> 部门id列表数
     * @date 2022-09-20 08:56
     */
    List<Long> selectDeptIdsByRoleId(Long roleId);

    /**
     * Description: 检查是否具有指定部门id的数据范围权限
     *
     * @param deptId 部门id
     * @return boolean true 具有 false 不具有
     * @date 2022-10-04 10:50
     */
    boolean checkDeptDataScope(Long deptId);
}
