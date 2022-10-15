package org.lc.admin.system.entities.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 岗位
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 10:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemPost extends BaseEntity {

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;


}
