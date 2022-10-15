package org.lc.admin.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Description: app应用配置
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  19:46
 */
@Configuration
@ConfigurationProperties(prefix = "admin")
@ComponentScan(basePackages = {"org.lc.admin"})
@EnableAspectJAutoProxy(exposeProxy = true)
public class ApplicationConfig {

    /**
     * 上传路径
     */
    private static String profile;
    /**
     * 验证码类型
     */
    private static String captchaDisturbType;
    /**
     * 应用程序名称
     */
    private static String name;
    /**
     * 版本
     */
    private static String version;
    /**
     * 版权年份
     */
    private static String copyrightYear;


    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        ApplicationConfig.profile = profile;
    }

    public static String getResource() {
        return getProfile() + "/resource";
    }

    public static String getCaptchaDisturbType() {
        return captchaDisturbType;
    }

    public void setCaptchaDisturbType(String captchaDisturbType) {
        ApplicationConfig.captchaDisturbType = captchaDisturbType;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getUploadRootPath() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getUploadRootPath() + "/common";
    }

    /**
     * 获取上传根路径
     */
    public static String getUploadRootPath() {
        return getProfile() + "/upload";
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        ApplicationConfig.name = name;
    }

    public static String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        ApplicationConfig.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        ApplicationConfig.copyrightYear = copyrightYear;
    }


}
