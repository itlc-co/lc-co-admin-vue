package org.lc.admin.common.datasource;

import org.lc.admin.common.datasource.provider.MultipleDataSourceProvider;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 动态多数据源
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-06 11:16
 */
public class DynamicMultipleDataSource extends AbstractRoutingDataSource {

    /**
     * 实际数据源提供者
     */
    private MultipleDataSourceProvider dataSourceProvider;

    /**
     * Description: 动态多数据源构造
     *
     * @param provider 多数据源提供者
     * @date 2022-10-06 11:22
     */
    public DynamicMultipleDataSource(MultipleDataSourceProvider provider) {
        // 多数据源提供者
        this.dataSourceProvider = provider;
        // 配置数据源map
        Map<String, DataSource> dataSourceMap = provider.loadDataSource();
        super.setTargetDataSources(new HashMap<>(dataSourceMap));
        // 配置默认数据源
        super.setDefaultTargetDataSource(dataSourceMap.getOrDefault(MultipleDataSourceProvider.DEFAULT_DATASOURCE, null));
        super.afterPropertiesSet();
    }


    /**
     * Description: 确定当前查找的数据源键
     *
     * @return {@link Object } 数据源键
     * @date 2022-10-06 10:39
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicMultipleDataSourceContextHolder.getDataSourceName();
    }

}
