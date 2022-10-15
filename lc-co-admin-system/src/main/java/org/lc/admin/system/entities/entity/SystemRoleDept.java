package org.lc.admin.system.entities.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 角色部门关联
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-20 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemRoleDept {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 部门id
     */
    private Long deptId;


}
