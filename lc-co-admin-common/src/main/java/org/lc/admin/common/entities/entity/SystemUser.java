package org.lc.admin.common.entities.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lc.admin.common.base.entities.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * Description: 用户
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-31  21:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemUser extends BaseEntity {

    private static final long serialVersionUID = 340101200201068068L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户类型（00系统用户）
     */
    private String userType;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 加密随机盐
     */
    private String salt;


    @Builder(toBuilder = true)
    public SystemUser(Integer orderNum, Boolean delFlag, Integer status, Long id, String createBy, LocalDateTime createTime, String updateBy, LocalDateTime updateTime, String remark, Long deptId, String userName, String userType, String nickName, String email, String phoneNumber, Integer sex, String avatar, String password, String loginIp, LocalDateTime loginTime, String salt) {
        super(orderNum, delFlag, status, id, createBy, createTime, updateBy, updateTime, remark);
        this.deptId = deptId;
        this.userName = userName;
        this.userType = userType;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.avatar = avatar;
        this.password = password;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.salt = salt;
    }

    public SystemUser(Long deptId, String userName, String userType, String nickName, String email, String phoneNumber, Integer sex, String avatar, String password, String loginIp, LocalDateTime loginTime, String salt) {
        this.deptId = deptId;
        this.userName = userName;
        this.userType = userType;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.avatar = avatar;
        this.password = password;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.salt = salt;
    }


    public SystemUser() {
    }

    public SystemUser(Integer orderNum, Boolean delFlag, Integer status, Long id, String createBy, LocalDateTime createTime, String updateBy, LocalDateTime updateTime, String remark) {
        super(orderNum, delFlag, status, id, createBy, createTime, updateBy, updateTime, remark);
    }

    /**
     * Description: 是否为管理admin用户
     *
     * @param userId 用户id
     * @return boolean
     * @date 2022-09-05 10:01
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * Description: 是否管理admin用户
     *
     * @return boolean
     * @date 2022-09-05 10:01
     */
    public boolean isAdmin() {
        return isAdmin(this.id);
    }
}
