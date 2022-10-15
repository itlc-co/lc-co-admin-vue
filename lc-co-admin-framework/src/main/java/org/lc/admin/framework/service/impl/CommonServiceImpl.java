package org.lc.admin.framework.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.common.pool.FileConstantsPool;
import org.lc.admin.common.utils.file.FileUtils;
import org.lc.admin.common.utils.server.ServerUtils;
import org.lc.admin.framework.config.ApplicationConfig;
import org.lc.admin.framework.service.CommonService;
import org.lc.admin.framework.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Description:  公共通用service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-05 10:46
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private FileService fileService;

    /**
     * Description: 通用下载文件方法
     *
     * @param request  请求
     * @param response 响应
     * @param fileName 文件名称
     * @param isDelete 下载后是否删除
     * @throws Exception 异常
     * @date 2022-10-05 10:57
     */
    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName, Boolean isDelete) throws Exception {
        // 文件绝对路径
        String filePath = ApplicationConfig.getDownloadPath() + fileName;

        // 检查文件名是否允许下载
        if (!this.fileService.checkAllowDownload(fileName)) {
            throw new FileException(StatusMsg.FILE_NAME_ILLEGAL);
        }

        // 检查文件是否存在
        if (!this.fileService.checkFileExist(filePath)) {
            throw new FileException(StatusMsg.FILE_NOT_FOUND);
        }

        // 下载文件并封装响应
        this.fileService.download(response, filePath);

        if (isDelete) {
            // 下载后删除原文件
            FileUtils.deleteFile(filePath);
        }
    }

    /**
     * Description: 通用上传文件并返回结果集map方法
     *
     * @param file MultipartFile网络io文件
     * @return {@link Map }<{@link String }, {@link Object }> 结果集map
     * @date 2022-10-05 10:57
     */
    @Override
    public Map<String, Object> upload(MultipartFile file) {
        // 结果集map
        Map<String, Object> map = MapUtil.newHashMap();

        // 上传文件路径
        String filePath = ApplicationConfig.getUploadPath();

        // 上传文件并返回绝对路径文件名
        String fileName = this.fileService.upload(file, filePath);

        // 绝对路径文件名不为空白字符串封装结果集map
        if (StrUtil.isNotBlank(fileName)) {
            // 文件绝对路径
            map.put("fileName", fileName);
            // 文件完整url
            map.put("url", ServerUtils.url() + fileName);
            // 文件名称
            map.put("newFileName", FileUtils.getName(fileName));
            // original文件名称
            map.put("originalFilename", file.getOriginalFilename());
        }

        return map;
    }

    /**
     * Description: 通用上传文件列表并返回结果集listMap方法
     *
     * @param files 文件列表
     * @return {@link Map }<{@link String }, {@link List<Object> }>  结果集listMap
     * @date 2022-10-05 11:20
     */
    @Override
    public Map<String, List<Object>> uploads(List<MultipartFile> files) {

        // 上传文件路径
        String filePath = ApplicationConfig.getUploadPath();

        // 上传文件列表并返回结果集mapList后转换为listMap 相同key合并成列表
        return MapUtil.toListMap(this.fileService.uploads(files, filePath));
    }

    /**
     * Description: 通用下载资源文件方法
     *
     * @param request  请求
     * @param response 响应
     * @param resource 资源文件名称
     * @throws Exception 异常
     * @date 2022-10-05 11:29
     */
    @Override
    public void downloadResource(HttpServletRequest request, HttpServletResponse response, String resource) throws Exception {

        // 本地资源下载路径
        String downloadPath = ApplicationConfig.getResource() + StrUtil.subAfter(resource, FileConstantsPool.RESOURCE_PREFIX, false);

        // 检查文件名是否允许下载
        if (!this.fileService.checkAllowDownload(resource)) {
            throw new FileException(StatusMsg.FILE_NAME_ILLEGAL);
        }

        // 检查文件是否存在
        if (!this.fileService.checkFileExist(downloadPath)) {
            throw new FileException(StatusMsg.FILE_NOT_FOUND);
        }

        // 下载资源文件
        this.fileService.downloadResource(response, downloadPath);
    }

}
