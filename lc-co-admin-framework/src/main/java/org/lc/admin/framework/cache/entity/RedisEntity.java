package org.lc.admin.framework.cache.entity;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.lc.admin.common.pool.RedisConstantsPool;
import org.lc.admin.framework.config.ApplicationConfig;

import java.util.concurrent.TimeUnit;

/**
 * Description: redis实体
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class RedisEntity {

    /**
     * 缓存模块
     */
    private String module;

    /**
     * 缓存名称
     */
    private String name;

    /**
     * 缓存键
     */
    private String key;

    /**
     * 缓存值
     */
    private Object value;

    /**
     * ttl过期时间
     */
    private Long ttl;

    /**
     * 时间单位
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * Description: 获取缓存键
     *
     * @return {@link String } 缓存键
     * @date 2022-10-02 15:47
     */
    public String getKey() {
        // 根据{applicationName}::{moduleName}::{name}::{key}模板格式化缓存键
        return StrUtil.format(
                RedisConstantsPool.REDIS_KEY_TEMPLATE,
                MapUtil.builder()
                        .put(RedisConstantsPool.REDIS_KEY_APPLICATION_NAME_FLAG, ApplicationConfig.getName())
                        .put(RedisConstantsPool.REDIS_KEY_MODULE_NAME_FLAG, StrUtil.isNotBlank(this.module) ? this.module : "*")
                        .put(RedisConstantsPool.REDIS_KEY_NAME_FLAG, StrUtil.isNotBlank(this.name) ? this.name : "*")
                        .put(RedisConstantsPool.REDIS_KEY_FLAG, StrUtil.isNotBlank(this.key) ? Base64.encode(this.key) : "*")
                        .build()
        );
    }


}
