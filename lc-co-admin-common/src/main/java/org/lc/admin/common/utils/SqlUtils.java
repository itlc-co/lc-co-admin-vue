package org.lc.admin.common.utils;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.exec.UtilsException;

import java.util.List;

/**
 * Description: sql操作工具类
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:22
 */
public class SqlUtils {
    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * Description:检查字符，防止注入绕过
     *
     * @param value sql
     * @return {@link String } sql
     * @date 2022-09-24 10:41
     */
    public static String escapeOrderBySql(String value) {
        if (StrUtil.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new UtilsException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    /**
     * Description: 验证 order by 语法是否符合规范
     *
     * @param value order by sql
     * @return boolean true 符合 false 不符合
     * @date 2022-09-24 10:41
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

    /**
     * Description:  SQL关键字检查
     *
     * @param value sql
     * @date 2022-09-24 10:41
     */
    public static void filterKeyword(String value) {
        if (StrUtil.isEmpty(value)) {
            return;
        }
        List<String> sqlKeywords = StrUtil.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords) {
            if (StrUtil.indexOfIgnoreCase(value, sqlKeyword) > -1) {
                throw new UtilsException("参数存在SQL注入风险");
            }
        }
    }
}

