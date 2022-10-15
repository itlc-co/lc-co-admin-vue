package org.lc.admin.system.entities.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.lc.admin.common.entities.entity.SystemRole;

/**
 * Description: 角色响应
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 19:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
public class SystemRoleResponse extends SystemRole {

    /**
     * 是否选择
     */
    private Boolean isChoose;

}
