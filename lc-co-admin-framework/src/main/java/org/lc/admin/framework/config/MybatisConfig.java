package org.lc.admin.framework.config;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Description: mybatis配置
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-07 10:24
 */
@ConfigurationProperties(prefix = "mybatis")
@Configuration
@MapperScan("org.lc.admin.**.mapper")
@Data
public class MybatisConfig {

    /**
     * 默认资源匹配表达式
     */
    public static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    public static final Logger log = LoggerFactory.getLogger(MybatisConfig.class);

    /**
     * 类型别名包
     */
    private String typeAliasesPackage;
    /**
     * mapper xml 路径
     */
    private String mapperLocations;
    /**
     * mybatis config 路径
     */
    private String configLocation;

    /**
     * Description: sql会话工厂
     *
     * @param dataSource 数据源
     * @return {@link SqlSessionFactory } sql会话工厂
     * @throws Exception 异常
     * @date 2022-10-07 10:23
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicMultipleDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage(this.typeAliasesPackage));
        sessionFactory.setMapperLocations(mapperLocations(this.mapperLocations));
        sessionFactory.setConfigLocation(configLocation(this.configLocation));
        return sessionFactory.getObject();
    }

    /**
     * Description: 配置文件路径转换为资源实例
     *
     * @param configLocation 配置文件路径
     * @return {@link Resource } 配置文件资源实例
     * @date 2022-10-07 09:10
     */
    private Resource configLocation(String configLocation) {
        return new DefaultResourceLoader().getResource(configLocation);
    }

    /**
     * Description: mapper xml 映射器文件路径转换为资源实例数组
     *
     * @param mapperLocations 映射器文件路径
     * @return {@link Resource[] } 资源实例数组
     * @date 2022-10-07 09:23
     */
    private Resource[] mapperLocations(String mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        // 多个路径按照，分割后转换
        return StrUtil.split(mapperLocations, ",")
                .stream()
                // 去重
                .distinct()
                .map((mapperLocation) -> {
                    Resource[] mappers;
                    try {
                        mappers = resourceResolver.getResources(mapperLocation);
                    } catch (IOException e) {
                        mappers = null;
                    }
                    return mappers;
                })
                // 保留mappers不为null
                .filter(ArrayUtil::isNotEmpty)
                // 扁平化mappers
                .flatMap((resources) -> Arrays.stream(resources).filter(Resource::isReadable))
                // 转换为数组
                .toArray(Resource[]::new);
    }

    /**
     * Description: 支持按,分隔多个类型别名包转换
     *
     * @param typeAliasesPackages 按,分隔多个类型别名包
     * @return {@link String } 类型别名包
     * @date 2022-10-07 10:15
     */
    private String typeAliasesPackage(String typeAliasesPackages) {
        // 获取资源解析器
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 获取元数据读取器工厂
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        // 按,分割类型别名字符串
        return StrUtil.split(typeAliasesPackages, ",")
                .stream()
                // 去重
                .distinct()
                // 类型别名包转换为resource数组
                .map((typeAliasesPackage) -> {
                    String aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(typeAliasesPackage.trim()) + "/" + DEFAULT_RESOURCE_PATTERN;
                    Resource[] resources;
                    try {
                        resources = resolver.getResources(aliasesPackage);
                    } catch (IOException e) {
                        resources = null;
                    }
                    return resources;
                })
                // 过滤掉资源数组为空
                .filter(ArrayUtil::isNotEmpty)
                // 扁平化资源数组保留可读资源以及去重
                .flatMap((resources) -> Arrays.stream(resources).filter(Resource::isReadable))
                // 将资源转换为类型别名包字符串
                .map((resource) -> {
                    String typeAliasesPackage;
                    try {
                        // 获取资源元数据读取器
                        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        // 获取资源包名称
                        typeAliasesPackage = Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName();
                    } catch (IOException | ClassNotFoundException e) {
                        typeAliasesPackage = StrUtil.EMPTY;
                    }
                    return typeAliasesPackage;
                })
                // 去重
                .distinct()
                // 保留非空白类型别名包字符串
                .filter(StrUtil::isNotBlank)
                // 根据,拼接类型别名
                .collect(Collectors.joining(","));
    }


}
