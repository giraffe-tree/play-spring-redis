package com.github.giraffetree.playspringredis.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author GiraffeTree
 * @date 2018/8/16
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    public FastJsonRedisSerializer() {

    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        String s;
        if (t == null) {
            s = "{}";
        } else {
            s= JSON.toJSONString(t, SerializerFeature.WriteClassName, SerializerFeature.WriteMapNullValue);
        }
        return s.getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }

}
