package org.lc.admin.common.datasource.provider;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Description: 多数据源提供者接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-06 10:46
 */
public interface MultipleDataSourceProvider {

    /**
     * 默认数据源
     */
    String DEFAULT_DATASOURCE = "master";

    /**
     * Description: 加载数据源
     *
     * @return {@link Map }<{@link String }, {@link DataSource }> 多数据源map
     * @date 2022-10-06 10:47
     */
    Map<String, DataSource> loadDataSource();


}
