package org.lc.admin.common.annotation;

import java.lang.annotation.*;

/**
 * Description: 分页注解
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-10 18:31
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pagination {

    /**
     * 分页参数中页码标识
     */
    String pageNum() default "pageNum";

    /**
     * 分页参数中页面容量标识
     */
    String pageSize() default "pageSize";

    /**
     * 分页参数中排序列名标识
     */
    String orderByColumn() default "orderByColumn";

    /**
     * 分页参数中排序类型标识
     */
    String orderType() default "orderType";

    /**
     * 分页参数中参数合理化标识
     */
    String reasonable() default "reasonable";


}
