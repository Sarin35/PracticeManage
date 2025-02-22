package com.practice.practicemanage.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import java.time.Duration;

@Configuration
@EnableCaching // 开启缓存
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        // 设置key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置value的序列化方式
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 设置hash key的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置hash value的序列化方式
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        // 创建 Redis 缓存的配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30)) // 设置默认过期时间
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer())); // 设置值的序列化方式

        // 创建并返回 RedisCacheManager 实例
        return RedisCacheManager.builder(redisTemplate.getConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)  // 设置默认缓存配置
                .transactionAware()  // 启用事务感知
                .build();
    }
}
