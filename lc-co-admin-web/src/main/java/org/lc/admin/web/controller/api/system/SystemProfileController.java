package org.lc.admin.web.controller.api.system;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.entities.entity.SystemUser;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.exec.UserException;
import org.lc.admin.common.utils.file.MimeTypeUtils;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.framework.config.ApplicationConfig;
import org.lc.admin.framework.security.service.TokenService;
import org.lc.admin.framework.service.FileService;
import org.lc.admin.system.entities.request.SystemUserRequest;
import org.lc.admin.system.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Description: 系统用户信息controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 20:40
 */
@RestController
@RequestMapping("/system/user/profile")
public class SystemProfileController extends BaseController {

    public static final String MODULE = "system_user_profile";

    public static final Logger log = LoggerFactory.getLogger(SystemProfileController.class);

    @Resource
    private SystemUserService userService;

    @Resource
    private TokenService tokenService;

    @Resource
    private FileService fileService;


    /**
     * Description: 用户个人信息详情接口
     *
     * @return {@link ResultResponse } 响应结果集
     * @date 2022-09-23 11:19
     */
    @GetMapping()
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse profile() {
        // 用户详情
        UserDetail userDetail = getUserDetail();
        SystemUser user = userDetail.getUser();
        String userName = userDetail.getUsername();
        ResultResponse response = success(user);
        // 角色组，岗位组数据
        response.put("roleGroup", this.userService.selectUserRoleGroup(userName));
        response.put("postGroup", this.userService.selectUserPostGroup(userName));
        return response;
    }

    /**
     * Description: 更新个人信息数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 11:19
     */
    @PutMapping
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse updateProfile(@RequestBody SystemUserRequest requestParam) {
        // 默认失败响应
        ResultResponse response = fail();
        // 获取登录用户详情实体
        UserDetail userDetail = getUserDetail();
        AuthUser user = userDetail.getUser();
        // 更新用户个人信息
        Integer row = this.userService.updateUserProfile(requestParam);
        if (row > 0) {
            // 更新缓存用户信息
            user.setNickName(requestParam.getNickName());
            user.setPhoneNumber(requestParam.getPhoneNumber());
            user.setEmail(requestParam.getEmail());
            user.setSex(requestParam.getSex());
            this.tokenService.setUserDetail(userDetail);
            response = success();
        }
        return response;
    }


    /**
     * Description: 更新密码接口
     *
     * @param updatePwdMap 更新密码参数map
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 11:20
     */
    @PutMapping("/updatePwd")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse updatePwd(@RequestBody Map<String, Object> updatePwdMap) {
        // 默认失败响应
        ResultResponse response = fail();
        // 校验重置密码参数
        if (!validateParam(updatePwdMap)) {
            throw new UserException(StatusMsg.USER_RESET_PWD_PARAM_ERROR);
        }
        // 新密码
        String newPassword = StrUtil.toString(updatePwdMap.get("newPassword"));
        // 生成随机盐
        String salt = SecurityUtils.prodSalt();
        updatePwdMap.put("salt", salt);

        // 获取登录用户详情实体
        UserDetail userDetail = getUserDetail();
        AuthUser user = userDetail.getUser();

        // 重置用户密码
        Integer row = this.userService.updateUserPwd(updatePwdMap);
        if (row > 0) {
            // 更新缓存用户密码
            user.setPassword(SecurityUtils.encryptPassword(newPassword, salt));
            user.setSalt(salt);
            this.tokenService.setUserDetail(userDetail);
            response = success();
        }

        return response;
    }


    /**
     * Description: 头像上传接口
     *
     * @param file 文件
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 11:20
     */
    @PostMapping("/avatar")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse avatar(MultipartFile file) {
        // 默认失败响应
        ResultResponse response = fail(StatusMsg.FILE_EMPTY);
        if (!file.isEmpty()) {
            // 获取用户详情
            UserDetail userDetail = getUserDetail();
            String userName = userDetail.getUsername();
            AuthUser user = userDetail.getUser();

            // 上传头像图片返回头像url
            String avatar = fileService.upload(file, ApplicationConfig.getAvatarPath(), MimeTypeUtils.IMAGE_EXTENSION);
            if (userService.updateUserAvatar(userName, avatar) > 0) {
                response = success();
                response.put("avatar", avatar);
                // 更新缓存用户头像
                user.setAvatar(avatar);
                tokenService.setUserDetail(userDetail);
            }
        }
        return response;
    }

    /**
     * Description: 验证修改密码参数
     *
     * @param updatePwdMap 更新密码参数map
     * @return boolean true 校验通过 false 并未通过
     * @date 2022-09-22 10:44
     */
    private boolean validateParam(Map<String, Object> updatePwdMap) {
        String newPassword = StrUtil.toString(updatePwdMap.getOrDefault("newPassword", ""));
        String oldPassword = StrUtil.toString(updatePwdMap.getOrDefault("oldPassword", ""));
        return StrUtil.isAllNotBlank(newPassword, oldPassword) && !StrUtil.equalsIgnoreCase(newPassword, oldPassword);
    }


}
