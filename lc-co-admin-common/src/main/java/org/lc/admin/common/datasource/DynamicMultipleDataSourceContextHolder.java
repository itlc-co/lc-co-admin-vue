package org.lc.admin.common.datasource;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 动态多数据源上下文holder 即本地线程存储线程中的数据源名称
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-06 10:39
 */
public class DynamicMultipleDataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DynamicMultipleDataSourceContextHolder.class);
    /**
     * 当前数据源名称本地线程
     */
    private static final ThreadLocal<String> CURRENT_DATASOURCE_NAME = new ThreadLocal<>();
    /**
     * 数据源名称
     */
    private static String DATA_SOURCE_NAME = StrUtil.EMPTY;

    /**
     * Description: 获取当前数据源名称
     *
     * @return {@link String } 数据源名称
     * @date 2022-10-06 10:38
     */
    public static String getDataSourceName() {
        return CURRENT_DATASOURCE_NAME.get();
    }

    /**
     * Description: 设置当前线程数据源名称
     *
     * @param dataSourceName 数据源名称
     * @date 2022-10-06 10:37
     */
    public static void setDataSourceName(String dataSourceName) {
        DATA_SOURCE_NAME = dataSourceName;
        CURRENT_DATASOURCE_NAME.set(dataSourceName);
    }

    /**
     * Description: 清理当前线程数据源名称
     *
     * @date 2022-10-06 10:38
     */
    public static String clearDataSourceName() {
        CURRENT_DATASOURCE_NAME.remove();
        return DATA_SOURCE_NAME;
    }

}
