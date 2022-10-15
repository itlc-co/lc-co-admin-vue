package org.lc.admin.common.utils.file;

import org.lc.admin.common.utils.AssertUtils;

/**
 * Description: 媒体类型工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 22:36
 */
public class MimeTypeUtils {
    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";

    public static final String[] IMAGE_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png"};

    public static final String[] FLASH_EXTENSION = {"swf", "flv"};

    public static final String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb"};

    public static final String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb"};

    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb",
            // pdf
            "pdf"};

    /**
     * Description: 根据媒体类型前缀获取后缀扩展名
     *
     * @param prefix 媒体类型前缀
     * @return {@link String } 后缀扩展名
     * @date 2022-09-24 10:30
     */
    public static String getExtension(String prefix) {
        switch (prefix) {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            default:
                return "";
        }
    }

    /**
     * Description: 根据contentType获取媒体类型后缀扩展包括.
     *
     * @param contentType 内容类型
     * @return {@link String } 媒体类型后缀扩展包括.
     * @date 2022-10-05 10:20
     */
    public static String getType(String contentType) {
        // 断言内容类型不能为空白字符串
        AssertUtils.isNotBlank(contentType);
        switch (contentType) {
            case IMAGE_PNG:
                return ".png";
            case IMAGE_JPG:
                return ".jpg";
            case IMAGE_JPEG:
                return ".jpeg";
            case IMAGE_BMP:
                return ".bmp";
            case IMAGE_GIF:
                return ".gif";
            default:
                return "";
        }
    }
}
