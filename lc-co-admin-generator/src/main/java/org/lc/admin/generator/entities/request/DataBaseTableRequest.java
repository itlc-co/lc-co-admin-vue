package org.lc.admin.generator.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;

/**
 * Description: 数据库表请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 21:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataBaseTableRequest extends BaseRequest {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

}
