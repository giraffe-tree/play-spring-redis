package com.github.giraffetree.playspringredis.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author GiraffeTree
 * @date 2018/7/17
 */
@Configuration
public class ThreadPoolConfig {

	@Bean
	public ExecutorService getThreadPool() {

		BasicThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
				.namingPattern("account-thread-%d").daemon(true).build();

		return new ThreadPoolExecutor(5, 20, 100L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024),
				basicThreadFactory, new ThreadPoolExecutor.AbortPolicy());

	}
}
