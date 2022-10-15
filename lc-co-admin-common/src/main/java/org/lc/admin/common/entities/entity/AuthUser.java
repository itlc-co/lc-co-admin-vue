package org.lc.admin.common.entities.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Description: 认证用户
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-31  21:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthUser extends SystemUser {

    private static final long serialVersionUID = 350101197908239745L;

    /**
     * 部门
     */
    private SystemDept dept;

    /**
     * 角色列表
     */
    private List<SystemRole> roles;

    /**
     * 角色组id
     */
    private Long[] roleIds;

    /**
     * 岗位组id
     */
    private Long[] postIds;

    /**
     * 角色ID
     */
    private Long roleId;

}
