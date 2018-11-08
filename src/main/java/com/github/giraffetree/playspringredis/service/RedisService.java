package com.github.giraffetree.playspringredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author GiraffeTree
 * @date 2018/11/8
 */
@Service
public class RedisService {

    private RedisTemplate<Serializable, Serializable> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveKeyAndValue(String key, String value) {
        ValueOperations<Serializable, Serializable> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public String getByKey(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

}
