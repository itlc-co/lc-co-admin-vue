package org.lc.admin.system.entities.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 菜单
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-08 14:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SystemMenu extends BaseEntity {

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单组件地址
     */
    private String path;

    /**
     * 菜单组件路径
     */
    private String component;

    /**
     * 菜单参数
     */
    private String query;

    /**
     * 是否为外链（0否 1是）
     */
    private Boolean isFrame;

    /**
     * 是否缓存（0不缓存 1缓存）
     */
    private Boolean isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private Character menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    private Integer visible;

    /**
     * 菜单权限字符串
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 子菜单
     */
    private List<SystemMenu> children = new ArrayList<SystemMenu>();

}
