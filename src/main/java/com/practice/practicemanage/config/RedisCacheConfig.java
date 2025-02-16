package com.practice.practicemanage.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import java.time.Duration;

@Configuration  // 标记该类是一个配置类，Spring 会自动扫描并注册为 Spring 上下文的一部分
@EnableCaching  // 启用 Spring 的缓存功能，允许在应用程序中使用缓存注解，如 @Cacheable, @CachePut, @CacheEvict
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        // 创建 Redis 缓存的配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30));  // 设置缓存的默认过期时间为 30 分钟

        // 返回 RedisCacheManager，该类负责缓存的管理和过期时间设置
        return RedisCacheManager.builder(redisTemplate.getConnectionFactory()) // 使用 RedisTemplate 的连接工厂
                .cacheDefaults(redisCacheConfiguration)  // 设置默认的缓存配置
                .transactionAware()  // 启用事务感知，保证缓存操作和事务操作的一致性
                .build();  // 构建并返回 RedisCacheManager 实例
    }
}
