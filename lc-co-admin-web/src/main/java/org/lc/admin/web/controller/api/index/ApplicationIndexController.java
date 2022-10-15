package org.lc.admin.web.controller.api.index;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.framework.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description: 应用 index controller 控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 10:54
 */
@RestController
public class ApplicationIndexController extends BaseController {

    /**
     * Description: 首页接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-22 10:59
     */
    @RequestMapping("/")
    public ResultResponse index() {
        return success(StrUtil.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", ApplicationConfig.getName(), ApplicationConfig.getVersion()));
    }

}
