package org.lc.admin.common.datasource.provider.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.lc.admin.common.datasource.provider.MultipleDataSourceProvider;
import org.lc.admin.common.properties.DataSourceProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: 动态多数据源提供者实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-06 10:46
 */
@Component
public class DynamicMultipleDataSourceProvider implements MultipleDataSourceProvider {

    @Resource
    private DataSourceProperties dataSourceProperties;


    /**
     * Description: 加载数据源
     *
     * @return {@link Map }<{@link String }, {@link DataSource }> 多个数据源map
     * @date 2022-10-06 11:13
     */
    @Override
    public Map<String, DataSource> loadDataSource() {
        return MapUtil.filter(
                dataSourceProperties.getDb()
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        (entry) -> {
                                            DruidDataSource dataSource;
                                            try {
                                                // 根据配置信息创建数据源
                                                // 设置数据源的配置
                                                dataSource = dataSourceProperties.dataSource((DruidDataSource) DruidDataSourceFactory.createDataSource(entry.getValue()));
                                            } catch (Exception e) {
                                                dataSource = null;
                                            }
                                            return dataSource;
                                        },
                                        // 重复key保留value1
                                        (value1, value2) -> value1)
                        ),
                (entry) -> ObjectUtil.isNotNull(entry.getValue())
        );
    }


}
