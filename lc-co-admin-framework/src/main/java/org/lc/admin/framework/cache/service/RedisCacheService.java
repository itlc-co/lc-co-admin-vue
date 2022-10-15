package org.lc.admin.framework.cache.service;


import org.lc.admin.common.annotation.RequestRateLimiter;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.framework.cache.entity.RedisCache;
import org.lc.admin.system.entities.entity.SystemConfig;
import org.lc.admin.system.entities.entity.SystemDictData;
import org.lc.admin.system.entities.entity.SystemDictType;
import org.lc.admin.system.entities.request.RedisCacheRequest;
import org.redisson.api.RRateLimiter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description: redis缓存service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-26 19:17
 */
public interface RedisCacheService {

    /**
     * Description: 查询缓存模块列表数据
     *
     * @return {@link List }<{@link RedisCache }> 缓存模块列表数据
     * @date 2022-10-02 11:47
     */
    List<RedisCache> getModules();

    /**
     * Description: 查询redis服务信息
     *
     * @return {@link Map }<{@link String }, {@link Object }> 服务信息map
     * @date 2022-09-26 23:34
     */
    Map<String, Object> detailInfo();

    /**
     * Description: 根据缓存模块名称查询缓存名称列表数据
     *
     * @param module 缓存模块名称
     * @return {@link List }<{@link RedisCache }> 缓存名称列表数据
     * @date 2022-10-02 11:48
     */
    List<RedisCache> getNamesByModule(String module);

    /**
     * Description: 根据uuid,value保存验证码缓存
     *
     * @param uuid  uuid唯一标识
     * @param value 验证码code
     * @date 2022-10-02 11:49
     */
    void saveCaptchaCode(String uuid, String value);

    /**
     * Description: 根据用户详情保存用户详情缓存
     *
     * @param user 用户详情
     * @date 2022-10-02 11:50
     */
    void saveUserDetail(UserDetail user);

    /**
     * Description: 根据uuid查询用户详情数据
     *
     * @param uuid uuid唯一标识
     * @return {@link UserDetail } 用户详情数据
     * @date 2022-10-02 11:50
     */
    UserDetail getUserDetail(String uuid);

    /**
     * Description: 根据用户名称查询密码错误次数缓存
     *
     * @param userName 用户名
     * @return {@link Long } 密码错误次数
     * @date 2022-10-02 11:50
     */
    Long getPasswordRetryCount(String userName);

    /**
     * Description: 根据用户名称设置并且增加密码重试次数
     *
     * @param userName 用户名称
     * @param ttl      ttl 过期时间
     * @param timeUnit 时间单位
     * @return {@link Long } 增加后的次数
     * @date 2022-10-02 11:51
     */
    Long setAndIncrementPasswordRetryCount(String userName, Long ttl, TimeUnit timeUnit);

    /**
     * Description: 根据用户名称清除密码重试次数缓存
     *
     * @param userName 用户名称
     * @return boolean true 存在清除成功 false 不存在清除失败
     * @date 2022-10-02 11:51
     */
    boolean clearPasswordRetryCount(String userName);

    /**
     * Description: 根据uuid查询并且删除验证码缓存
     *
     * @param uuid uuid 唯一标识
     * @return {@link String } 验证码code
     * @date 2022-10-02 11:52
     */
    String getAndDeleteCaptchaCode(String uuid);

    /**
     * Description: 根据uuid删除用户详情
     *
     * @param uuid uuid唯一标识
     * @return boolean true 存在即删除成功 false 不存在则删除失败
     * @date 2022-10-02 11:53
     */
    boolean deleteUserDetail(String uuid);

    /**
     * Description: 查询用户详情列表缓存数据
     *
     * @return {@link List }<{@link UserDetail }> 用户详情列表缓存数据
     * @date 2022-10-02 11:53
     */
    List<UserDetail> getUserDetailList();

    /**
     * Description: 根据配置列表保存配置列表缓存数据
     *
     * @param configs 配置列表
     * @date 2022-10-02 11:54
     */
    void saveSystemConfigList(List<SystemConfig> configs);

    /**
     * Description: 根据字典数据列表保存字典列表缓存数据
     *
     * @param dictDatas dict类型数据
     * @date 2022-10-02 11:54
     */
    void saveDictDataList(List<SystemDictData> dictDatas);

