package org.lc.admin.framework.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.common.pool.FileConstantsPool;
import org.lc.admin.common.utils.file.FileUtils;
import org.lc.admin.common.utils.file.MimeTypeUtils;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.common.utils.server.ServerUtils;
import org.lc.admin.framework.config.ApplicationConfig;
import org.lc.admin.framework.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 文件service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 09:43
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * Description: 获取路径文件名 （url）
     *
     * @param basePath 基本路径
     * @param filePath 文件名称路径
     * @return {@link String } 路径文件名 （url）
     * @date 2022-09-22 10:35
     */
    private String getPathFileName(String basePath, String filePath) {
        int dirLastIndex = ApplicationConfig.getProfile().length() + 1;
        String curDir = StrUtil.subSuf(basePath, dirLastIndex);
        return FileConstantsPool.RESOURCE_PREFIX + "/" + curDir + "/" + filePath;
    }


    /**
     * Description: 上传文件
     *
     * @param file             文件
     * @param basePath         文件绝对路径上一级路径不包括文件名称
     * @param allowedExtension 支持允许上传的后缀扩展名
     * @return {@link String } 文件名称
     * @date 2022-09-21 22:48
     */
    @Override
    public String upload(MultipartFile file, String basePath, String[] allowedExtension) {

        // 断言判断文件有效性（文件名称长度不超过默认长度，文件大小不超过默认大小）
        assertFileValidity(file);

        // 断言判断文件扩展名后缀是否支持
        assertFileExtension(file, allowedExtension);

        // 提取编码文件名
        String filePath = extractFilename(file);

        // 获取完整绝对路径 父目录不存在则创建
        String absolutePath = getAndMkdirAbsoluteFile(basePath, filePath)
                .getAbsolutePath();

        try {
            // 从网络io传输到本地绝对路径中
            file.transferTo(Paths.get(absolutePath));
        } catch (Exception e) {
            throw new FileException(StatusMsg.FILE_ERROR);
        }

        // 获取路径文件名返回
        return getPathFileName(basePath, filePath);

    }

    /**
     * Description: 断言校验文件有效性
     *
     * @param file 文件
     * @date 2022-10-05 10:32
     */
    private void assertFileValidity(MultipartFile file) {
        // 检查文件名称长度
        if (!checkFileNameLen(file)) {
            throw new FileException(StatusMsg.FILE_UPLOAD_NAME_LEN_TOO_LONG.getCode(), StrUtil.format(StatusMsg.FILE_UPLOAD_NAME_LEN_TOO_LONG.getMsg() + "{}", FileConstantsPool.DEFAULT_UPLOAD_FILE_NAME_LENGTH));
        }

        // 文件大小校验
        if (!checkFileSize(file)) {
            throw new FileException(StatusMsg.FILE_UPLOAD_SIZE_TOO_BIG.getCode(), StrUtil.format(StatusMsg.FILE_UPLOAD_SIZE_TOO_BIG.getMsg() + "{}M", FileConstantsPool.DEFAULT_UPLOAD_FILE_SIZE / 1024 / 1024));
        }
    }

    /**
     * Description: 获取绝对路径文件实例
     *
     * @param path     路径
     * @param filePath 文件名称路径
     * @return {@link File } 文件实例 不存在父级目录会自动创建
     * @date 2022-09-22 09:32
     */
    private File getAndMkdirAbsoluteFile(String path, String filePath) {
        // 绝对路径文件实例
        File desc = new File(path + File.separator + filePath);
        if (!desc.exists() && !desc.getParentFile().exists()) {
            // 父级目录不存在情况下则创建多级目录
            desc.getParentFile().mkdirs();
        }
        return desc;
    }

    /**
     * Description: 提取路径文件名
     *
     * @param file 文件
     * @return {@link String } 路径文件名
     * @date 2022-09-22 09:33
     */
    private String extractFilename(MultipartFile file) {
        return StrUtil.format("{}/{}", SecurityUtils.getUserName(), prodFileName(file));
    }

    /**
     * Description: 生成文件名
     *
     * @param file 文件
     * @return {@link String } 文件名
     * @date 2022-09-22 09:33
     */
    private String prodFileName(MultipartFile file) {
        return StrUtil.format("{}_{}.{}", FileNameUtil.mainName(file.getOriginalFilename()), IdUtil.nanoId(), getExtension(file));
    }

    /**
     * Description: 断言文件后缀扩展名是否支持
     *
     * @param file             文件
     * @param allowedExtension 文件后缀扩展名
     * @date 2022-09-22 09:33
     */
    private void assertFileExtension(MultipartFile file, String[] allowedExtension) {
        // 判断是否支持当前文件后缀扩展名
        if (!StrUtil.equalsAnyIgnoreCase(StrUtil.blankToDefault(FileNameUtil.extName(file.getOriginalFilename()), MimeTypeUtils.getType(file.getContentType())), allowedExtension)) {
            // 不支持的异常响应
            Integer code = StatusMsg.FILE_UPLOAD_NAME_SUFFIX_NOT_ALLOW.getCode();
            String msg = StatusMsg.FILE_UPLOAD_NAME_SUFFIX_NOT_ALLOW.getMsg();
            throw new FileException(code, msg + ",支持的文件后缀扩展名:{}", StrUtil.join(",", Arrays.asList(allowedExtension)));
        }
    }

    /**
     * Description: 检查文件大小
     *
     * @param file 文件
     * @return boolean true 未超 false 已超
     * @date 2022-09-22 09:34
     */
    private boolean checkFileSize(MultipartFile file) {
        return file.getSize() <= FileConstantsPool.DEFAULT_UPLOAD_FILE_SIZE;
    }

    /**
     * Description: 检查文件名len
     *
     * @param file 文件
     * @return boolean true 未超 false 已超
     * @date 2022-09-22 09:34
     */
    private boolean checkFileNameLen(MultipartFile file) {
        return StrUtil.length(file.getOriginalFilename()) <= FileConstantsPool.DEFAULT_UPLOAD_FILE_NAME_LENGTH;
    }

    /**
     * Description: 获取文件后缀扩展名
     *
     * @param file 文件
     * @return {@link String }  文件后缀扩展名
     * @date 2022-09-22 09:34
     */
    private String getExtension(MultipartFile file) {
        String extension = FileNameUtil.extName(file.getName());
        if (StrUtil.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }


    /**
     * Description: 上传文件
     *
     * @param file     文件
     * @param basePath 文件绝对路径上一级路径不包括文件名称
     * @return {@link String } 文件名称
     * @date 2022-10-04 22:41
     */
    @Override
    public String upload(MultipartFile file, String basePath) {
        return upload(file, basePath, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }

    /**
     * Description: 上传文件列表
     *
     * @param files    文件列表
     * @param basePath 文件绝对路径上一级路径不包括文件名称
     * @return {@link List }<{@link Map }<{@link Object }, {@link Object }>> 结果集mapList
     * @date 2022-10-05 11:15
     */
    @Override
    public List<Map<String, Object>> uploads(List<MultipartFile> files, String basePath) {
        return files.stream().map((file) -> {
            // 返回文件上传结果map
            Map<String, Object> map = MapUtil.newHashMap(4);
            String fileName = this.upload(file, basePath);
            // 绝对路径文件名称
            map.put("filename", fileName);
            // 完整url
            map.put("url", ServerUtils.url() + fileName);
            // 文件名称
            map.put("newFileName", FileUtils.getName(fileName));
            // originalFilename
            map.put("originalFilename", file.getOriginalFilename());
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * Description: 下载文件
     *
     * @param response 响应
     * @param filePath 文件绝对路径
     * @throws Exception 异常
     * @date 2022-10-05 11:38
     */
    @Override
    public void download(HttpServletResponse response, String filePath) throws Exception {
        // 原文件名称
        String fileName = StrUtil.subAfter(filePath, ApplicationConfig.getDownloadPath(), false);

        // 下载返回文件名称
        String realFileName = System.currentTimeMillis() + "_" + fileName.substring(fileName.indexOf("_") + 1);

        // 封装响应头
        setResponseHeader(response, realFileName);

        // 封装文件响应体
        setResponseBody(response, filePath);
    }

    /**
     * Description: 设置响应体
     *
     * @param response 响应
     * @param filePath 文件路径
     * @date 2022-10-05 00:16
     */
    private void setResponseBody(HttpServletResponse response, String filePath) throws Exception {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            // 写入文件至响应输出流中
            FileUtil.writeToStream(filePath, out);
        } finally {
            if (ObjectUtil.isNotNull(out)) {
                // 关闭响应输出流
                IoUtil.close(out);
            }
        }
    }

    /**
     * Description: 设置响应头
     *
     * @param response     响应
     * @param realFileName 文件名称
     * @date 2022-10-05 00:16
     */
    private void setResponseHeader(HttpServletResponse response, String realFileName) {
        String encodeFileName = URLEncodeUtil.encode(realFileName);
        String str = "attachment; filename=" +
                encodeFileName +
                ";" +
                "filename*=" +
                "utf-8''" +
                encodeFileName;

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", str);
        response.setHeader("download-filename", encodeFileName);
    }


    /**
     * Description: 下载资源文件
     *
     * @param response 响应
     * @param filePath 文件绝对路径
     * @throws Exception 异常
     * @date 2022-10-05 11:38
     */
    @Override
    public void downloadResource(HttpServletResponse response, String filePath) throws Exception {
        // 下载文件名称
        String fileName = StrUtil.subAfter(filePath, "/", true);

        // 封装响应头
        setResponseHeader(response, fileName);

        // 封装文件响应体
        setResponseBody(response, filePath);
    }


    /**
     * Description: 检查是否支持文件下载
     *
     * @param fileName 文件名称
     * @return boolean true 支持 false 不支持
     * @date 2022-10-05 11:32
     */
    @Override
    public boolean checkAllowDownload(String fileName) {
        // 禁止使用相对路径以及只能是系统允许的文件类型
        return !StrUtil.contains(fileName, "..") && FileNameUtil.isType(fileName, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }


    /**
     * Description: 检查文件是否存在
     *
     * @param filePath 文件绝对路径
     * @return boolean true 存在 false 不存在
     * @date 2022-10-05 16:23
     */
    @Override
    public boolean checkFileExist(String filePath) {
        return FileUtil.exist(filePath);
    }
}
