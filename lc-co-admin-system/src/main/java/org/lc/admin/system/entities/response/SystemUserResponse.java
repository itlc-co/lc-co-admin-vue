package org.lc.admin.system.entities.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.lc.admin.common.entities.entity.SystemUser;

import java.util.Set;

/**
 * Description: 用户响应
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-10 22:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemUserResponse extends SystemUser {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色列表
     */
    private Set<String> roleNames;

}
