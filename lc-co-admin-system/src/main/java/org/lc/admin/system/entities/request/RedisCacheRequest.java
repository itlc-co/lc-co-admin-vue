package org.lc.admin.system.entities.request;

import lombok.*;
import org.lc.admin.common.base.entities.request.BaseRequest;

/**
 * Description: redis缓存请求
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisCacheRequest extends BaseRequest {

    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 缓存模块名称
     */
    private String cacheModule;

    /**
     * 缓存键
     */
    private String cacheKey;


}
