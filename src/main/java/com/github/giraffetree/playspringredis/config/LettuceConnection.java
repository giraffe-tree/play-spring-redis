package com.github.giraffetree.playspringredis.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

/**
 * @author GiraffeTree
 * @date 2018/8/31
 */
public interface LettuceConnection {

    default RedisStandaloneConfiguration connection(RedisProps redisConfig) {
        RedisProperties redisProperties = redisConfig.getBasic();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return redisStandaloneConfiguration;
    }

    default RedisConnectionFactory lettuceConnectionFactory(RedisProps redisConfig) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getMaxTotal());
        poolConfig.setMinIdle(redisConfig.getMinIdle());

        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(15))
                .poolConfig(poolConfig)
                .shutdownTimeout(Duration.ZERO)
                .build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(connection(redisConfig), lettuceClientConfiguration);
        // 这里有个这句加上之后 netty 会提示 memory leak, 原因未知
        //  lettuceConnectionFactory.afterPropertiesSet()

        System.out.println("lettuce config: \n\t" + poolConfig);

        return lettuceConnectionFactory;
    }

    default FastJsonRedisSerializer<Object> fastJsonRedisSerializer() {
        ParserConfig.getGlobalInstance().addAccept("com.github.giraffetree.playspringredis.entity");
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteClassName);
        return new FastJsonRedisSerializer<>(Object.class);
    }

}
