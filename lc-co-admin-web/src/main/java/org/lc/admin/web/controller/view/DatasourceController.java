package org.lc.admin.web.controller.view;

import org.lc.admin.common.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description: 数据源监控controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:21
 */
@Controller
@RequestMapping("/monitor/druid")
public class DatasourceController extends BaseController {

    /**
     * Description: 首页index视图接口
     *
     * @return {@link String }  重定向-->/druid
     * @date 2022-10-02 15:22
     */
    @GetMapping
    public String index() {
        // 重定向/druid
        return redirect("/druid");
    }


}
