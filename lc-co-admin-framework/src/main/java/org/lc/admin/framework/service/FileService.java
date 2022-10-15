package org.lc.admin.framework.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Description: 文件service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-05 10:33
 */
public interface FileService {

    /**
     * Description: 上传文件
     *
     * @param file             文件
     * @param basePath         文件绝对路径上一级路径不包括文件名称
     * @param allowedExtension 支持允许上传的后缀扩展名
     * @return {@link String } 文件名称
     * @date 2022-10-05 11:34
     */
    String upload(MultipartFile file, String basePath, String[] allowedExtension);

    /**
     * Description: 上传文件
     *
     * @param file     文件
     * @param basePath 文件绝对路径上一级路径不包括文件名称
     * @return {@link String } 文件名称
     * @date 2022-10-05 11:33
     */
    String upload(MultipartFile file, String basePath);

    /**
     * Description: 上传文件列表
     *
     * @param files    文件列表
     * @param basePath 文件绝对路径上一级路径不包括文件名称
     * @return {@link List }<{@link Map }<{@link Object }, {@link Object }>> 结果集mapList
     * @date 2022-10-05 11:16
     */
    List<Map<String, Object>> uploads(List<MultipartFile> files, String basePath);

    /**
     * Description: 下载文件
     *
     * @param response 响应
     * @param filePath 文件绝对路径
     * @throws Exception 异常
     * @date 2022-10-05 11:31
     */
    void download(HttpServletResponse response, String filePath) throws Exception;

    /**
     * Description: 下载资源文件
     *
     * @param response 响应
     * @param filePath 文件绝对路径
     * @throws Exception 异常
     * @date 2022-10-05 11:31
     */
    void downloadResource(HttpServletResponse response, String filePath) throws Exception;

    /**
     * Description: 检查是否支持文件下载
     *
     * @param fileName 文件名称
     * @return boolean true 支持 false 不支持
     * @date 2022-10-05 11:31
     */
    boolean checkAllowDownload(String fileName);

    /**
     * Description: 检查文件是否存在
     *
     * @param filePath 文件绝对路径
     * @return boolean true 存在 false 不存在
     * @date 2022-10-05 16:22
     */
    boolean checkFileExist(String filePath);
}
