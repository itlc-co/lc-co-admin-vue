package org.lc.admin.web.controller.view;

import org.lc.admin.common.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description: api接口文档 swagger controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 16:33
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController extends BaseController {


    /**
     * Description: api文档视图
     *
     * @return {@link String } 重定向-->/doc.html
     * @date 2022-09-23 13:53
     */
    @GetMapping()
    public String index() {
        //  重定向 /doc.html
        return redirect("/doc.html");
    }


}
