package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.system.entities.bo.SystemUserBo;
import org.lc.admin.system.entities.request.SystemUserRequest;
import org.lc.admin.system.entities.response.SystemUserResponse;

import java.util.List;

/**
 * Description: 系统用户mapper接口
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  15:18
 */
public interface SystemUserMapper {

    /**
     * Description: 更新用户数据
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-05 22:48
     */
    int updateUser(SystemUser user);

    /**
     * Description: 查询用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-10 20:21
     */
    List<SystemUserResponse> selectUserList(SystemUserRequest requestParam);

    /**
     * Description: 根据用户id查询用户数据
     *
     * @param userId 用户id
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-13 10:33
     */
    SystemUser selectUserByUserId(@Param("userId") Long userId);

    /**
     * Description: 插入用户数据
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-13 16:43
     */
    Integer insertUser(SystemUser user);

    /**
     * Description: 根据用户名称查询用户数量
     *
     * @param userName 用户名
     * @return {@link Integer } 记录数
     * @date 2022-09-13 17:52
     */
    Integer selectCntUserByUserName(@Param("userName") String userName);

    /**
     * Description: 通过用户手机号码查询用户数量
     *
     * @param phoneNumber 手机号码
     * @return {@link Integer }  用户数量
     * @date 2022-09-13 17:56
     */
    Integer selectCntUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * Description: 通过用户邮箱查询用户数量
     *
     * @param email 电子邮件
     * @return {@link Integer } 用户数量
     * @date 2022-09-13 17:57
     */
    Integer selectCntUserByEmail(@Param("email") String email);

    /**
     * Description: 根据用户id列表删除用户数据
     *
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:45
     */
    Integer deleteUserByIds(@Param("userIds") List<Long> userIds);

    /**
     * Description: 查询用户Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserBo }> 用户Bo列表
     * @date 2022-09-19 14:41
     */
    List<SystemUserBo> selectUserBos(SystemUserRequest requestParam);

    /**
     * Description: 根据部门id查询用户数量
     *
     * @param deptId 部门id
     * @return {@link Long } 记录数
     * @date 2022-09-19 21:16
     */
    Integer selectCntUserByDeptId(@Param("deptId") Long deptId);

    /**
     * Description: 查询已分配角色的用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-20 16:27
     */
    List<SystemUserResponse> selectAllocatedUserList(SystemUserRequest requestParam);

    /**
     * Description: 查询未分配角色的用户列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemUserResponse }> 用户列表数据
     * @date 2022-09-20 16:27
     */
    List<SystemUserResponse> selectUnallocatedUserList(SystemUserRequest requestParam);

    /**
     * Description: 根据邮箱查询用户数据
     *
     * @param email 邮箱
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:17
     */
    SystemUser selectUserByEmail(@Param("email") String email);

    /**
     * Description: 根据用户手机号码查询用户数据
     *
     * @param phoneNumber 手机号码
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:17
     */
    SystemUser selectUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * Description: 根据用户名称查询用户数据
     *
     * @param userName 用户名
     * @return {@link SystemUser } 用户数据
     * @date 2022-09-23 16:18
     */
    SystemUser selectUserByUserName(@Param("userName") String userName);

    /**
     * Description: 重置用户密码数据
     *
     * @param userName 用户名
     * @param password 密码
     * @param salt     随机盐
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:18
     */
    Integer resetUserPwd(@Param("userName") String userName, @Param("password") String password, @Param("salt") String salt);

    /**
     * Description: 更新用户头像数据
     *
     * @param userName 用户名
     * @param avatar   头像url
     * @return {@link Integer } 记录数
     * @date 2022-09-23 16:18
     */
    Integer updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * Description: 根据用户数据记录登录信息
     *
     * @param user 用户数据
     * @return {@link Integer } 记录数
     * @date 2022-09-24 11:48
     */
    Integer recordLoginInfo(SystemUser user);

    /**
     * Description: 根据用户id删除用户数据
     *
     * @param userId 用户id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 21:54
     */
    Integer deleteUserById(@Param("userId") Long userId);
}
