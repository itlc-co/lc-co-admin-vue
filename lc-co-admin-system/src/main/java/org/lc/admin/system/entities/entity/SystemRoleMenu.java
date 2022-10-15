package org.lc.admin.system.entities.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 角色菜单关联
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-20 16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemRoleMenu {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

}
