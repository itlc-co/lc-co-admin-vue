package org.lc.admin.framework.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Description: 公共通用service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-05 10:34
 */
public interface CommonService {

    /**
     * Description: 通用下载文件方法
     *
     * @param request  请求
     * @param response 响应
     * @param fileName 文件名称
     * @param isDelete 下载后是否删除
     * @throws Exception 异常
     * @date 2022-10-05 10:45
     */
    void download(HttpServletRequest request, HttpServletResponse response, String fileName, Boolean isDelete) throws Exception;

    /**
     * Description: 通用上传文件并返回结果集map方法
     *
     * @param file MultipartFile网络io文件
     * @return {@link Map }<{@link String }, {@link Object }> 结果集map
     * @date 2022-10-05 10:50
     */
    Map<String, Object> upload(MultipartFile file);

    /**
     * Description: 通用上传文件列表并返回结果集listMap方法
     *
     * @param files 文件列表
     * @return {@link Map }<{@link String }, {@link List<Object> }>  结果集listMap
     * @date 2022-10-05 10:58
     */
    Map<String, List<Object>> uploads(List<MultipartFile> files);

    /**
     * Description: 通用下载资源文件方法
     *
     * @param request  请求
     * @param response 响应
     * @param resource 资源文件名称
     * @throws Exception 异常
     * @date 2022-10-05 11:28
     */
    void downloadResource(HttpServletRequest request, HttpServletResponse response, String resource) throws Exception;
}
