package org.lc.admin.system.service;

import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.system.entities.bo.SystemUserBo;
import org.lc.admin.system.entities.request.SystemUserRequest;
import org.lc.admin.system.entities.response.SystemUserResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: 系统用户service服务接口
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  11:53
 */
public interface SystemUserService {

    /**
     * Description: 更新用户数据
     *
     * @param systemUser 用户数据
     * @return int 记录数
     * @date 2022-09-05 22:42
     */
    int updateUserInfo(SystemUser systemUser);

    /**
     * Description: 查询用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-23 15:51
     */
    List<SystemUserResponse> selectUserList(SystemUserRequest requestParam);

    /**
     * Description: 根据用户id插值用户详情并封装map
     *
     * @param userId 用户id
     * @return {@link Map }<{@link String }, {@link Object }> 用户详情信息map
     * @date 2022-09-23 15:52
     */
    Map<String, Object> userDetail(Long userId);

    /**
     * Description: 根据用户id查询用户数据
     *
     * @param userId 用户id
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-13 10:31
     */
    SystemUser selectUserByUserId(Long userId);

    /**
     * Description: 添加用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-13 15:43
     */
    Integer addUser(SystemUserRequest requestParam);

    /**
     * Description: 插入用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-13 15:53
     */
    Integer insertUser(SystemUserRequest requestParam);

    /**
     * Description: 编辑用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:01
     */
    Integer editUser(SystemUserRequest requestParam);

    /**
     * Description: 更新用户数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:02
     */
    Integer updateUser(SystemUserRequest requestParam);

    /**
     * Description: 改变用户状态
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:07
     */
    Integer changeStatus(SystemUserRequest requestParam);

    /**
     * Description: 重置用户密码
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:08
     */
    Integer resetPwd(SystemUserRequest requestParam);

    /**
     * Description: 根据用户id删除用户数据
     *
     * @param userId 用户id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 21:54
     */
    Integer deleteUserById(Long userId);

    /**
     * Description: 根据用户id列表删除用户数据
     *
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:40
     */
    Integer deleteUserByIds(List<Long> userIds);

    /**
     * Description: 根据用户id查询认证角色数据
     *
     * @param userId 用户id
     * @return {@link Map }<{@link String }, {@link Object }> 认证角色数据
     * @date 2022-09-14 11:43
     */
    Map<String, Object> getAuthRoles(Long userId);

    /**
     * Description: 授权用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @date 2022-09-14 13:08
     */
    void authRole(Long userId, List<Long> roleIds);

    /**
     * Description: 用户列表数据excel导入模板下载
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException io异常
     * @date 2022-10-07 17:20
     */
    void importTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * Description: 根据请求参数导出用户列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-07 16:46
     */
    void export(SystemUserRequest requestParam, HttpServletResponse response) throws IOException;

    /**
     * Description: 查询用户Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserBo }> 用户Bo列表
     * @date 2022-09-19 14:40
     */
    List<SystemUserBo> selectUserBoList(SystemUserRequest requestParam);

    /**
     * Description: 根据部门id查询用户数量
     *
     * @param deptId 部门id
     * @return {@link Long } 记录数
     * @date 2022-09-19 21:14
     */
    Integer selectCntUserByDeptId(Long deptId);

    /**
     * Description: 查询已分配角色的用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-20 16:26
     */
    List<SystemUserResponse> selectAllocatedUserList(SystemUserRequest requestParam);

    /**
     * Description: 查询未分配角色的用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-20 16:26
     */
    List<SystemUserResponse> selectUnallocatedUserList(SystemUserRequest requestParam);

    /**
     * Description: 导入用户列表
     *
     * @param userBos       用户Bo实体列表
     * @param updateSupport 更新是否支持
     * @return {@link Map }<{@link String }, {@link Set }<{@link String }>> 响应结果消息map
     * @date 2022-09-23 16:08
     */
    Map<String, Set<String>> importUser(List<SystemUserBo> userBos, boolean updateSupport);

    /**
     * Description: 插入用户数据
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 20:27
     */
    Integer insertUser(SystemUser user);

    /**
     * Description: 更新用户数据
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-20 20:36
     */
    Integer updateUser(SystemUser user);

    /**
     * Description: 根据用户名查询用户角色组
     *
     * @param userName 用户名
     * @return {@link String } 角色组数据
     * @date 2022-09-23 15:58
     */
    String selectUserRoleGroup(String userName);

    /**
     * Description: 根据用户名查询用户岗位组
     *
     * @param userName 用户名
     * @return {@link String } 岗位组
     * @date 2022-09-23 15:59
     */
    String selectUserPostGroup(String userName);

    /**
     * Description: 更新用户信息数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 15:59
     */
    Integer updateUserProfile(SystemUserRequest requestParam);

    /**
     * Description: 更新用户信息数据
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:00
     */
    Integer updateUserProfile(SystemUser user);

    /**
     * Description: 根据邮箱查询用户数据
     *
     * @param email 邮箱
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:00
     */
    SystemUser selectUserByEmail(String email);

    /**
     * Description: 根据用户手机号码查询用户数据
     *
     * @param phoneNumber 手机号码
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:00
     */
    SystemUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * Description: 根据用户名称查询用户数据
     *
     * @param userName 用户名
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:01
     */
    SystemUser selectUserByUserName(String userName);

    /**
     * Description: 更新用户密码数据
     *
     * @param updatePwdMap 更新密码参数map
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:02
     */
    Integer updateUserPwd(Map<String, Object> updatePwdMap);

    /**
     * Description: 重置用户密码数据
     *
     * @param userName 用户名
     * @param password 密码
     * @param salt     随机盐
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:02
     */
    Integer resetUserPwd(String userName, String password, String salt);

    /**
     * Description: 更新用户头像数据
     *
     * @param userName 用户名
     * @param avatar   头像url
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:03
     */
    Integer updateUserAvatar(String userName, String avatar);

    /**
     * Description: 根据用户名称查询用户数量
     *
     * @param userName 用户名
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:03
     */
    Integer selectCntUserByUserName(String userName);

    /**
     * Description: 根据用户数据记录登录信息
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-24 11:46
     */
    Integer recordLoginInfo(SystemUser user);

    /**
     * Description: 校验用户是否有数据权限
     *
     * @param userId 用户id
     * @return boolean true 具有数据权限 false 不具有数据权限
     * @date 2022-10-04 09:50
     */
    boolean checkUserDataScope(Long userId);

    /**
     * Description: 检查用户是否允许
     *
     * @param userId 用户id
     * @return boolean true 允许 false 不允许
     * @date 2022-10-04 10:07
     */
    boolean checkUserAllowed(Long userId);
}
