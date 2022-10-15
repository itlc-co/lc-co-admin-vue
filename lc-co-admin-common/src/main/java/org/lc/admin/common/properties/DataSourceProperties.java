package org.lc.admin.common.properties;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Map;

/**
 * Description: 数据源配置属性
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-06 10:07
 */
@ConfigurationProperties(prefix = "spring.datasource")
@Configuration
@Data
public class DataSourceProperties {

    /**
     * 数据库db
     */
    private Map<String, Map<String, String>> db;

    /**
     * 初始连接数
     */
    private int initialSize;

    /**
     * 最小连接池数量
     */
    private int minIdle;

    /**
     * 最大活跃连接池数量
     */
    private int maxActive;

    /**
     * 最大等待时间
     */
    private int maxWait;

    /**
     * 检测间隔时间
     */
    private int timeBetweenEvictionRunsMillis;

    /**
     * 最小的生存时间
     */
    private int minEvictableIdleTimeMillis;

    /**
     * 最大的生存时间
     */
    private int maxEvictableIdleTimeMillis;

    /**
     * 检测连接是否有效
     */
    private String validationQuery;

    /**
     * 失效连接检测
     */
    private boolean testWhileIdle;

    /**
     * 申请连接时判断否是可用
     */
    private boolean testOnBorrow;

    /**
     * 连接返回检测
     */
    private boolean testOnReturn;

    private String filters;

    /**
     * Description: 数据源配置
     *
     * @param dataSource 数据源
     * @return {@link DruidDataSource } 配置后的druid数据源
     * @date 2022-10-06 10:06
     */
    public DruidDataSource dataSource(DruidDataSource dataSource) throws SQLException {
        // 配置初始连接数
        dataSource.setInitialSize(initialSize);
        // 配置最小连接池数量
        dataSource.setMinIdle(minIdle);
        // 配置最大连接池数量
        dataSource.setMaxActive(maxActive);
        // 配置连接等待超时的时间
        dataSource.setMaxWait(maxWait);
        // 配置检测间隔时间，检测需要关闭的空闲连接，单位毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 配置一个连接在连接池中最小的生存时间，单位毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        // 配置一个连接在连接池中最大的生存时间，单位毫秒
        dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
        // 配置检测连接是否有效
        dataSource.setValidationQuery(validationQuery);
        // 如果为true（默认为false），当应用向连接池申请连接时，连接池会判断这条连接是否是可用的  降低性能
        dataSource.setTestOnBorrow(testOnBorrow);
        // 配置连接返回检测 降低性能
        dataSource.setTestOnReturn(testOnReturn);
        // 配置失效连接检测
        dataSource.setTestWhileIdle(testWhileIdle);
        // 添加过滤器
        dataSource.addFilters(filters);
        return dataSource;
    }


}
