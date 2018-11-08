package com.github.giraffetree.playspringredis.controller;

import com.github.giraffetree.playspringredis.dao.AppleRepository;
import com.github.giraffetree.playspringredis.entity.Apple;
import com.github.giraffetree.playspringredis.service.CacheService;
import com.github.giraffetree.playspringredis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author GiraffeTree
 * @date 2018/11/8
 */
@RestController
@RequestMapping("/open")
public class OpenController {

    private CacheService cacheService;
    private AppleRepository appleRepository;
    private RedisService redisService;

    @Autowired
    public OpenController(CacheService cacheService, AppleRepository appleRepository, RedisService redisService) {
        this.cacheService = cacheService;
        this.appleRepository = appleRepository;
        this.redisService = redisService;
    }

    @GetMapping("/apple")
    public Apple getApple(
            @RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        if (id.equals(0L)) {
            return null;
        }
        return cacheService.findById(id);
    }

    @PostMapping("/apple")
    public Apple addApple(
            @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Apple apple = new Apple();
        apple.setCreated(new Date());
        apple.setUpdated(new Date());
        apple.setName(name);
        Apple save = appleRepository.save(apple);
        cacheService.dirtyAppleById(save.getId());
        return save;
    }

    @PostMapping("/value")
    public Map setStr(
            @RequestParam(value = "key", required = false, defaultValue = "") String key,
            @RequestParam(value = "value", required = false, defaultValue = "") String value
    ) {
        HashMap<String, String> map = new HashMap<>();

        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(key)) {
            map.put("error", "null key/value");
            return map;
        }
        redisService.saveKeyAndValue(key, value);

        map.put(key, value);
        return map;
    }

    @GetMapping("/value")
    public Map getStr(
            @RequestParam(value = "key", required = false, defaultValue = "") String key) {
        HashMap<String, String> map = new HashMap<>();
        if (StringUtils.isEmpty(key)) {
            map.put("error", "null key");
            return map;
        }
        String value= redisService.getByKey(key);
        map.put(key, value);
        return map;
    }


}
