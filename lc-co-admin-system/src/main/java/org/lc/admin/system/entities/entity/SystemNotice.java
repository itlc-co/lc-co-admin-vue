package org.lc.admin.system.entities.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 通知
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 19:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class SystemNotice extends BaseEntity {

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private Integer noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

}
