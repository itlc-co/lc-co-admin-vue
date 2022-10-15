package org.lc.admin.framework.cache.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.utils.AssertUtils;
import org.lc.admin.framework.cache.entity.RedisEntity;
import org.redisson.api.*;
import org.redisson.client.RedisConnection;
import org.redisson.client.codec.Codec;
import org.redisson.client.protocol.RedisCommands;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: redis服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:48
 */
@Service
public class RedisService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisConnection redisConnection;


    /**
     * Description: 根据redis key键获取value
     *
     * @param key redis键
     * @return {@link Object } value
     * @date 2022-10-02 16:27
     */
    public Object get(String key) {
        return getBucket(key).get();
    }

    /**
     * Description: 根据redis键 value的类型class对象获取value
     *
     * @param key  redis键
     * @param type value的类型
     * @return {@link T } value
     * @date 2022-10-02 16:28
     */
    public <T> T get(String key, Class<T> type) {
        return BeanUtil.toBean(get(key), type);
    }

    /**
     * Description: 根据RedisEntity，value的类型class对象获取value
     *
     * @param entity 实体
     * @param type   类型
     * @return {@link T }
     * @date 2022-10-02 16:29
     */
    public <T> T get(RedisEntity entity, Class<T> type) {
        return BeanUtil.toBean(get(entity), type);
    }

    /**
     * Description: 根据RedisEntity获取集合类型的指定元素类型的value
     *
     * @param entity redis实体
     * @param type   元素类型
     * @return {@link Collection }<{@link T }> 集合类型的指定元素类型的value
     * @date 2022-10-02 16:29
     */
    @SuppressWarnings("unchecked")
    public <T> Collection<T> getCollValueByEntity(RedisEntity entity, Class<T> type) {
        return BeanUtil.copyToList((List<Object>) get(entity), type);
    }

    /**
     * Description: 根据redis键以及指定序列化方式获取object类型的value
     *
     * @param key   redis键
     * @param codec 序列化方式
     * @return {@link Object } object类型的value
     * @date 2022-10-02 16:31
     */
    public Object get(String key, Codec codec) {
        return getBucket(key, codec).get();
    }

    /**
     * Description: 根据redis实体获取value
     *
     * @param entity redis实体
     * @return {@link Object } value
     * @date 2022-10-02 16:32
     */
    public Object get(RedisEntity entity) {
        AssertUtils.isNotNull(entity);
        return get(entity.getKey());
    }

    /**
     * Description: 根据RedisEntity以及指定序列化方式获取object类型的value
     *
     * @param entity redis实体
     * @param codec  序列化方式
     * @return {@link Object } object类型的value
     * @date 2022-10-02 16:32
     */
    public Object get(RedisEntity entity, Codec codec) {
        AssertUtils.isNotNull(entity);
        return get(entity.getKey(), codec);
    }

    /**
     * Description: 获取并且删除指定redis实体的value
     *
     * @param entity 实体
     * @return {@link Object } value值
     * @date 2022-10-02 16:33
     */
    public Object getAndDelete(RedisEntity entity) {
        AssertUtils.isNotNull(entity);
        return getBucket(entity.getKey()).getAndDelete();
    }


    /**
     * Description: 设置redis缓存基本的对象，Integer、String、实体类等
     *
     * @param key   redis键
     * @param value redis值
     * @date 2022-09-29 11:07
     */
    public <T> void set(final String key, final T value) {
        AssertUtils.isNotNull(value);
        getBucket(key).set(value);
    }

    /**
     * Description: 设置redis缓存基本的对象，Integer、String、实体类等
     *
     * @param key      redis键
     * @param value    redis值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @date 2022-09-29 11:08
     */
    public <T> void set(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        AssertUtils.isAllNotNull(value, timeout, timeUnit);
        getBucket(key).set(value, timeout, timeUnit);
    }

    /**
     * Description: 设置redis缓存基本的对象，Integer、String、实体类等
     *
     * @param key   redis键
     * @param value redis值
     * @param codec 序列化方式
     * @date 2022-09-29 11:07
     */
    public <T> void set(final String key, final T value, Codec codec) {
        AssertUtils.isAllNotNull(value, codec);
        getBucket(key, codec).set(value);
    }

    /**
     * Description: 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @date 2022-09-29 11:08
     */
    public <T> void set(final String key, final T value, final Long timeout, final TimeUnit timeUnit, Codec codec) {
        AssertUtils.isAllNotNull(value, timeout, timeUnit);
        getBucket(key, codec).set(value, timeout, timeUnit);
    }

    /**
     * Description: 根据redis实体设置value值
     *
     * @param entity redis实体
     * @date 2022-10-02 16:36
     */
    public void set(RedisEntity entity) {
        AssertUtils.isNotNull(entity);
        if (ObjectUtil.isNotNull(entity.getTtl())) {
            set(entity.getKey(), entity.getValue(), entity.getTtl(), entity.getTimeUnit());
        } else {
            set(entity.getKey(), entity.getValue());
        }
    }


    /**
     * Description: 根据redis实体并且指定序列化方式设置value值
     *
     * @param entity redis实体
     * @param codec  序列化方式
     * @date 2022-10-02 16:37
     */
    public void set(RedisEntity entity, Codec codec) {
        AssertUtils.isNotNull(entity);
        if (ObjectUtil.isNotNull(entity.getTtl())) {
            set(entity.getKey(), entity.getValue(), entity.getTtl(), entity.getTimeUnit(), codec);
        } else {
            set(entity.getKey(), entity.getValue(), codec);
        }
    }

    /**
     * Description: 根据redis实体列表设置redis value值
     *
     * @param entitys redis实体列表
     * @date 2022-10-02 16:37
     */
    public void set(List<RedisEntity> entitys) {
        getBuckets().set(entitys.stream().filter(ObjectUtil::isNotNull).collect(Collectors.toMap(RedisEntity::getKey, RedisEntity::getValue)));
    }

    /**
     * Description: 根据键值map设置redis缓存
     *
     * @param map 键值map
     * @date 2022-10-02 16:38
     */
    public void set(Map<String, ?> map) {
        getBuckets().set(map);
    }


    /**
     * Description: 获取对象列表桶
     *
     * @return {@link RBuckets }
     * @date 2022-10-02 16:43
     */
    private RBuckets getBuckets() {
        return redissonClient.getBuckets();
    }

    /**
     * Description: 获取对象列表桶并且指定序列化方式
     *
     * @param codec 序列化方式
     * @return {@link RBuckets }
     * @date 2022-10-02 16:44
     */
    private RBuckets getBuckets(Codec codec) {
        AssertUtils.isNotNull(codec);
        return redissonClient.getBuckets(codec);
    }

    /**
     * Description: 根据redis键获取redisson基本类型的bucket
     *
     * @param key redis键
     * @return {@link RBucket }<{@link Object }> redis bucket
     * @date 2022-09-29 11:26
     */
    private RBucket<Object> getBucket(String key) {
        AssertUtils.isNotBlank(key);
        return redissonClient.getBucket(key);
    }

    /**
     * Description: 获取指定redis key的对象桶并且指定序列化方式
     *
     * @param key   redis键
     * @param codec 序列化方式
     * @return {@link RBucket }<{@link Object }>
     * @date 2022-10-02 16:45
     */
    private RBucket<Object> getBucket(String key, Codec codec) {
        AssertUtils.isNotBlank(key);
        AssertUtils.isNotNull(codec);
        return redissonClient.getBucket(key, codec);
    }


    /**
     * Description: 获取redis详细信息map
     *
     * @return {@link Map }<{@link String }, {@link Object }> redis详细信息map
     * @date 2022-10-02 16:45
     */
    public Map<String, Object> detailInfo() {
        return MapUtil.ofEntries(
                // redis 服务信息
                MapUtil.entry("info", info()),
                // redis 键数量
                MapUtil.entry("keySize", keySize()),
                // redis command命令count
                MapUtil.entry("commandStats", commandStats())
        );
    }


    /**
     * Description: 获取命令数据wordcount map
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link String }>> 命令数据wordcount map
     * @date 2022-10-02 16:47
     */
    private List<Map<String, String>> commandStats() {
        return redisConnection.sync(RedisCommands.INFO_COMMANDSTATS).entrySet().stream().filter(ObjectUtil::isNotNull).map((entry) -> MapUtil.ofEntries(
                MapUtil.entry("value", StrUtil.subBetween(entry.getValue(), "calls=", ",usec")),
                MapUtil.entry("name", StrUtil.removePrefix(entry.getKey(), "cmdstat_"))
        )).collect(Collectors.toList());
    }

    /**
     * Description: redis键数量
     *
     * @return {@link Long }
     * @date 2022-09-26 19:15
     */
    private Long keySize() {
        return getRKeys().count();
    }

    /**
     * Description: redis 信息数据
     *
     * @return {@link Map }<{@link String }, {@link String }> 信息数据map
     * @date 2022-09-26 19:16
     */
    private Map<String, String> info() {
        // 执行info指令
        return redisConnection.sync(RedisCommands.INFO_ALL);
    }

    /**
     * Description: 删除指定redis键的value
     *
     * @param key redis键
     * @return boolean
     * @date 2022-10-02 16:47
     */
    public boolean delete(String key) {
        return getBucket(key).delete();
    }

    /**
     * Description: 删除指定entity redis实体的value
     *
     * @param entity redis实体
     * @return boolean true 存在且删除成功 false 不存在且删除失败
     * @date 2022-10-02 16:47
     */
    public boolean delete(RedisEntity entity) {
        AssertUtils.isNotNull(entity);
        return getBucket(entity.getKey()).delete();
    }

    /**
     * Description: 删除指定redis键列表的value
     *
     * @param keys redis键
     * @return boolean true 存在且删除成功 false 不存在且删除失败
     * @date 2022-10-02 16:55
     */
    public boolean delete(String... keys) {
        AssertUtils.isAllNotBlank(keys);
        return getRKeys().delete(ArrayUtil.distinct(keys)) > 0;
    }

    /**
     * Description: 删除指定redis键列表的value
     *
     * @param keys redis键
     * @return boolean true 存在且删除成功 false 不存在且删除失败
     * @date 2022-10-02 16:55
     */
    public boolean deleteKeys(List<String> keys) {
        AssertUtils.isNotEmpty(keys);
        return getRKeys().delete(keys.stream().distinct().toArray(String[]::new)) > 0;
    }

    /**
     * Description: 删除指定redis 实体的value
     *
     * @param entitys 实体
     * @return boolean true 存在且删除成功 false 不存在且删除失败
     * @date 2022-10-02 16:55
     */
    public boolean deleteEntity(List<RedisEntity> entitys) {
        return deleteKeys(entitys.stream().filter(ObjectUtil::isNotNull).map(RedisEntity::getKey).distinct().collect(Collectors.toList()));
    }


    /**
     * Description: 获取Rkeys实例
     *
     * @return {@link RKeys } Rkeys实例
     * @date 2022-10-02 16:56
     */
    private RKeys getRKeys() {
        return redissonClient.getKeys();
    }

    /**
     * Description: 根据redis键获取原子的Long类型
     *
     * @param key redis键
     * @return {@link RAtomicLong } 原子的Long类型
     * @date 2022-10-02 16:56
     */
    private RAtomicLong getAtomicLong(String key) {
        return redissonClient.getAtomicLong(key);
    }


    /**
     * Description: 根据redis实体获取原子的Long类型
     *
     * @param entity redis实体
     * @return {@link RAtomicLong } 原子的Long类型
     * @date 2022-10-02 16:56
     */
    private RAtomicLong getAtomicLong(RedisEntity entity) {
        AssertUtils.isNotNull(entity);
        return getAtomicLong(entity.getKey());
    }


    /**
     * Description: 根据redis实体设置并且自增1
     *
     * @param entity 实体
     * @return {@link Long } 自增后的value
     * @date 2022-10-02 16:57
     */
    public Long setAndIncrement(RedisEntity entity) {
        RAtomicLong atomicLong = getAtomicLong(entity);
        if (!atomicLong.isExists()) {
            atomicLong.set(-1L);
        }
        if (ObjectUtil.isAllNotEmpty(entity.getTtl(), entity.getTimeUnit())) {
            atomicLong.expire(entity.getTtl(), entity.getTimeUnit());
        }
        return atomicLong.incrementAndGet();
    }

    /**
     * Description: 根据pattern表达式获取keyIterable实例
     *
     * @param pattern pattern表达式
     * @return {@link Iterable }<{@link String }> keyIterable实例
     * @date 2022-10-02 16:57
     */
    public Iterable<String> getKeysByPattern(String pattern) {
        AssertUtils.isNotBlank(pattern);
        return getRKeys().getKeysByPattern(pattern);
    }

    /**
     * Description: 根据pattern表达式获取keyStream流实例
     *
     * @param pattern pattern表达式
     * @return {@link Stream }<{@link String }> keyStream流实例
     * @date 2022-10-02 16:57
     */
    public Stream<String> getKeysStreamByPattern(String pattern) {
        AssertUtils.isNotBlank(pattern);
        return getRKeys().getKeysStreamByPattern(pattern);
    }

    /**
     * Description: 根据redis实体获取keyStream流实例
     *
     * @param entity redis实体
     * @return {@link Stream }<{@link String }> keyStream流实例
     * @date 2022-10-02 16:57
     */
    public Stream<String> getKeysStreamByPattern(RedisEntity entity) {
        AssertUtils.isNotNull(entity);
        return getKeysStreamByPattern(entity.getKey());
    }


    /**
     * Description: 指定序列化方式与redis键列表获取redis键值map
     *
     * @param codec 序列化方式
     * @param keys  redis键列表
     * @return {@link Map }<{@link String }, {@link Object }> redis键值map
     * @date 2022-10-02 16:57
     */
    public Map<String, Object> gets(Codec codec, String... keys) {
        return getBuckets(codec).get(ArrayUtil.distinct(keys));
    }


    /**
     * Description: 根据redis实体并且指定序列化方式获取redis键值map
     *
     * @param entity redis实体
     * @param codec  序列化方式
     * @return {@link Map }<{@link String }, {@link Object }> redis键值map
     * @date 2022-10-02 16:57
     */
    public Map<String, Object> getValuesByPattern(RedisEntity entity, Codec codec) {
        AssertUtils.isNotNull(entity);
        return gets(codec, ArrayUtil.toArray(getKeysByPattern(entity.getKey()), String.class));
    }

    /**
     * Description: 根据redis实体并且指定序列化方式获取redis值列表
     *
     * @param entity redis实体
     * @param codec  序列化方式
     * @return {@link List }<{@link Object }> redis值列表
     * @date 2022-10-02 16:54
     */
    public List<Object> getValueListsByPattern(RedisEntity entity, Codec codec) {
        return getValuesByPattern(entity, codec).values().stream().filter(ObjectUtil::isNotNull).collect(Collectors.toList());
    }

    /**
     * Description: 删除符合指定匹配redis键的pattern表达式的key
     *
     * @param pattern pattern表达式
     * @return boolean  存在且删除成功 false 不存在且删除失败
     * @date 2022-10-02 16:49
     */
    public boolean deleteByPattern(String pattern) {
        AssertUtils.isNotBlank(pattern);
        return getRKeys().deleteByPattern(pattern) >= 0L;
    }

    /**
     * Description: 获取原子类型的Long类型
     *
     * @param entity 实体
     * @return {@link Long } Long类型 value
     * @date 2022-10-03 21:52
     */
    public Long getAtomicLongValue(RedisEntity entity) {
        return getAtomicLong(entity.getKey()).get();
    }

    /**
     * Description: 获取redisson限流器
     *
     * @param key 限流控制器键
     * @return {@link RRateLimiter } redisson限流器
     * @date 2022-10-05 22:57
     */
    public RRateLimiter getRRateLimiter(String key) {
        AssertUtils.isNotBlank(key);
        return redissonClient.getRateLimiter(key);
    }

    /**
     * Description: 尝试配置限流控制值
     *
     * @param key      限流控制器键
     * @param rateType 类型
     * @param rate     数量
     * @param interval 时间间隔
     * @param unit     时间单位
     * @return boolean true 配置成功 false 配置不成功
     * @date 2022-10-05 22:59
     */
    public boolean trySetRate(String key, RateType rateType, long rate, long interval, RateIntervalUnit unit) {
        return getRRateLimiter(key).trySetRate(rateType, rate, interval, unit);
    }

    /**
     * Description: 获取RMap缓存数据
     *
     * @param entity 实体
     * @param codec  编解码器
     * @return {@link Map }<{@link Object }, {@link Object }> Ramp缓存数据
     * @date 2022-10-07 20:11
     */
    public Map<Object, Object> getMap(RedisEntity entity, Codec codec) {
        return getRMap(entity, codec).readAllMap();
    }

    /**
     * Description: 获取RMap实例
     *
     * @param entity 实体
     * @param codec  编解码器
     * @return {@link RMap }<{@link K }, {@link V }> RMap实例键
     * @date 2022-10-07 20:11
     */
    private <K, V> RMap<K, V> getRMap(RedisEntity entity, Codec codec) {
        AssertUtils.isNotNull(entity);
        return getRMap(entity.getKey(), codec);
    }

    /**
     * Description: 获取RMap实例
     *
     * @param entity 实体
     * @return {@link RMap }<{@link K }, {@link V }> RMap实例
     * @date 2022-10-07 20:12
     */
    private <K, V> RMap<K, V> getRMap(RedisEntity entity) {
        AssertUtils.isNotNull(entity);
        return getRMap(entity.getKey());
    }

    /**
     * Description: 获取RMap实例
     *
     * @param key   RMap键
     * @param codec 编解码器
     * @return {@link RMap }<{@link K }, {@link V }> RMap实例
     * @date 2022-10-07 20:12
     */
    private <K, V> RMap<K, V> getRMap(String key, Codec codec) {
        AssertUtils.isNotBlank(key);
        AssertUtils.isNotNull(codec);
        return redissonClient.getMap(key, codec);
    }

    /**
     * Description: 获取RMap实例
     *
     * @param key RMap键
     * @return {@link RMap }<{@link K }, {@link V }> RMap实例
     * @date 2022-10-07 20:13
     */
    private <K, V> RMap<K, V> getRMap(String key) {
        AssertUtils.isNotBlank(key);
        return redissonClient.getMap(key);
    }

}
