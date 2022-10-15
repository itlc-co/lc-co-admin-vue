package org.lc.admin.common.entities.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.lc.admin.common.base.entities.entity.BaseEntity;

/**
 * Description: 角色
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  17:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemRole extends BaseEntity {

    private static final long serialVersionUID = 510101197606186668L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限
     */
    private String roleKey;

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    private Integer dataScope;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    private Boolean deptCheckStrictly;


    /**
     * Description: 是否为admin管理角色
     *
     * @return boolean
     * @date 2022-09-13 09:41
     */
    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    /**
     * Description: 是否为admin管理角色
     *
     * @param roleId 角色id
     * @return boolean
     * @date 2022-09-13 09:41
     */
    public static boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }

}