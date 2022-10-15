package org.lc.admin.system.entities.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Description: 路由
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-08 16:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Router {

    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 路由参数：如 {"id": 1, "name": "admin"}
     */
    private String query;

    /**
     * 是否总是显示路由 0 否 1 是
     */
    private Boolean isAlwaysShow;

    /**
     * 元数据（标题等信息）
     */
    private Meta meta;

    /**
     * 子路由
     */
    private List<Router> children;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Accessors(chain = true)
    public static class Meta {

        /**
         * 名字标题
         */
        private String title;

        /**
         * 图标
         */
        private String icon;

        /**
         * 设置为false，则不会被 <keep-alive>缓存
         */
        private Boolean isCache;

        /**
         * 内链地址（http(s)://开头）
         */
        private String link;
    }

}
