package org.lc.admin.framework.config;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.client.RedisClient;
import org.redisson.client.RedisClientConfig;
import org.redisson.client.RedisConnection;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Description: 缓存配置
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-07 21:28
 */
@Configuration
public class CacheConfig {

    @Value("${spring.redis.host}")
    public String host;

    @Value("${spring.redis.port}")
    public Integer port;

    @Value("${spring.redis.password}")
    public String password;

    /**
     * Description: 序列化编解码器
     *
     * @return {@link SerializationCodec } 序列化编解码器
     * @date 2022-09-24 10:56
     */
    @Bean
    public SerializationCodec serializationCodec() {
        return new SerializationCodec();
    }

    /**
     * Description: redis客户端
     *
     * @return {@link RedisClient } redis客户端
     * @date 2022-09-26 15:57
     */
    @Bean
    public RedisClient redisClient() {
        RedisClientConfig config = new RedisClientConfig();
        config
                .setAddress(StrUtil.format("redis://{}:{}", host, port))
                .setDatabase(1)
                .setClientName("redisson_client")
                .setGroup(new NioEventLoopGroup());
        if (StrUtil.isNotBlank(password)) {
            config.setPassword(password);
        }
        return RedisClient.create(config);
    }


    /**
     * Description: redis连接实例
     *
     * @return {@link RedisConnection }  redis连接实例
     * @date 2022-09-26 16:12
     */
    @Bean
    public RedisConnection redisConnection() {
        return redisClient().connect();
    }

    /**
     * Description: redis操作Template
     *
     * @param redisConnectionFactory redis连接工厂
     * @return {@link RedisTemplate }<{@link String },{@link Object }> redisTemplate
     * @date 2022-10-02 15:45
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }


}
