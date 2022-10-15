package org.lc.admin.common.entities.page;

import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.utils.servlet.ServletUtils;

/**
 * Description: 表格数据支持
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:07
 */
public class TableDataSupport {

    /**
     * Description: 构建请求参数分页domain
     *
     * @param pagination 分页注解
     * @return {@link PageDomain } 分页domain
     * @date 2022-09-10 20:13
     */
    public static PageDomain buildPageRequest(Pagination pagination) {
        return getPageDomain(pagination);
    }

    /**
     * Description: 获取分页domain
     *
     * @param pagination 分页注解
     * @return {@link PageDomain } 分页domain实体
     * @date 2022-09-10 20:12
     */
    private static PageDomain getPageDomain(Pagination pagination) {
        return PageDomain.builder()
                .pageNum(ServletUtils.getParameterToInt(pagination.pageNum(), 1))
                .pageSize(ServletUtils.getParameterToInt(pagination.pageSize(), 10))
                .orderByColumn(ServletUtils.getParameter(pagination.orderByColumn()))
                .orderType(ServletUtils.getParameter(pagination.orderType()))
                .reasonable(ServletUtils.getParameterToBool(pagination.reasonable()))
                .build();
    }

}
