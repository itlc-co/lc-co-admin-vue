package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Description: 菜单请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemMenuRequest extends BaseRequest {

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String menuName;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 是否可见（0 可见 1 隐藏）
     */
    private Integer visible;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private Character menuType;

    /**
     * 菜单组件路径
     */
    @Size(max = 200, message = "组件路径不能超过255个字符")
    private String component;

    /**
     * 菜单参数
     */
    private String query;

    /**
     * 权限
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 是否外链
     */
    private Boolean isFrame;

    /**
     * 是否缓存
     */
    private Boolean isCache;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单路径
     */
    @Size(max = 200, message = "路由地址不能超过200个字符")
    private String path;


}
