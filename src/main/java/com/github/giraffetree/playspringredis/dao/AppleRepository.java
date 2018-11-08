package com.github.giraffetree.playspringredis.dao;

import com.github.giraffetree.playspringredis.entity.Apple;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author GiraffeTree
 * @date 2018/11/8
 */
public interface AppleRepository extends JpaRepository<Apple, Long> {

}
