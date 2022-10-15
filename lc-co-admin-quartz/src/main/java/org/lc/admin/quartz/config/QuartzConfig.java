package org.lc.admin.quartz.config;

import org.lc.admin.common.datasource.DynamicMultipleDataSource;
import org.lc.admin.quartz.factory.JobFactory;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Description: quartz定时任务框架配置
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 13:45
 */
@Configuration
public class QuartzConfig {

    /**
     * 配置文件位置
     */
    public static final String PROPERTIES_LOCATION = "/quartz.properties";
    /**
     * 定时任务数据源名称
     */
    public static final String DATASOURCE_NAME = "quartz";


    @Resource
    private DynamicMultipleDataSource dataSource;

    @Resource
    private JobFactory jobFactory;

    /**
     * Description: quartz数据源
     *
     * @return {@link DataSource } 数据源
     * @throws SQLException sqlexception异常
     * @date 2022-10-11 13:46
     */
    @Bean(name = "quartzDataSource")
    public DataSource quartzDataSource() throws SQLException {
        DataSource quartzDataSource;
        Map<Object, DataSource> resolvedDataSources = dataSource.getResolvedDataSources();
        if (resolvedDataSources.containsKey(DATASOURCE_NAME)) {
            quartzDataSource = resolvedDataSources.get(DATASOURCE_NAME);
        } else {
            throw new SQLException("quartz datasource not found");
        }
        return quartzDataSource;
    }

    /**
     * Description: 调度器工厂bean
     *
     * @param dataSource 数据源
     * @param properties 属性配置
     * @return {@link SchedulerFactoryBean } 调度器工厂bean
     * @date 2022-10-11 13:46
     */
    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("quartzDataSource") DataSource dataSource,
                                                     @Qualifier("quartzProperties") Properties properties) {

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        // 配置数据源，job工厂，配置属性
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setQuartzProperties(properties);

        // 自动启动
        schedulerFactoryBean.setAutoStartup(true);
        // 覆盖已存在的job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        // 延迟3s启动quartz
        schedulerFactoryBean.setStartupDelay(3);
        // 关闭时等待job完成
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        return schedulerFactoryBean;

    }

    /**
     * Description: quartz配置属性
     *
     * @return {@link Properties } quartz配置属性
     * @throws IOException io异常
     * @date 2022-10-11 13:47
     */
    @Bean(name = "quartzProperties")
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // 设置配置路径 classpath
        propertiesFactoryBean.setLocation(new ClassPathResource(PROPERTIES_LOCATION));
        // 属性设置之后创建properties
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * Description: 调度器
     *
     * @param schedulerFactoryBean 调度器工厂bean
     * @return {@link Scheduler } 调度器
     * @date 2022-10-11 13:48
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler(@Qualifier("schedulerFactoryBean") SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }

}
