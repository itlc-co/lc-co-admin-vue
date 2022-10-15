package org.lc.admin.common.entities.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 部门
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-09-01  17:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemDept extends BaseEntity {


    private static final long serialVersionUID = 150201200305117979L;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 子部门
     */
    private List<SystemDept> children = new ArrayList<>();


}
