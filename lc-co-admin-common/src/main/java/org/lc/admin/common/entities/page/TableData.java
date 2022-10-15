package org.lc.admin.common.entities.page;

import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.base.pool.ResponseConstantsPool;

import java.util.List;

/**
 * Description: 表格数据
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:07
 */
public class TableData extends ResultResponse {

    /**
     * Description: 表格数据
     *
     * @param code 状态码
     * @param msg  消息
     * @date 2022-09-10 17:34
     */
    public TableData(Integer code, String msg) {
        super(code, msg);
    }

    /**
     * Description: 表格数据
     *
     * @param statusMsg 状态消息实体
     * @date 2022-09-10 17:42
     */
    public TableData(StatusMsg statusMsg) {
        this(statusMsg.getCode(), statusMsg.getMsg());
    }

    /**
     * Description: 表格数据
     *
     * @param statusMsg 状态消息实体
     * @date 2022-09-10 17:42
     */
    public TableData(StatusMsg statusMsg, List<?> rows, Long total) {
        this(statusMsg.getCode(), statusMsg.getMsg(), rows, total);
    }

    /**
     * Description: 表格数据
     *
     * @param code  状态码
     * @param msg   消息
     * @param rows  记录列表
     * @param total 总数
     * @date 2022-09-10 17:35
     */
    public TableData(Integer code, String msg, List<?> rows, Long total) {
        super(code, msg);
        super.put(ResponseConstantsPool.ROWS_TAG, rows);
        super.put(ResponseConstantsPool.TOTAL_TAG, total);
    }

    /**
     * Description: 成功表格数据
     *
     * @param rows  行记录
     * @param total 总计数
     * @return {@link TableData } 表格数据
     * @date 2022-09-10 17:45
     */
    public static TableData success(List<?> rows, Long total) {
        return new TableData(StatusMsg.SUCCESS, rows, total);
    }

    /**
     * Description: 成功表格数据
     *
     * @param rows 行记录
     * @return {@link TableData } 表格数据
     * @date 2022-10-12 22:05
     */
    public static TableData success(List<?> rows) {
        return new TableData(StatusMsg.SUCCESS, rows, ((long) rows.size()));
    }

}
