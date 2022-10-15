package org.lc.admin.system.entities.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 用户角色关联
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 19:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemUserRole {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long roleId;
}
