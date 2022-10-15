package org.lc.admin.framework.cache.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.annotation.RequestRateLimiter;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.pool.AuthConstantsPool;
import org.lc.admin.common.pool.RedisConstantsPool;
import org.lc.admin.common.pool.TokenConstantsPool;
import org.lc.admin.framework.cache.entity.RedisCache;
import org.lc.admin.framework.cache.entity.RedisEntity;
import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.framework.cache.service.RedisService;
import org.lc.admin.framework.config.ApplicationConfig;
import org.lc.admin.framework.config.properties.TokenProperties;
import org.lc.admin.system.entities.entity.SystemConfig;
import org.lc.admin.system.entities.entity.SystemDictData;
import org.lc.admin.system.entities.entity.SystemDictType;
import org.lc.admin.system.entities.request.RedisCacheRequest;
import org.lc.admin.system.entities.request.SystemConfigRequest;
import org.lc.admin.system.entities.request.SystemDictDataRequest;
import org.lc.admin.system.service.SystemConfigService;
import org.lc.admin.system.service.SystemDictDataService;
import org.redisson.api.RRateLimiter;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Description: redis缓存service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 08:37
 */
@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    @Resource
    private RedisService redisService;

    @Resource
    private SerializationCodec serializationCodec;

    @Resource
    private SystemDictDataService dictDataService;

    @Resource
    private SystemConfigService configService;

    /**
     * Description: 查询缓存模块列表数据
     *
     * @return {@link List }<{@link RedisCache }> 缓存模块列表数据
     * @date 2022-10-02 14:51
     */
    @Override
    public List<RedisCache> getModules() {
        return RedisConstantsPool.MODULES.entrySet().stream().distinct().filter(ObjectUtil::isNotNull).map((entry) -> RedisCache.builder()
                .module(entry.getKey())
                .remark(entry.getValue())
                .build()).collect(Collectors.toList());
    }

    /**
     * Description: 查询redis服务信息
     *
     * @return {@link Map }<{@link String }, {@link Object }> 服务信息map
     * @date 2022-10-02 14:51
     */
    @Override
    public Map<String, Object> detailInfo() {
        return this.redisService.detailInfo();
    }

    /**
     * Description: 根据配置列表保存配置列表缓存数据
     *
     * @param configs 配置列表
     * @date 2022-10-02 14:52
     */
    @Override
    public void saveSystemConfigList(List<SystemConfig> configs) {
        this.redisService.set(
                configs.stream()
                        // 转换为redisEntity实体
                        .map(
                                (config) -> RedisEntity.builder()
                                        .module(RedisConstantsPool.SYSTEM_CONFIG_MODULE)
                                        .name(config.getConfigModule())
                                        .key(config.getConfigKey())
                                        .value(config)
                                        .build()
                        )
                        .collect(Collectors.toList())
        );
    }


    /**
     * Description: 刷新字典数据缓存数据
     *
     * @date 2022-10-02 14:52
     */
    @Override
    public void refreshDictData() {
        this.clearDictData();
        this.saveDictData();
    }

    /**
     * Description: 保存字典缓存数据
     *
     * @date 2022-10-02 14:53
     */
    @Override
    public void saveDictData() {
        // 构造字典请求参数
        SystemDictDataRequest requestParam = new SystemDictDataRequest();
        // 正常状态
        requestParam.setStatus(0);
        // 查询字典数据列表后保存缓存
        this.saveDictDataList(this.dictDataService.selectDictDataList(requestParam));
    }


    /**
     * Description: 刷新配置缓存数据
     *
     * @date 2022-10-02 14:53
     */
    @Override
    public void refreshSystemConfig() {
        this.clearSystemConfig();
        this.saveSystemConfig();
    }

    /**
     * Description: 清晰配置缓存数据
     *
     * @date 2022-10-02 14:53
     */
    @Override
    public void clearSystemConfig() {
        this.clearCache(StrUtil.format("{}::{}::*", ApplicationConfig.getName(), RedisConstantsPool.SYSTEM_CONFIG_MODULE));
    }


    /**
     * Description: 根据配置列表删除配置缓存数据
     *
     * @param configs 配置列表
     * @date 2022-10-02 14:53
     */
    @Override
    public void deleteSystemConfigs(List<SystemConfig> configs) {
        this.redisService.deleteEntity(
                configs.stream()
                        // 转换为redisEntity
                        .map((config) ->
                                RedisEntity.builder()
                                        .module(RedisConstantsPool.SYSTEM_CONFIG_MODULE)
                                        .name(config.getConfigModule())
                                        .key(config.getConfigKey())
                                        .build())
                        // 去重
                        .distinct()
                        .collect(Collectors.toList())
        );
    }


    /**
     * Description: 获取重复请求缓存value
     *
     * @param key 缓存键
     * @return {@link Map }<{@link String },{@link Object }> 重复请求键的value值map
     * @date 2022-10-05 19:39
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public Map<String, Object> getRepeatRequest(String key) {
        return (Map<String, Object>) this.redisService.get(
                RedisEntity.builder()
                        .module(RedisConstantsPool.REQUEST_MODULE)
                        .name(RedisConstantsPool.REPEAT_REQUEST_NAME)
                        .key(key)
                        .build()
        );
    }

    /**
     * Description: 获取和配置速度限制器
     *
     * @param key         速度限制器键
     * @param rateLimiter 速度限制器
     * @return {@link RRateLimiter } 速度限制器
     * @date 2022-10-05 23:00
     */
    @Override
    public RRateLimiter getAndSetRateLimiter(String key, RequestRateLimiter rateLimiter) {
        RRateLimiter rRateLimiter = this.redisService.getRRateLimiter(key);
        this.redisService.trySetRate(key, rateLimiter.rateType(), rateLimiter.rate(), rateLimiter.interval(), rateLimiter.unit());
        return rRateLimiter;
    }

    /**
     * Description: 保存重复请求缓存
     *
     * @param key   缓存键
     * @param value 缓存数据
     * @param ttl   过期时间
     * @date 2022-10-05 19:39
     */
    @Override
    public void saveRepeatRequest(String key, Map<String, Object> value, int ttl) {
        this.redisService.set(
                RedisEntity.builder()
                        .module(RedisConstantsPool.REQUEST_MODULE)
                        .name(RedisConstantsPool.REPEAT_REQUEST_NAME)
                        .key(key)
                        .value(value)
                        .ttl((long) ttl)
                        .timeUnit(TimeUnit.MILLISECONDS)
                        .build()
        );
    }

    /**
     * Description: 根据字典类型列表保存字典数据列表缓存
     *
     * @param dictTypes 字典类型列表
     * @date 2022-10-02 14:55
     */
    @Override
    public void deleteDictDatasByDictTypes(List<SystemDictType> dictTypes) {
        this.redisService.deleteEntity(
                dictTypes
                        .stream()
                        // 转换为RedisEntity
                        .map(
                                (dictType) ->
                                        RedisEntity.builder()
                                                .module(RedisConstantsPool.SYSTEM_DICT_MODULE)
                                                .name(dictType.getDictModule())
                                                .key(dictType.getDictType())
                                                .build()
                        )
                        .collect(Collectors.toList()));
    }


    /**
     * Description: 根据字典列表保存字典数据列表缓存
     *
     * @param dictDatas 字典列表数据
     * @date 2022-10-02 14:56
     */
    @Override
    public void saveDictDataListByType(List<SystemDictData> dictDatas) {
        // 转换字典数据列表为以缓存键为key，以字典数据列表为value的map，重复保留key1
        this.redisService.set(dictDatas.stream().collect(Collectors.toMap((dictData) -> StrUtil.format("{}::{}::{}::{}", ApplicationConfig.getName(), RedisConstantsPool.SYSTEM_DICT_MODULE, dictData.getDictModule(), Base64.encode(dictData.getDictType())), (dictData) -> this.dictDataService.selectDictDataByDictType(dictData.getDictType()), (key1, key2) -> key1)));
    }

    /**
     * Description: 根据配置键保存系统配置
     *
     * @param configKey 配置键
     * @date 2022-10-02 14:58
     */
    @Override
    public void saveSystemConfig(String configKey) {
        // 根据缓存键查询配置数据后保存缓存
        this.saveSystemConfig(this.configService.selectConfigByKey(configKey));
    }

    /**
     * Description: 根据配置保存配置缓存数据
     *
     * @param config 配置
     * @date 2022-10-02 14:59
     */
    @Override
    public void saveSystemConfig(SystemConfig config) {
        this.redisService.set(
                // 转换为RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.SYSTEM_CONFIG_MODULE)
                        .name(config.getConfigModule())
                        .key(config.getConfigKey())
                        .value(config).build()
        );
    }

    /**
     * Description: 根据配置键查询配置缓存数据
     *
     * @param configKey 配置键
     * @return {@link SystemConfig } 配置缓存数据
     * @date 2022-10-02 15:00
     */
    @Override
    public SystemConfig getSystemConfigByKey(String configKey) {
        return this.redisService.get(
                // 构建RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.SYSTEM_CONFIG_MODULE)
                        // 根据配置键查询配置模块作为缓存名称
                        .name(this.configService.selectConfigModuleByKey(configKey))
                        .key(configKey).build(),
                SystemConfig.class
        );
    }

    /**
     * Description: 根据字典数据保存字典缓存数据
     *
     * @param dictData 字典数据
     * @date 2022-10-02 15:01
     */
    @Override
    public void saveDictData(SystemDictData dictData) {
        this.redisService.set(
                // 构建RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.SYSTEM_DICT_MODULE)
                        .name(dictData.getDictModule())
                        .key(dictData.getDictType())
                        // 根据字典类型名称查询字典数据列表作为缓存值
                        .value(this.dictDataService.selectDictDataByDictType(dictData.getDictType()))
                        .build()
        );
    }

    /**
     * Description: 根据字典类型查询字典数据列表缓存
     *
     * @param dictType 字典类型
     * @return {@link List }<{@link SystemDictData }> 字典数据列表缓存
     * @date 2022-10-02 15:02
     */
    @Override
    public List<SystemDictData> getDictDataListByDictType(String dictType) {
        return ListUtil.toList(
                this.redisService.getCollValueByEntity(
                        RedisEntity.builder()
                                .module(RedisConstantsPool.SYSTEM_DICT_MODULE)
                                // 根据字典类型名称查询字典模块作为缓存名称
                                .name(this.dictDataService.selectDictModuleByDictType(dictType))
                                .key(dictType)
                                .build(),
                        SystemDictData.class
                ));
    }

    /**
     * Description: 保存配置缓存数据
     *
     * @date 2022-10-02 15:03
     */
    @Override
    public void saveSystemConfig() {
        // 查询配置列表数据后保存缓存
        this.saveSystemConfigList(this.configService.selectConfigList(new SystemConfigRequest()));
    }

    /**
     * Description: 清除字典缓存数据
     *
     * @date 2022-10-02 15:03
     */
    @Override
    public void clearDictData() {
        // 根据正则表达式* 删除所有字典相关模块的缓存数据
        this.clearCache(StrUtil.format("{}::{}::*", ApplicationConfig.getName(), RedisConstantsPool.SYSTEM_DICT_MODULE));
    }

    /**
     * Description: 根据请求参数清除缓存数据
     *
     * @param requestParam 请求参数
     * @return boolean true 清除成功 false 清除失败
     * @date 2022-10-02 15:04
     */
    @Override
    public boolean clearCache(RedisCacheRequest requestParam) {
        // 缓存请求参数
        String cacheModule = requestParam.getCacheModule();
        String cacheName = requestParam.getCacheName();
        String cacheKey = requestParam.getCacheKey();

        // pattern 模式匹配表达式
        StringBuilder sb = StrUtil.builder(ApplicationConfig.getName());

        // 缓存模块名称
        if (StrUtil.isNotBlank(cacheModule)) {
            sb.append(StrUtil.format("::{}", cacheModule));
        }

        // 缓存名称
        if (StrUtil.isNotBlank(cacheName)) {
            sb.append(StrUtil.format("::{}", cacheName));
        }

        // 缓存键
        if (StrUtil.isNotBlank(cacheKey)) {
            sb.append(StrUtil.format("::{}", Base64.encode(cacheKey)));
        }

        // *正则匹配
        if (StrUtil.isBlank(cacheKey)) {
            sb.append("::*");
        }

        // 删除匹配表达式的缓存数据
        return this.clearCache(sb.toString());
    }


    /**
     * Description: 根据pattern模式匹配key清除缓存数据
     *
     * @param pattern 模式匹配表达式
     * @return boolean true 存在key清理成功 false 不存在key清理失败
     * @date 2022-10-02 15:05
     */
    private boolean clearCache(String pattern) {
        return this.redisService.deleteByPattern(pattern);
    }

    /**
     * Description: 根据请求参数查询缓存值数据
     *
     * @param requestParam 请求参数
     * @return {@link RedisCache } 缓存值数据
     * @date 2022-10-02 15:07
     */
    @Override
    public RedisCache getValuesByParam(RedisCacheRequest requestParam) {
        // redisson序列化编码类型
        Codec codec = JsonJacksonCodec.INSTANCE;

        // 请求参数中获取缓存模块，缓存名称，缓存键
        String cacheModule = requestParam.getCacheModule();
        String cacheName = requestParam.getCacheName();
        String cacheKey = requestParam.getCacheKey();

        // 构建RedisEntity
        RedisEntity entity = RedisEntity.builder()
                .module(cacheModule)
                .name(cacheName)
                .key(cacheKey)
                .build();

        if (StrUtil.equalsIgnoreCase(cacheModule, RedisConstantsPool.AUTH_MODULE) && StrUtil.equalsIgnoreCase(cacheName, RedisConstantsPool.AUTH_TOKEN_NAME)) {
            // auth模块下的auth_token的缓存修改为jdk序列化方式
            codec = serializationCodec;
        }

        if (StrUtil.equalsIgnoreCase(cacheModule, RedisConstantsPool.REQUEST_MODULE) && StrUtil.equalsIgnoreCase(cacheName, RedisConstantsPool.REQUEST_RATE_LIMITER_NAME)) {
            // 请求限流缓存采用字符串序列表
            codec = StringCodec.INSTANCE;
            return RedisCache.builder()
                    // 构建redisCache实例
                    .module(cacheModule)
                    .name(cacheName)
                    .key(cacheKey)
                    .value(
                            StrUtil.toString(
                                    // 获取指定RedisEntity的缓存值
                                    this.redisService.getMap(entity, codec)
                            ))
                    .build();
        }

        return RedisCache.builder()
                // 构建redisCache实例
                .module(cacheModule)
                .name(cacheName)
                .key(cacheKey)
                .value(
                        StrUtil.toString(
                                // 获取指定RedisEntity的缓存值
                                this.redisService.get(entity, codec)
                        ))
                .build();
    }

    /**
     * Description: 根据请求参数查询缓存键列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link RedisCache }> 缓存键列表数据
     * @date 2022-10-02 15:10
     */
    @Override
    public List<RedisCache> getKeysByParam(RedisCacheRequest requestParam) {
        return this.redisService.getKeysStreamByPattern(
                        // 构建RedisEntity
                        RedisEntity.builder()
                                // 缓存模块
                                .module(requestParam.getCacheModule())
                                // 缓存名称
                                .name(requestParam.getCacheName()).build()
                )
                .map((key) ->
                        // key转换为RedisCache实例
                        RedisCache.builder()
                                .module(requestParam.getCacheModule())
                                .name(requestParam.getCacheName())
                                .key(Base64.decodeStr(StrUtil.subAfter(key, "::", true)))
                                .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * Description: 根据字典数据列表保存字典列表缓存数据
     *
     * @param dictDatas dict类型数据
     * @date 2022-10-02 15:11
     */
    @Override
    public void saveDictDataList(List<SystemDictData> dictDatas) {
        redisService.set(dictDatas.stream()
                // 字典数据按照redis key分组
                .collect(Collectors.groupingBy((dictData) ->
                                // 拼接redis key
                                StrUtil.join("::", ApplicationConfig.getName(), RedisConstantsPool.SYSTEM_DICT_MODULE, dictData.getDictModule(), Base64.encode(dictData.getDictType()))
                        )
                )
        );
    }

    /**
     * Description: 查询用户详情列表缓存数据
     *
     * @return {@link List }<{@link UserDetail }> 用户详情列表缓存数据
     * @date 2022-10-02 15:12
     */
    @Override
    public List<UserDetail> getUserDetailList() {
        return this.redisService.getValueListsByPattern(
                        // 构建RedisEntity并指定jdk序列方式
                        RedisEntity.builder()
                                .module(RedisConstantsPool.AUTH_MODULE)
                                .name(RedisConstantsPool.AUTH_TOKEN_NAME)
                                .key(StrUtil.EMPTY)
                                .build(), serializationCodec)
                .stream()
                // Object强制转换为UserDetail
                .map((obj) -> (UserDetail) obj)
                .collect(Collectors.toList());
    }

    /**
     * Description: 根据uuid删除用户详情
     *
     * @param uuid uuid唯一标识
     * @return boolean true 存在即删除成功 false 不存在则删除失败
     * @date 2022-10-02 15:13
     */
    @Override
    public boolean deleteUserDetail(String uuid) {
        return this.redisService.delete(
                // 构建RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.AUTH_MODULE)
                        .name(RedisConstantsPool.AUTH_TOKEN_NAME)
                        .key(uuid)
                        .build()
        );
    }

    /**
     * Description: 根据uuid查询并且删除验证码缓存
     *
     * @param uuid uuid 唯一标识
     * @return {@link String } 验证码code
     * @date 2022-10-02 15:13
     */
    @Override
    public String getAndDeleteCaptchaCode(String uuid) {
        return StrUtil.toStringOrNull(
                // 获取验证码code缓存后删除缓存
                this.redisService.getAndDelete(
                        // 构建RedisEntity
                        RedisEntity.builder()
                                .module(RedisConstantsPool.AUTH_MODULE)
                                .name(RedisConstantsPool.AUTH_CAPTCHA_NAME)
                                .key(uuid)
                                .build()
                ));
    }

    /**
     * Description: 根据用户名称清除密码重试次数缓存
     *
     * @param userName 用户名称
     * @return boolean true 存在清除成功 false 不存在清除失败
     * @date 2022-10-02 15:14
     */
    @Override
    public boolean clearPasswordRetryCount(String userName) {
        return this.redisService.delete(
                // 构建RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.AUTH_MODULE)
                        .name(RedisConstantsPool.AUTH_PWD_RETRY_CNT_NAME)
                        .key(userName)
                        .build()
        );
    }

    /**
     * Description: 根据用户名称设置并且增加密码重试次数
     *
     * @param userName 用户名称
     * @param ttl      ttl 过期时间
     * @param timeUnit 时间单位
     * @return {@link Long } 增加后的次数
     * @date 2022-10-02 15:15
     */
    @Override
    public Long setAndIncrementPasswordRetryCount(String userName, Long ttl, TimeUnit timeUnit) {
        return this.redisService.setAndIncrement(
                // 构建RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.AUTH_MODULE)
                        .name(RedisConstantsPool.AUTH_PWD_RETRY_CNT_NAME)
                        .key(userName)
                        .ttl(ttl)
                        .timeUnit(timeUnit)
                        .build()
        );
    }

    /**
     * Description: 根据用户名称查询密码错误次数缓存
     *
     * @param userName 用户名
     * @return {@link Long } 密码错误次数
     * @date 2022-10-02 15:15
     */
    @Override
    public Long getPasswordRetryCount(String userName) {
        return this.redisService.getAtomicLongValue(
                // 构建RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.AUTH_MODULE)
                        .name(RedisConstantsPool.AUTH_PWD_RETRY_CNT_NAME)
                        .key(userName)
                        .build()
        );
    }

    /**
     * Description: 根据uuid查询用户详情数据
     *
     * @param uuid uuid唯一标识
     * @return {@link UserDetail } 用户详情数据
     * @date 2022-10-02 15:15
     */
    @Override
    public UserDetail getUserDetail(String uuid) {
        return (UserDetail) this.redisService.get(
                RedisEntity.builder()
                        .module(RedisConstantsPool.AUTH_MODULE)
                        .name(RedisConstantsPool.AUTH_TOKEN_NAME)
                        .key(uuid)
                        .build()
                , serializationCodec
        );
    }

    /**
     * Description: 根据用户详情保存用户详情缓存
     *
     * @param user 用户详情
     * @date 2022-10-02 15:16
     */
    @Override
    public void saveUserDetail(UserDetail user) {
        Long ttl = ObjectUtil.defaultIfNull(TokenProperties.getExpiresTime(), TokenConstantsPool.DEFAULT_AUTH_TOKEN_TTL);
        this.redisService.set(
                // 构建RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.AUTH_MODULE)
                        .name(RedisConstantsPool.AUTH_TOKEN_NAME)
                        .key(user.getUuid())
                        .value(user)
                        .ttl(ttl)
                        .timeUnit(TokenConstantsPool.DEFAULT_AUTH_TOKEN_TIMEUNIT)
                        .build()
                , serializationCodec
        );
    }

    /**
     * Description: 根据uuid,value保存验证码缓存
     *
     * @param uuid  uuid唯一标识
     * @param value 验证码code
     * @date 2022-10-02 15:16
     */
    @Override
    public void saveCaptchaCode(String uuid, String value) {
        this.redisService.set(
                // 构建RedisEntity
                RedisEntity.builder()
                        .module(RedisConstantsPool.AUTH_MODULE)
                        .name(RedisConstantsPool.AUTH_CAPTCHA_NAME)
                        .key(uuid)
                        .value(value)
                        .ttl(AuthConstantsPool.DEFAULT_AUTH_CAPTCHA_TTL)
                        .timeUnit(AuthConstantsPool.DEFAULT_TIMEUNIT)
                        .build()
        );
    }

    /**
     * Description: 根据缓存模块名称查询缓存名称列表数据
     *
     * @param module 缓存模块名称
     * @return {@link List }<{@link RedisCache }> 缓存名称列表数据
     * @date 2022-10-02 15:17
     */
    @Override
    public List<RedisCache> getNamesByModule(String module) {
        return RedisConstantsPool.NAMES.getOrDefault(module, ListUtil.empty())
                .stream()
                // 缓存名称去重
                .distinct()
                // 保留缓存不为null
                .filter(ObjectUtil::isNotNull)
                // 转换为RedisCache
                .map((map) -> BeanUtil.toBean(map, RedisCache.class))
                .collect(Collectors.toList());
    }

}
