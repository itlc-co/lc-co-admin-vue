package org.lc.admin.generator.entities.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 数据库表
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-15 18:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class DataBaseTable extends BaseEntity {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;


}