    /**
     * Description: 根据请求参数查询缓存键列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link RedisCache }> 缓存键列表数据
     * @date 2022-10-02 11:54
     */
    List<RedisCache> getKeysByParam(RedisCacheRequest requestParam);

    /**
     * Description: 根据请求参数查询缓存值数据
     *
     * @param requestParam 请求参数
     * @return {@link RedisCache } 缓存值数据
     * @date 2022-10-02 11:55
     */
    RedisCache getValuesByParam(RedisCacheRequest requestParam);

    /**
     * Description: 根据请求参数清除缓存数据
     *
     * @param requestParam 请求参数
     * @return boolean true 清除成功 false 清除失败
     * @date 2022-10-02 11:55
     */
    boolean clearCache(RedisCacheRequest requestParam);

    /**
     * Description: 刷新字典数据缓存数据
     *
     * @date 2022-10-02 11:56
     */
    void refreshDictData();

    /**
     * Description: 保存字典缓存数据
     *
     * @date 2022-10-02 11:56
     */
    void saveDictData();

    /**
     * Description: 清除字典缓存数据
     *
     * @date 2022-10-02 11:56
     */
    void clearDictData();

    /**
     * Description: 刷新配置缓存数据
     *
     * @date 2022-10-02 11:57
     */
    void refreshSystemConfig();

    /**
     * Description: 清晰配置缓存数据
     *
     * @date 2022-10-02 11:57
     */
    void clearSystemConfig();

    /**
     * Description: 保存配置缓存数据
     *
     * @date 2022-10-02 11:57
     */
    void saveSystemConfig();

    /**
     * Description: 根据字典类型查询字典数据列表缓存
     *
     * @param dictType 字典类型
     * @return {@link List }<{@link SystemDictData }> 字典数据列表缓存
     * @date 2022-10-02 11:57
     */
    List<SystemDictData> getDictDataListByDictType(String dictType);

    /**
     * Description: 根据字典数据保存字典缓存数据
     *
     * @param dictData 字典数据
     * @date 2022-10-02 11:57
     */
    void saveDictData(SystemDictData dictData);

    /**
     * Description: 根据配置键查询配置缓存数据
     *
     * @param configKey 配置键
     * @return {@link SystemConfig } 配置缓存数据
     * @date 2022-10-02 11:58
     */
    SystemConfig getSystemConfigByKey(String configKey);

    /**
     * Description: 根据配置保存配置缓存数据
     *
     * @param config 配置
     * @date 2022-10-02 11:58
     */
    void saveSystemConfig(SystemConfig config);

    /**
     * Description: 根据配置键保存系统配置
     *
     * @param configKey 配置键
     * @date 2022-10-02 11:58
     */
    void saveSystemConfig(String configKey);

    /**
     * Description: 根据配置列表删除配置缓存数据
     *
     * @param configs 配置列表
     * @date 2022-10-02 11:58
     */
    void deleteSystemConfigs(List<SystemConfig> configs);

    /**
     * Description: 根据字典列表保存字典数据列表缓存
     *
     * @param dictDatas 字典列表数据
     * @date 2022-10-02 11:59
     */
    void saveDictDataListByType(List<SystemDictData> dictDatas);

    /**
     * Description: 根据字典类型列表保存字典数据列表缓存
     *
     * @param dictTypes 字典类型列表
     * @date 2022-10-02 12:00
     */
    void deleteDictDatasByDictTypes(List<SystemDictType> dictTypes);

    /**
     * Description: 根据缓存键获取重复请求缓存value
     *
     * @param key 缓存键
     * @return {@link Map }<{@link String },{@link Object }> 重复请求键的value值map
     * @date 2022-10-05 19:25
     */
    Map<String, Object> getRepeatRequest(String key);

    /**
     * Description: 保存重复请求缓存
     *
     * @param key   缓存键
     * @param value 缓存数据
     * @param ttl   过期时间
     * @date 2022-10-05 19:38
     */
    void saveRepeatRequest(String key, Map<String, Object> value, int ttl);

    /**
     * Description: 获取和配置速度限制器
     *
     * @param key         速度限制器键
     * @param rateLimiter 速度限制器
     * @return {@link RRateLimiter } 速度限制器
     * @date 2022-10-05 22:55
     */
    RRateLimiter getAndSetRateLimiter(String key, RequestRateLimiter rateLimiter);
}
