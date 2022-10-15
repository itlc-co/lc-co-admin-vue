package org.lc.admin.framework.config;

import org.lc.admin.common.datasource.DynamicMultipleDataSource;
import org.lc.admin.common.datasource.provider.impl.DynamicMultipleDataSourceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;

/**
 * Description: 动态数据源配置
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-06 11:53
 */
@Configuration
public class DynamicDataSourceConfig {

    @Resource
    private DynamicMultipleDataSourceProvider dataSourceProvider;

    /**
     * Description: 动态多数据源
     *
     * @return {@link DynamicMultipleDataSource } 动态多数据源
     * @date 2022-10-06 11:53
     */
    @Bean
    public DynamicMultipleDataSource dynamicMultipleDataSource() {
        return new DynamicMultipleDataSource(dataSourceProvider);
    }

    /**
     * Description: 动态多数据源事务管理器
     *
     * @return {@link PlatformTransactionManager } 事务管理器
     * @date 2022-10-06 21:37
     */
    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicMultipleDataSource());
    }

}
