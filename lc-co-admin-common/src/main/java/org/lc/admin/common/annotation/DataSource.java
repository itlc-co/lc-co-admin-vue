package org.lc.admin.common.annotation;

import org.lc.admin.common.entities.enums.DataSourceName;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Description: 数据源注解
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-06 11:32
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    /**
     * Description: 数据源名称
     *
     * @return {@link DataSourceName } 数据源名称
     * @date 2022-10-06 11:28
     */
    @AliasFor("name")
    DataSourceName value() default DataSourceName.MASTER;

    /**
     * Description: 数据源名称
     *
     * @return {@link DataSourceName } 数据源名称
     * @date 2022-10-06 11:28
     */
    DataSourceName name() default DataSourceName.MASTER;
}
