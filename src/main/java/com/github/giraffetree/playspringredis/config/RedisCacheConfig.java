package com.github.giraffetree.playspringredis.config;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author GiraffeTree
 * @date 2018/8/31
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisProps.class)
public class RedisCacheConfig extends CachingConfigurerSupport implements LettuceConnection {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisCacheConfig.class);

    private RedisProps redisConfig;

    @Autowired
    public RedisCacheConfig(RedisProps redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {

        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            if (ArrayUtils.isNotEmpty(params)) {
                String collect = Arrays.stream(params).map(x -> x.getClass().getSimpleName()).collect(Collectors.joining(",", "(", ")"));
                sb.append(collect);
            }
            return sb.toString();
        };
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return lettuceConnectionFactory(redisConfig);
    }


    @Bean
    @Override
    public RedisCacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer()))
                .entryTtl(redisConfig.getEntryTtl());

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .transactionAware()
                .build();

        cacheManager.afterPropertiesSet();
        LOGGER.info("RedisCacheManager config success!");
        return cacheManager;
    }

    @Bean
    public RedisTemplate<Serializable, Serializable> redisTemplate() {
        RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

}