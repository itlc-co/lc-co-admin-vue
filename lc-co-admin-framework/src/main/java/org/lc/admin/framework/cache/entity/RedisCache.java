package org.lc.admin.framework.cache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Description: redis缓存
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors
public class RedisCache {

    /**
     * 缓存模块名称
     */
    private String module;
    /**
     * 缓存名称
     */
    private String name;
    /**
     * 缓存键名
     */
    private String key;
    /**
     * 缓存内容
     */
    private String value;
    /**
     * 备注
     */
    private String remark;


}
