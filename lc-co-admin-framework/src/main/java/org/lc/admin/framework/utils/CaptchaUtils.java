package org.lc.admin.framework.utils;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import org.lc.admin.common.pool.AuthConstantsPool;
import org.lc.admin.framework.config.ApplicationConfig;

/**
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07  20:52
 */
public class CaptchaUtils {

    /**
     * 验证码图片宽度
     */
    public static final Integer CAPTCHA_WIDTH = AuthConstantsPool.CAPTCHA_WIDTH;

    /**
     * 验证码图片高度
     */
    public static final Integer CAPTCHA_HEIGHT = AuthConstantsPool.CAPTCHA_HEIGHT;


    /**
     * Description: 构建验证码实例
     *
     * @return {@link AbstractCaptcha } 抽象验证码实例
     * @date 2022-10-07 20:53
     */
    public static AbstractCaptcha buildCaptcha() {
        AbstractCaptcha captcha;
        switch (ApplicationConfig.getCaptchaDisturbType()) {
            case "circle":
                captcha = CaptchaUtil.createCircleCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
                break;
            case "gif":
                captcha = CaptchaUtil.createGifCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
                break;
            case "shear":
                captcha = CaptchaUtil.createShearCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
                break;
            default:
                captcha = CaptchaUtil.createLineCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
                break;
        }
        return captcha;
    }

}
