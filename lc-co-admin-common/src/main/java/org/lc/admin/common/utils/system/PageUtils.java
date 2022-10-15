package org.lc.admin.common.utils.system;

import com.github.pagehelper.PageHelper;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.entities.page.PageDomain;
import org.lc.admin.common.entities.page.TableDataSupport;
import org.lc.admin.common.utils.SqlUtils;

/**
 * Description: 分页工具类
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:21
 */
public class PageUtils extends PageHelper {

    /**
     * Description: 设置分页参数启动分页
     *
     * @param pagination 分页注解
     * @date 2022-09-10 20:15
     */
    public static void startPage(Pagination pagination) {
        PageDomain pageDomain = TableDataSupport.buildPageRequest(pagination);
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage() {
        PageHelper.clearPage();
    }

}

