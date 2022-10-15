package org.lc.admin.system.entities.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 用户岗位关联
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 19:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemUserPost {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;
}
