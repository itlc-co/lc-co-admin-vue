package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.common.entities.entity.SystemDept;
import org.lc.admin.common.entities.entity.SystemRole;
import org.lc.admin.system.entities.request.SystemDeptRequest;

import java.util.List;

/**
 * Description: 系统部门mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 16:35
 */
public interface SystemDeptMapper {

    /**
     * Description: 查询部门列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDept }> 部门列表数据
     * @date 2022-09-11 16:35
     */
    List<SystemDept> selectDeptList(SystemDeptRequest requestParam);

    /**
     * Description: 根据部门id查询部门数据
     *
     * @param deptId 部门id
     * @return {@link SystemDept } 部门数据
     * @date 2022-09-19 15:37
     */
    SystemDept selectDeptById(@Param("deptId") Long deptId);

    /**
     * Description: 查询排除指定部门id的部门及其父子部门的部门列表数据
     *
     * @param deptId 部门id
     * @return {@link List }<{@link SystemDept }> 排除后的部门列表数据
     * @date 2022-09-19 15:48
     */
    List<SystemDept> selectDeptListExcludeById(@Param("deptId") Long deptId);

    /**
     * Description: 插入部门数据
     *
     * @param dept 部门数据
     * @return {@link Integer } 记录数
     * @date 2022-09-19 16:12
     */
    Integer insertDept(SystemDept dept);

    /**
     * Description: 查询唯一性的系统部门id
     *
     * @param deptName 部门名称
     * @param parentId 父id
     * @return {@link Long } 系统部门id
     * @date 2022-09-19 20:58
     */
    Long selectUniqueDeptId(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * Description: 通过部门id查询系统部门状态
     *
     * @param deptId 部门id
     * @return {@link Integer } 部门状态
     * @date 2022-09-19 20:58
     */
    Integer selectDeptStatusById(@Param("deptId") Long deptId);

    /**
     * Description: 通过部门id查询系统部门祖级链路
     *
     * @param deptId 部门id
     * @return {@link String } 祖级链路
     * @date 2022-09-19 20:58
     */
    String selectDeptAncestorsById(@Param("deptId") Long deptId);

    /**
     * Description: 根据部门id查询部门下正常状态下的子部门的数量
     *
     * @param deptId 部门id
     * @return {@link Integer } 记录数
     * @date 2022-09-19 19:35
     */
    Integer selectCntNormalChildrenDeptById(@Param("deptId") Long deptId);

    /**
     * Description: 更新子部门祖级列表
     *
     * @param parentId     父部门id
     * @param oldAncestors 修改前祖级列表
     * @param newAncestors 修改后祖级列表
     * @return {@link Integer } 记录数
     * @date 2022-09-19 20:27
     */
    Integer updateChildrenDeptAncestors(@Param("parentId") Long parentId, @Param("oldAncestors") String oldAncestors, @Param("newAncestors") String newAncestors);

    /**
     * Description: 修改部门数据
     *
     * @param dept 部门数据
     * @return {@link Integer } 记录数
     * @date 2022-09-19 20:42
     */
    Integer updateDept(SystemDept dept);

    /**
     * Description: 根据部门id列表更新部门正常状态
     *
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-19 20:53
     */
    Integer updateDeptStatusNormal(@Param("deptIds") List<Long> deptIds);

    /**
     * Description: 根据部门id删除部门数据
     *
     * @param deptId 部门id
     * @return {@link Integer } 记录数
     * @date 2022-09-19 21:04
     */
    Integer deleteDeptById(@Param("deptId") Long deptId);

    /**
     * Description: 根据部门id查询子部门数量
     *
     * @param deptId 部门id
     * @return {@link Long } 记录数
     * @date 2022-09-19 21:19
     */
    Long selectCntChildDeptById(@Param("deptId") Long deptId);

    /**
     * Description: 根据角色数据查询部门id列表数据
     *
     * @param role 角色数据
     * @return {@link List }<{@link Long }> 部门id列表数据
     * @date 2022-09-20 09:23
     */
    List<Long> selectDeptIdsByRole(SystemRole role);

    /**
     * Description: 根据部门id列表删除部门数据
     *
     * @param deptIds 部门id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:32
     */
    Integer deleteDeptByIds(@Param("deptIds") List<Long> deptIds);
}
