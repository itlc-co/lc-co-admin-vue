package org.lc.admin.common.utils.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;

/**
 * Description: 文件工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-04 22:42
 */
public class FileUtils {

    /**
     * Description: 根据绝对路径获取文件名称
     *
     * @param absPath 绝对路径
     * @return {@link String } 文件名称
     * @date 2022-10-04 22:43
     */
    public static String getName(String absPath) {
        return FileNameUtil.getName(absPath);
    }

    /**
     * Description: 删除文件
     *
     * @param filePath 文件路径
     * @return boolean true 删除成功 false 删除失败
     * @date 2022-10-04 23:52
     */
    public static boolean deleteFile(String filePath) {
        return FileUtil.del(filePath);
    }


}
