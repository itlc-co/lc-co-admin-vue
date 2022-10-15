package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.annotation.DataScope;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.common.exec.UserException;
import org.lc.admin.common.utils.system.AuthUserUtils;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.bo.SystemUserBo;
import org.lc.admin.system.entities.entity.SystemUserPost;
import org.lc.admin.system.entities.entity.SystemUserRole;
import org.lc.admin.system.entities.request.SystemUserRequest;
import org.lc.admin.system.entities.response.SystemUserResponse;
import org.lc.admin.system.mapper.SystemUserMapper;
import org.lc.admin.system.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 系统用户service服务实现
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  11:54
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    /**
     * 用户列表导入模板url
     */
    private static final String USER_LIST_IMPORT_TEMPLATE_URL = "excel/user_import_template.xlsx";

    @Resource
    private SystemUserMapper userMapper;

    @Resource
    private SystemRoleService roleService;

    @Resource
    private SystemPostService postService;

    @Resource
    private SystemUserRoleService userRoleService;

    @Resource
    private SystemUserPostService userPostService;

    @Resource
    private SystemConfigService configService;

    /**
     * Description: 更新用户数据
     *
     * @param systemUser 用户数据
     * @return int 记录数
     * @date 2022-09-05 22:47
     */
    @Override
    public int updateUserInfo(SystemUser systemUser) {
        return this.updateUser(systemUser);
    }

    /**
     * Description: 更新用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:03
     */
    @Override
    public Integer updateUser(SystemUserRequest requestParam) {
        // 实体转换 请求参数实体转换为db实体
        SystemUser user = BeanUtil.toBean(requestParam, SystemUser.class);
        // 防止空指针
        user.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getUserId()));

        // 是否修改密码，修改密码则重新配置密文密码与随机盐
        String password = requestParam.getPassword();
        if (StrUtil.isNotBlank(password)) {
            user.setSalt(SecurityUtils.prodSalt());
            user.setPassword(SecurityUtils.encryptPassword(password, user.getSalt()));
        }

        // 修改用户实体到db中返回记录数
        Integer row = this.updateUser(user);

        // 修改用户岗位关联信息
        this.updateUserPost(user.getId(), requestParam.getPostIds());

        // 修改用户角色关联信息
        this.updateUserRole(user.getId(), requestParam.getRoleIds());

        return row;
    }

    /**
     * Description: 更新系统用户角色关联数据
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @date 2022-09-13 22:08
     */
    public void updateUserRole(Long userId, List<Long> roleIds) {
        // 删除用户与角色关联信息
        this.userRoleService.deleteUserRolesByUserId(userId);
        if (CollUtil.isNotEmpty(roleIds)) {
            // 插入用户与角色关联信息列表
            List<SystemUserRole> userRoles = roleIds.stream().map((roleId) -> SystemUserRole.builder().userId(userId).roleId(roleId).build()).collect(Collectors.toList());
            this.userRoleService.batchUserRole(userRoles);
        }
    }

    /**
     * Description: 更新系统用户岗位关联数据
     *
     * @param userId  用户id
     * @param postIds 岗位id列表
     * @date 2022-09-13 22:08
     */
    public void updateUserPost(Long userId, List<Long> postIds) {
        // 删除用户与岗位关联信息
        this.userPostService.deleteUserPostsByUserId(userId);
        if (CollUtil.isNotEmpty(postIds)) {
            // 插入用户与岗位关联信息
            List<SystemUserPost> userPosts = postIds.stream().map((postId) -> SystemUserPost.builder().userId(userId).postId(postId).build()).collect(Collectors.toList());
            this.userPostService.batchUserPost(userPosts);
        }
    }

    /**
     * Description: 编辑用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:01
     */
    @Override
    @Transactional
    public Integer editUser(SystemUserRequest requestParam) {
        // 判断手机号码唯一性
        if (this.checkPhoneNumberUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_PHONE_NUMBER_EXIST);
        }
        // 判断邮箱唯一性
        if (this.checkEmailUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_EMAIL_EXIST);
        }
        // 更新用户实体至数据库
        return this.updateUser(requestParam);
    }

    /**
     * Description: 插入用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-13 15:53
     */
    @Override
    public Integer insertUser(SystemUserRequest requestParam) {
        // 实体转换 请求参数实体转换为db实体
        SystemUser user = BeanUtil.toBean(requestParam, SystemUser.class);

        // 插入用户实体到db中返回记录数
        Integer row = this.insertUser(user);

        // 插入用户岗位关联信息
        this.insertUserPost(user.getId(), requestParam.getPostIds());

        // 插入用户角色关联信息
        this.insertUserRole(user.getId(), requestParam.getRoleIds());

        return row;
    }

    /**
     * Description: 插入系统用户角色关联数据
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @date 2022-09-13 19:11
     */
    public void insertUserRole(Long userId, List<Long> roleIds) {
        if (CollUtil.isNotEmpty(roleIds)) {
            // 用户与岗位关联信息列表
            List<SystemUserRole> userRoles = roleIds.stream().map((roleId) -> SystemUserRole.builder().userId(userId).roleId(roleId).build()).collect(Collectors.toList());
            this.userRoleService.batchUserRole(userRoles);
        }
    }

    /**
     * Description: 改变用户状态
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:10
     */
    @Override
    public Integer changeStatus(SystemUserRequest requestParam) {
        // 转换系统用户实体
        SystemUser user = SystemUser.builder()
                .id(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getUserId()))
                .status(requestParam.getStatus())
                .updateBy(SecurityUtils.getUserName())
                .build();
        return this.userMapper.updateUser(user);
    }

    /**
     * Description: 根据用户名查询用户角色组
     *
     * @param userName 用户名
     * @return {@link String } 角色组数据
     * @date 2022-09-23 16:09
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        return StrUtil.join(",", this.roleService.selectRoleNamesByUserName(userName));
    }

    /**
     * Description: 根据用户名查询用户岗位组
     *
     * @param userName 用户名
     * @return {@link String } 岗位组
     * @date 2022-09-23 16:09
     */
    @Override
    public String selectUserPostGroup(String userName) {
        return StrUtil.join(",", this.postService.selectPostNamesByUserName(userName));
    }

    /**
     * Description: 更新用户信息数据
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:09
     */
    @Override
    public Integer updateUserProfile(SystemUser user) {
        // 设置修改者
        String userName = SecurityUtils.getUserName();
        user.setUpdateBy(userName);
        return this.userMapper.updateUser(user);
    }

    /**
     * Description: 根据邮箱查询用户数据
     *
     * @param email 邮箱
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:10
     */
    @Override
    public SystemUser selectUserByEmail(String email) {
        return this.userMapper.selectUserByEmail(email);
    }

    /**
     * Description: 根据用户手机号码查询用户数据
     *
     * @param phoneNumber 手机号码
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:10
     */
    @Override
    public SystemUser selectUserByPhoneNumber(String phoneNumber) {
        return this.userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * Description: 根据用户名称查询用户数据
     *
     * @param userName 用户名
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:10
     */
    @Override
    public SystemUser selectUserByUserName(String userName) {
        return this.userMapper.selectUserByUserName(userName);
    }

    /**
     * Description: 更新用户密码数据
     *
     * @param updatePwdMap 更新密码参数map
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:10
     */
    @Override
    public Integer updateUserPwd(Map<String, Object> updatePwdMap) {
        // 参数map数据
        String oldPassword = StrUtil.toString(updatePwdMap.get("oldPassword"));
        String newPassword = StrUtil.toString(updatePwdMap.get("newPassword"));
        String newSalt = StrUtil.toString(updatePwdMap.get("salt"));

        // 修改前用户详情信息
        UserDetail userDetail = SecurityUtils.getUserDetail();
        String userName = userDetail.getUsername();
        String password = userDetail.getPassword();
        String oldSalt = userDetail.getSalt();

        if (!SecurityUtils.matchesPassword(oldPassword, oldSalt, password)) {
            // 旧密码不正确
            throw new UserException(StatusMsg.USER_OLD_PASSWORD_ERROR);
        }

        return this.resetUserPwd(userName, newPassword, newSalt);
    }

    /**
     * Description: 重置用户密码数据
     *
     * @param userName 用户名
     * @param password 密码
     * @param salt     随机盐
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:10
     */
    @Override
    public Integer resetUserPwd(String userName, String password, String salt) {
        return this.userMapper.resetUserPwd(userName, SecurityUtils.encryptPassword(password, salt), salt);
    }

    /**
     * Description: 更新用户头像数据
     *
     * @param userName 用户名
     * @param avatar   头像url
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:10
     */
    @Override
    public Integer updateUserAvatar(String userName, String avatar) {
        return this.userMapper.updateUserAvatar(userName, avatar);
    }

    /**
     * Description: 检查用户是否允许
     *
     * @param userId 用户id
     * @return boolean true 允许 false 不允许
     * @date 2022-10-04 10:07
     */
    @Override
    public boolean checkUserAllowed(Long userId) {
        // 不允许操作admin用户
        return !AuthUserUtils.isAdmin(userId);
    }

    /**
     * Description: 校验用户是否有数据权限
     *
     * @param userId 用户id
     * @return boolean true 具有数据权限 false 不具有数据权限
     * @date 2022-10-04 09:50
     */
    @Override
    public boolean checkUserDataScope(Long userId) {
        boolean flag = true;
        // 登录用户非admin才需要校验
        if (!AuthUserUtils.isAdmin(SecurityUtils.getUserId())) {

            // 构造查询参数
            SystemUserRequest requestParam = new SystemUserRequest();
            requestParam.setUserId(userId);

            // 根据用户id查询用户列表数据
            List<SystemUserResponse> users = this.selectUserList(requestParam);

            // 用户列表数据转换为用户id集合
            Set<Long> userIds = users.stream().map(SystemUserResponse::getId).distinct().filter(ObjectUtil::isNotNull).collect(Collectors.toSet());

            // 用户id集合是否为空
            // 空表示没有数据权限返回false 反之亦然
            flag = CollUtil.isNotEmpty(userIds);

        }
        return flag;
    }

    /**
     * Description: 根据用户数据记录登录信息
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-24 11:47
     */
    @Override
    public Integer recordLoginInfo(SystemUser user) {
        return this.userMapper.recordLoginInfo(user);
    }

    /**
     * Description: 根据用户名称查询用户数量
     *
     * @param userName 用户名
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:10
     */
    @Override
    public Integer selectCntUserByUserName(String userName) {
        return this.userMapper.selectCntUserByUserName(userName);
    }

    /**
     * Description: 更新用户信息数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:09
     */
    @Override
    public Integer updateUserProfile(SystemUserRequest requestParam) {

        // 检查用户名唯一性
        if (this.checkUserNameUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_NAME_EXIST);
        }

        // 检查手机号唯一性
        if (this.checkPhoneNumberUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_PHONE_NUMBER_EXIST);
        }

        // 检查邮箱唯一性
        if (this.checkEmailUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_EMAIL_EXIST);
        }

        // 实体转换
        SystemUser user = BeanUtil.toBean(requestParam, SystemUser.class, CopyOptions.create().setIgnoreProperties("password"));
        user.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getUserId()));

        // 更新登录用户信息
        return this.updateUserProfile(user);
    }

    /**
     * Description: 检查邮箱唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一性 false 唯一
     * @date 2022-09-23 16:14
     */
    private boolean checkEmailUnique(SystemUserRequest requestParam) {
        // 防止空指针 前端可能传userId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getUserId());
        Long userId = ObjectUtil.isNull(id) ? -1L : id;

        // 邮箱加载用户信息
        String email = requestParam.getEmail();
        SystemUser user = this.selectUserByEmail(email);

        // 是否唯一
        return ObjectUtil.isNotNull(user) && !ObjectUtil.equals(userId, user.getId());
    }

    /**
     * Description: 检查手机号码唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一性 false 唯一
     * @date 2022-09-23 16:15
     */
    private boolean checkPhoneNumberUnique(SystemUserRequest requestParam) {

        // 防止空指针 前端可能传userId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getUserId());
        Long userId = ObjectUtil.isNull(id) ? -1L : id;

        // 手机号码加载用户信息
        String phoneNumber = requestParam.getPhoneNumber();
        SystemUser user = this.selectUserByPhoneNumber(phoneNumber);

        // 是否唯一
        return ObjectUtil.isNotNull(user) && !ObjectUtil.equals(userId, user.getId());

    }

    /**
     * Description: 检查用户名称唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一性 false 唯一
     * @date 2022-09-23 16:15
     */
    private boolean checkUserNameUnique(SystemUserRequest requestParam) {

        // 防止空指针 前端可能传userId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getUserId());
        Long userId = ObjectUtil.isNull(id) ? -1L : id;

        // 用户名称加载用户信息
        String userName = requestParam.getUserName();
        SystemUser user = this.selectUserByUserName(userName);

        // 是否唯一
        return ObjectUtil.isNotNull(user) && !ObjectUtil.equals(userId, user.getId());
    }

    /**
     * Description: 更新用户数据
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:09
     */
    @Override
    public Integer updateUser(SystemUser user) {
        // 设置修改者
        user.setUpdateBy(SecurityUtils.getUserName());
        return this.userMapper.updateUser(user);
    }

    /**
     * Description: 插入用户数据
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 20:27
     */
    @Override
    public Integer insertUser(SystemUser user) {
        // 设置创建人，修改人以及生成随机盐对明文密码加密
        String userName = SecurityUtils.getUserName();
        String salt = SecurityUtils.prodSalt();
        String password = user.getPassword();
        user.setCreateBy(userName);
        user.setUpdateBy(userName);
        if (StrUtil.isNotBlank(password)) {
            user.setSalt(salt);
            user.setPassword(SecurityUtils.encryptPassword(password, salt));
        }
        return this.userMapper.insertUser(user);
    }

    /**
     * Description: 导入用户列表
     *
     * @param userBos       用户Bo实体列表
     * @param updateSupport 更新是否支持
     * @return {@link Map }<{@link String }, {@link Set }<{@link String }>> 响应结果消息map
     * @date 2022-09-23 16:08
     */
    @Override
    public Map<String, Set<String>> importUser(List<SystemUserBo> userBos, boolean updateSupport) {
        // 结果集
        Map<String, Set<String>> resultMap = MapUtil.newHashMap(2);
        Set<String> successUserNames = CollUtil.newHashSet();
        Set<String> failUserNames = CollUtil.newHashSet();

        // 不存在用户导入列表
        if (CollUtil.isEmpty(userBos)) {
            throw new FileException(StatusMsg.EXCEL_IMPORT_EMPTY);
        }

        // 初始密码配置项
        String password = configService.selectConfigValueByKey("initPassword");
        if (StrUtil.isBlank(password)) {
            throw new UserException(StatusMsg.CONFIG_NOT_DEFAULT_PASSWORD);
        }

        // bean实体类别转换
        List<SystemUser> users = BeanUtil.copyToList(userBos, SystemUser.class);

        users.forEach((user) -> {
            // 用户名称
            String userName = user.getUserName();
            try {
                // 验证是否存在这个用户
                SystemUser systemUser = this.selectUserByUserName(userName);
                if (ObjectUtil.isEmpty(systemUser)) {
                    // 不存在则插入
                    String salt = SecurityUtils.prodSalt();
                    user.setPassword(SecurityUtils.encryptPassword(password, salt));
                    user.setSalt(salt);
                    this.insertUser(user);
                    successUserNames.add(userName);
                } else if (updateSupport) {
                    // 存在且支持修改
                    this.updateUser(user);
                    successUserNames.add(userName);
                } else {
                    // 导入失败
                    failUserNames.add(userName);
                }
            } catch (Exception e) {
                failUserNames.add(userName);
            }
        });
        resultMap.put("successUserNames", successUserNames);
        resultMap.put("failUserNames", failUserNames);
        return resultMap;
    }

    /**
     * Description: 查询未分配角色的用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-20 16:27
     */
    @Override
    @DataScope
    public List<SystemUserResponse> selectUnallocatedUserList(SystemUserRequest requestParam) {
        return this.userMapper.selectUnallocatedUserList(requestParam);
    }

    /**
     * Description: 查询已分配角色的用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-20 16:27
     */
    @Override
    @DataScope
    public List<SystemUserResponse> selectAllocatedUserList(SystemUserRequest requestParam) {
        return this.userMapper.selectAllocatedUserList(requestParam);
    }

    /**
     * Description: 根据部门id查询用户数量
     *
     * @param deptId 部门id
     * @return {@link Long } 记录数
     * @date 2022-09-19 21:14
     */
    @Override
    public Integer selectCntUserByDeptId(Long deptId) {
        return this.userMapper.selectCntUserByDeptId(deptId);
    }

    /**
     * Description: 查询用户Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserBo }> 用户Bo列表
     * @date 2022-09-19 14:40
     */
    @Override
    @DataScope
    public List<SystemUserBo> selectUserBoList(SystemUserRequest requestParam) {
        return this.userMapper.selectUserBos(requestParam);
    }

    /**
     * Description: 根据请求参数导出用户列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-09-14 22:34
     */
    @Override
    public void export(SystemUserRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, this.selectUserBoList(requestParam), SystemUserBo.class, "用户信息");
    }

    /**
     * Description: 用户列表数据excel导入模板下载
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException io异常
     * @date 2022-09-14 17:05
     */
    @Override
    public void importTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 导出用户列表数据excel导入模板
        ExcelUtils.exportExcel(response, MapUtil.of("users", this.selectUsersImportTemplate()), USER_LIST_IMPORT_TEMPLATE_URL);
    }

    /**
     * Description: 查询用户导入模板模拟数据
     *
     * @return {@link List }<{@link SystemUser }> 模拟数据
     * @date 2022-09-14 22:01
     */
    private List<SystemUser> selectUsersImportTemplate() {
        return ListUtil.of(
                SystemUser.builder().id(1L)
                        .deptId(100L)
                        .userName("import")
                        .nickName("导入")
                        .email("123@qq.com")
                        .phoneNumber("123456789")
                        .sex(1)
                        .status(0)
                        .build()
        );
    }

    /**
     * Description: 授权用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @date 2022-09-14 13:09
     */
    @Override
    @Transactional
    public void authRole(Long userId, List<Long> roleIds) {
        this.updateUserRole(userId, roleIds);
    }

    /**
     * Description: 根据用户id查询认证角色数据
     *
     * @param userId 用户id
     * @return {@link Map }<{@link String }, {@link Object }> 认证角色数据
     * @date 2022-09-14 11:43
     */
    @Override
    public Map<String, Object> getAuthRoles(Long userId) {
        // 结果集map
        Map<String, Object> data = MapUtil.newHashMap();
        // 获取用户信息
        data.put("user", this.selectUserByUserId(userId));
        // 获取角色信息
        data.put("roles", this.roleService.selectAuthRoles(userId));
        return data;
    }

    /**
     * Description: 根据用户id删除用户数据
     *
     * @param userId 用户id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 21:54
     */
    @Override
    @Transactional
    public Integer deleteUserById(Long userId) {
        // 批量删除用户返回记录数
        Integer row = this.userMapper.deleteUserById(userId);
        if (row > 0) {
            // 删除用户岗位关联信息
            this.userPostService.deleteUserPostsByUserId(userId);
            // 删除用户角色关联信息
            this.userRoleService.deleteUserRolesByUserId(userId);
        }
        return row;
    }

    /**
     * Description: 根据用户id列表删除用户数据
     *
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:41
     */
    @Override
    @Transactional
    public Integer deleteUserByIds(List<Long> userIds) {
        // 批量删除用户返回记录数
        Integer row = this.userMapper.deleteUserByIds(userIds);
        if (row > 0) {
            // 删除用户岗位关联信息
            this.userPostService.deleteUserPostsByUserIds(userIds);
            // 删除用户角色关联信息
            this.userRoleService.deleteUserRolesByUserIds(userIds);
        }
        return row;
    }

    /**
     * Description: 重置用户密码
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:12
     */
    @Override
    public Integer resetPwd(SystemUserRequest requestParam) {
        // 生成加密随机盐
        String salt = SecurityUtils.prodSalt();
        // 转换系统用户实体
        SystemUser user = SystemUser.builder()
                .id(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getUserId()))
                // 修改者
                .updateBy(SecurityUtils.getUserName())
                // 随机盐
                .salt(salt)
                // 密文密码
                .password(SecurityUtils.encryptPassword(requestParam.getPassword(), salt))
                .build();
        return this.userMapper.updateUser(user);
    }

    /**
     * Description: 插入系统用户岗位关联数据
     *
     * @param userId  用户id
     * @param postIds 岗位id列表
     * @date 2022-09-13 19:12
     */
    public void insertUserPost(Long userId, List<Long> postIds) {
        if (CollUtil.isNotEmpty(postIds)) {
            // 用户与岗位关联信息列表
            List<SystemUserPost> userPosts = postIds.stream().map((postId) -> SystemUserPost.builder().userId(userId).postId(postId).build()).collect(Collectors.toList());
            this.userPostService.batchUserPost(userPosts);
        }
    }

    /**
     * Description: 添加用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-13 15:44
     */
    @Override
    @Transactional
    public Integer addUser(SystemUserRequest requestParam) {
        // 判断用户名唯一性
        if (this.checkUserNameUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_EXIST);
        }
        // 判断手机号码唯一性
        if (this.checkPhoneNumberUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_PHONE_NUMBER_EXIST);
        }
        // 判断邮箱唯一性
        if (this.checkEmailUnique(requestParam)) {
            throw new UserException(StatusMsg.USER_EMAIL_EXIST);
        }
        // 插入用户实体至数据库
        return this.insertUser(requestParam);
    }


    /**
     * Description: 根据用户id查询用户数据
     *
     * @param userId 用户id
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-13 10:32
     */
    @Override
    public SystemUser selectUserByUserId(Long userId) {
        return this.userMapper.selectUserByUserId(userId);
    }

    /**
     * Description: 根据用户id插值用户详情并封装map
     *
     * @param userId 用户id
     * @return {@link Map }<{@link String }, {@link Object }> 用户详情信息map
     * @date 2022-09-13 10:28
     */
    @Override
    public Map<String, Object> userDetail(Long userId) {
        Map<String, Object> userDetailMap = MapUtil.newHashMap();
        if (ObjectUtil.isNotEmpty(userId)) {
            // 用户详情信息
            userDetailMap.put("user", this.selectUserByUserId(userId));
            // 角色id列表
            userDetailMap.put("roleIds", this.roleService.selectRoleIdsByUserId(userId));
            // 岗位id列表
            userDetailMap.put("postIds", this.postService.selectPostIdsByUserId(userId));
        }
        return userDetailMap;
    }

    /**
     * Description: 查询用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-10 20:20
     */
    @Override
    @DataScope
    public List<SystemUserResponse> selectUserList(SystemUserRequest requestParam) {
        return this.userMapper.selectUserList(requestParam).stream().peek((userResponse) -> userResponse.setRoleNames(userResponse.getRoleNames().stream()
                // 将用户角色集合按照,分割后扁平化处理
                .map((roleNames) -> roleNames.trim().split(","))
                .flatMap(Arrays::stream)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet()))).collect(Collectors.toList());
    }

}
