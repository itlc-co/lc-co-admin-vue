package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.annotation.DataScope;
import org.lc.admin.common.annotation.DataSource;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.entities.entity.SystemRole;
import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.common.entities.enums.DataSourceName;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.exec.RoleException;
import org.lc.admin.common.utils.system.AuthUserUtils;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.bo.SystemRoleBo;
import org.lc.admin.system.entities.entity.SystemUserRole;
import org.lc.admin.system.entities.request.SystemRoleRequest;
import org.lc.admin.system.entities.response.SystemRoleResponse;
import org.lc.admin.system.mapper.SystemRoleMapper;
import org.lc.admin.system.service.SystemRoleDeptService;
import org.lc.admin.system.service.SystemRoleMenuService;
import org.lc.admin.system.service.SystemRoleService;
import org.lc.admin.system.service.SystemUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 系统角色service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-07 22:02
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Resource
    private SystemRoleMapper roleMapper;

    @Resource
    private SystemUserRoleService userRoleService;

    @Resource
    private SystemRoleMenuService roleMenuService;

    @Resource
    private SystemRoleDeptService roleDeptService;

    /**
     * Description: 根据用户id查询所有角色列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemRole }> 角色列表数据
     * @date 2022-09-13 09:25
     */
    @Override
    public List<SystemRole> selectRolesAll(Long userId) {
        // 所有角色列表
        List<SystemRole> roles = this.selectRolesAll();
        // 如果为admin用户则显示所有，否则显示除admin外的角色
        return SystemUser.isAdmin(userId) ? roles : roles.stream().filter((role) -> !role.isAdmin()).collect(Collectors.toList());
    }

    /**
     * Description: 查询系统角色Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemRoleBo }> 角色bo实体列表
     * @date 2022-09-20 21:10
     */
    @Override
    @DataScope
    public List<SystemRoleBo> selectRoleBoList(SystemRoleRequest requestParam) {
        return this.roleMapper.selectRoleBoList(requestParam);
    }

    /**
     * Description: 根据用户名称查询角色名称集合
     *
     * @param userName 用户名
     * @return {@link Set }<{@link String }> 角色名称集合
     * @date 2022-09-23 16:44
     */
    @Override
    public Set<String> selectRoleNamesByUserName(String userName) {
        return this.roleMapper.selectRoleNamesByUserName(userName);
    }

    /**
     * Description: 校验角色id是否允许访问
     *
     * @param roleId 角色id
     * @return boolean true 允许 false 不允许
     * @date 2022-10-04 10:35
     */
    @Override
    public boolean checkRoleAllowed(Long roleId) {
        // 不允许操作admin角色
        return !SystemRole.isAdmin(roleId);
    }

    /**
     * Description: 检查角色id是否有数据权限
     *
     * @param roleId 角色id
     * @return boolean true 具有 false 不具有
     * @date 2022-10-04 10:35
     */
    @Override
    public boolean checkRoleDataScope(Long roleId) {
        // 默认允许
        boolean flag = true;
        // 登录用户非admin才需要校验
        if (!AuthUserUtils.isAdmin(SecurityUtils.getUserId())) {

            // 构造查询参数
            SystemRoleRequest requestParam = new SystemRoleRequest();
            requestParam.setRoleId(roleId);

            // 根据角色id查询角色列表数据
            List<SystemRole> roles = this.selectRoleList(requestParam);

            // 角色列表数据转换为角色id集合
            Set<Long> roleIds = roles.stream().map(SystemRole::getId).distinct().filter(ObjectUtil::isNotNull).collect(Collectors.toSet());

            // 角色id集合是否为空
            // 空表示没有数据权限返回false 反之亦然
            flag = CollUtil.isNotEmpty(roleIds);

        }
        return flag;
    }

    /**
     * Description: 插入角色数据
     *
     * @param role 角色
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:44
     */
    @Override
    public Integer insertRole(SystemRole role) {
        // 设置创建修改者
        String userName = SecurityUtils.getUserName();
        role.setCreateBy(userName);
        role.setUpdateBy(userName);
        return this.roleMapper.insertRole(role);
    }

    /**
     * Description: 根据请求参数导出角色列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-09-20 21:09
     */
    @Override
    public void export(SystemRoleRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, this.selectRoleBoList(requestParam), SystemRoleBo.class, "角色信息");
    }

    /**
     * Description: 插入角色数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:20
     */
    @Override
    public Integer insertRole(SystemRoleRequest requestParam) {
        // bean实体转换
        SystemRole role = BeanUtil.toBean(requestParam, SystemRole.class);

        // 插入角色信息
        Integer result = this.insertRole(role);
        if (result > 0) {
            // 新增角色菜单关联信息
            result = this.roleMenuService.batchRoleMenu(role.getId(), requestParam.getMenuIds());
        }

        return result;
    }

    /**
     * Description: 添加角色数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 19:18
     */
    @Override
    @Transactional
    public Integer addRole(SystemRoleRequest requestParam) {
        // 检查角色名唯一性
        if (this.checkRoleNameUnique(requestParam)) {
            throw new RoleException(StatusMsg.ROLE_NAME_NOT_UNIQUE);
        }

        // 检查角色权限字符唯一性
        if (this.checkRoleKeyUnique(requestParam)) {
            throw new RoleException(StatusMsg.ROLE_KEY_NOT_UNIQUE);
        }

        // 插入系统角色信息
        return this.insertRole(requestParam);
    }

    /**
     * Description: 授权角色数据权限
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 18:46
     */
    @Override
    @Transactional
    public Integer authDataScope(SystemRoleRequest requestParam) {
        // bean实体转换
        SystemRole role = BeanUtil.toBean(requestParam, SystemRole.class);
        role.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getRoleId()));

        // 修改角色信息 dataScope字段
        Integer result = this.updateRole(role);

        if (result > 0) {
            // 修改角色部门关联信息
            this.roleDeptService.updateRoleDeptByRoleId(role.getId(), requestParam.getDeptIds());
        }

        return result;
    }

    /**
     * Description: 根据角色键查询角色数据
     *
     * @param roleKey 角色键
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 18:01
     */
    @Override
    public SystemRole selectRoleByRoleKey(String roleKey) {
        return this.roleMapper.selectRoleByRoleKey(roleKey);
    }

    /**
     * Description: 根据角色名称查询角色数据
     *
     * @param roleName 角色名称
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 17:12
     */
    @Override
    public SystemRole selectRoleByRoleName(String roleName) {
        return this.roleMapper.selectRoleByRoleName(roleName);
    }

    /**
     * Description: 更新角色数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:42
     */
    @Override
    public Integer updateRole(SystemRoleRequest requestParam) {
        // 实体转换
        SystemRole role = BeanUtil.toBean(requestParam, SystemRole.class);
        // 设置角色id
        Long roleId = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getRoleId());
        role.setId(roleId);
        // 修改角色信息
        Integer result = this.updateRole(role);
        if (result > 0) {
            // 修改角色菜单关联信息
            result = roleMenuService.updateRoleMenuByRoleId(roleId, requestParam.getMenuIds());
        }
        return result;
    }

    /**
     * Description: 编辑角色信息
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 16:08
     */
    @Override
    @Transactional
    public Integer edit(SystemRoleRequest requestParam) {
        // 检查角色名唯一性
        if (this.checkRoleNameUnique(requestParam)) {
            throw new RoleException(StatusMsg.ROLE_NAME_NOT_UNIQUE);
        }

        // 检查角色权限字符唯一性
        if (this.checkRoleKeyUnique(requestParam)) {
            throw new RoleException(StatusMsg.ROLE_KEY_NOT_UNIQUE);
        }

        // 更新系统角色信息
        return this.updateRole(requestParam);
    }

    /**
     * Description: 检查角色名称唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-20 18:03
     */
    private boolean checkRoleNameUnique(SystemRoleRequest requestParam) {

        // 防止空指针 前端可能传roleId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getRoleId(), requestParam.getId());
        Long roleId = ObjectUtil.isNull(id) ? -1L : id;

        // 角色名称加载角色信息
        String roleName = requestParam.getRoleName();
        SystemRole role = this.selectRoleByRoleName(roleName);

        // 是否唯一
        return ObjectUtil.isNotNull(role) && !ObjectUtil.equals(roleId, role.getId());
    }

    /**
     * Description: 检查角色权限字符串唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-20 18:02
     */
    private boolean checkRoleKeyUnique(SystemRoleRequest requestParam) {

        // 防止空指针 前端可能传roleId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getRoleId(), requestParam.getId());
        Long roleId = ObjectUtil.isNull(id) ? -1L : id;

        // 角色权限字符从加载角色信息
        String roleKey = requestParam.getRoleKey();
        SystemRole role = this.selectRoleByRoleKey(roleKey);

        // 是否唯一
        return ObjectUtil.isNotNull(role) && !ObjectUtil.equals(roleId, role.getId());
    }

    /**
     * Description: 更新角色数据
     *
     * @param role 角色数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 15:43
     */
    @Override
    public Integer updateRole(SystemRole role) {
        // 设置修改者
        role.setUpdateBy(SecurityUtils.getUserName());
        return this.roleMapper.updateRole(role);
    }

    /**
     * Description: 改变角色状态
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 15:39
     */
    @Override
    public Integer changeStatus(SystemRoleRequest requestParam) {
        // 实体bean转换
        SystemRole role = BeanUtil.toBean(requestParam, SystemRole.class);
        // 设置id
        role.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getRoleId()));
        return this.updateRole(role);
    }

    /**
     * Description: 根据角色id查询分配角色用户数量
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:38
     */
    @Override
    public Integer selectCntUserRoleByRoleId(Long roleId) {
        return this.userRoleService.selectCntUserRoleByRoleId(roleId);
    }

    /**
     * Description: 根据角色id删除角色数据
     *
     * @param roleId 角色id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:07
     */
    @Override
    @Transactional
    public Integer deleteRoleById(Long roleId) {
        // 根据角色id删除角色数据
        Integer row = this.roleMapper.deleteRoleByRoleId(roleId);
        if (row > 0) {
            // 删除角色与菜单关联信息
            this.roleMenuService.deleteRoleMenusByRoleId(roleId);
            // 删除角色与部门关联信息
            this.roleDeptService.deleteRoleDeptByRoleId(roleId);
        }
        return row;
    }

    /**
     * Description: 根据角色id列表删除角色数据
     *
     * @param roleIds 角色id
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:38
     */
    @Override
    @Transactional
    public Integer deleteRoleByRoleIds(List<Long> roleIds) {
        // 校验是否含有已分配的角色，如已分配则无法删除
        roleIds.forEach((roleId) -> {
            SystemRole systemRole = this.roleMapper.selectRoleById(roleId);
            if (this.selectCntUserRoleByRoleId(roleId) > 0) {
                throw new RoleException(StrUtil.format("{}角色已分配用户", systemRole.getRoleName()));
            }
        });

        // 根据角色id列表删除角色数据
        Integer row = this.roleMapper.deleteRoleByRoleIds(roleIds);
        if (row > 0) {
            // 删除角色与菜单关联信息
            this.roleMenuService.deleteRoleMenus(roleIds);
            // 删除角色与部门关联信息
            this.roleDeptService.deleteRoleDepts(roleIds);
        }
        return row;
    }

    /**
     * Description: 添加分配角色用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:38
     */
    @Override
    public Integer addAuthUserAll(SystemRoleRequest requestParam) {
        // 角色id
        Long roleId = requestParam.getRoleId();
        // 用户id列表
        List<Long> userIds = requestParam.getUserIds();
        // 映射为用户角色关联实体
        List<SystemUserRole> userRoles = userIds.stream().map((userId) -> SystemUserRole.builder().roleId(roleId).userId(userId).build()).collect(Collectors.toList());
        return this.userRoleService.batchUserRole(userRoles);
    }

    /**
     * Description: 删除分配角色用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:39
     */
    @Override
    public Integer deleteAuthUsers(SystemRoleRequest requestParam) {
        // 角色id
        Long roleId = requestParam.getRoleId();
        // 该角色下的所有用户
        List<Long> userIds = requestParam.getUserIds();
        return this.userRoleService.deleteUserRoles(roleId, userIds);
    }

    /**
     * Description: 取消分配角色用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-20 13:39
     */
    @Override
    public Integer cancelAuthUser(SystemRoleRequest requestParam) {
        return this.userRoleService.deleteUserRole(BeanUtil.toBean(requestParam, SystemUserRole.class));
    }

    /**
     * Description: 查询角色列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemRole }> 角色列表数据
     * @date 2022-09-20 13:39
     */
    @Override
    @DataScope
    @DataSource(value = DataSourceName.SLAVE)
    public List<SystemRole> selectRoleList(SystemRoleRequest requestParam) {
        return this.roleMapper.selectRoleList(requestParam);
    }

    /**
     * Description: 根据角色id查询角色数据
     *
     * @param roleId 角色id
     * @return {@link SystemRole } 角色数据
     * @date 2022-09-20 13:39
     */
    @Override
    public SystemRole selectRoleById(Long roleId) {
        return this.roleMapper.selectRoleById(roleId);
    }

    /**
     * Description: 根据用户id查询所有角色数据
     * （已有角色isChoose为true反之为false）
     *
     * @param userId 用户id
     * @return {@link List }<{@link SystemRoleResponse }> 所有角色列表数据
     * @date 2022-09-14 15:11
     */
    @Override
    public List<SystemRoleResponse> selectAuthRoles(Long userId) {
        // 查询所有角色列表 用户已有角色 isChoose为true反正为false
        List<SystemRoleResponse> roles = this.roleMapper.selectAuthRoles(userId);
        // 非admin用户保留非admin角色
        return SystemUser.isAdmin(userId) ? roles : roles.stream().filter((role) -> !role.isAdmin()).collect(Collectors.toList());
    }

    /**
     * Description: 根据用户id查询角色id列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link Long }> 角色id列表数据
     * @date 2022-09-13 10:52
     */
    @Override
    public List<Long> selectRoleIdsByUserId(Long userId) {
        return this.roleMapper.selectRoleIdsByUserId(userId);
    }

    /**
     * Description: 查询所有角色列表数据
     *
     * @return {@link List }<{@link SystemRole }> 角色列表数据
     * @date 2022-09-13 09:26
     */
    @Override
    public List<SystemRole> selectRolesAll() {
        return this.roleMapper.selectRolesAll();
    }

    /**
     * Description:  根据用户id查询角色权限集合数据
     *
     * @param userId 用户id
     * @return {@link Set }<{@link String }> 角色权限集合
     * @date 2022-09-07 22:02
     */
    @Override
    public Set<String> selectRolePermissionsByUserId(Long userId) {
        // 调用系统菜单mapper查询用户菜单权限集合
        return roleMapper.selectRolePermissionsByUserId(userId)
                .stream()
                // 将菜单权限按照,分割后扁平化处理
                .map((roleKey) -> roleKey.trim().split(","))
                .flatMap(Arrays::stream)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
    }
}
