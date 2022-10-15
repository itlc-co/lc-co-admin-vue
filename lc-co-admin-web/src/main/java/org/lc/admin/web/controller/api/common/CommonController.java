package org.lc.admin.web.controller.api.common;

import cn.hutool.core.map.MapUtil;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.framework.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Description: 公共通用common控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-04 22:26
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Resource
    private CommonService commonService;

    /**
     * Description: 通用下载文件接口
     *
     * @param request  请求
     * @param response 响应
     * @param fileName 文件名称
     * @param isDelete 下载后是否删除
     * @date 2022-10-04 22:29
     */
    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileName") String fileName, @RequestParam("isDelete") Boolean isDelete) {
        try {
            // 通过下载文件
            this.commonService.download(request, response, fileName, isDelete);
        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }
    }


    /**
     * Description: 通用上传文件接口
     *
     * @param file 文件
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-04 23:02
     */
    @PostMapping("/upload")
    public ResultResponse upload(MultipartFile file) {
        // 默认异常响应
        ResultResponse response = error();

        // 上传并返回结果集map
        Map<String, Object> map = this.commonService.upload(file);

        if (MapUtil.isNotEmpty(map)) {
            // 结果集map不为空串则封装结果集响应
            response = success().put(map);
        }

        return response;
    }

    /**
     * Description: 通用上传文件列表接口
     *
     * @param files 文件列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-10-04 23:02
     */
    @PostMapping("/uploads")
    public ResultResponse uploads(List<MultipartFile> files) {
        // 默认异常响应
        ResultResponse response = error();

        // 上传文件列表
        Map<String, List<Object>> map = this.commonService.uploads(files);

        if (MapUtil.isNotEmpty(map)) {
            // 封装响应结果集 url列表等信息
            response = success().put(map);
        }

        return response;
    }

    /**
     * Description: 通用下载资源文件接口
     *
     * @param request  请求
     * @param response 响应
     * @param resource 资源文件名
     * @date 2022-10-05 00:28
     */
    @GetMapping("/download/resource")
    public void downloadResource(HttpServletRequest request, HttpServletResponse response, String resource) {
        try {
            // 下载资源文件
            this.commonService.downloadResource(request, response, resource);
        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }
    }


}
