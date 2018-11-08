package com.github.giraffetree.playspringredis.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author GiraffeTree
 * @date 2018/8/31
 */
@ConfigurationProperties(prefix = "redis")
public class RedisProps {

    private RedisProperties basic;

    /**
     * redis pool最大空闲连接
     */
    private int maxIdle = 10;

    /**
     * redis pool最连接数
     */
    private int maxTotal = 50;

    /**
     * redis pool最小空闲连接
     */
    private int minIdle = 0;

    /**
     * 缓存有效期
     */
    private Duration entryTtl = Duration.ofHours(2L);

    /**
     * 连接超时
     */
    private Duration connectTimeout = Duration.ofSeconds(5L);

    /**
     * 读取超时
     */
    private Duration readTimeout = Duration.ofSeconds(10L);

    public RedisProperties getBasic() {
        return basic;
    }

    public void setBasic(RedisProperties basic) {
        this.basic = basic;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public Duration getEntryTtl() {
        return entryTtl;
    }

    public void setEntryTtl(Duration entryTtl) {
        this.entryTtl = entryTtl;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }
}
