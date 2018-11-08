package com.github.giraffetree.playspringredis.service;

import com.github.giraffetree.playspringredis.dao.AppleRepository;
import com.github.giraffetree.playspringredis.entity.Apple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author GiraffeTree
 * @date 2018/11/8
 */
@Service
public class CacheService {

    private AppleRepository appleRepository;

    @Autowired
    public CacheService(AppleRepository appleRepository) {
        this.appleRepository = appleRepository;
    }

//    @Cacheable(value = "apple", key = "'apple:id:apple_'+#id")
    public Apple findById(Long id) {
        return appleRepository.findById(id).orElse(null);
    }

//    @CacheEvict(value = "apple", key = "'apple:id:apple_'+#id")
    public void dirtyAppleById(Long id) {
    }

}
