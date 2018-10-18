package com.dstz.agilebpm.sys.autoconfiguration;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.cache.impl.MemoryCache;
import com.dstz.sys.redis.RedisCache;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 缓存相关配置
 *
 * @author jeff
 * @date 2018-8-12 22:36:19
 */
@Configuration
@EnableConfigurationProperties(RedisConfigProperties.class)
public class CacheAutoConfiguration {
	
	@Bean
	public ICache iCache(RedisConfigProperties redisConfig) {
		if (!redisConfig.isUseRedisCache()) {
			return new MemoryCache<>();
		}
		return new RedisCache<>();
	}

	@Bean(name="abJedisPoolConfig")
	public JedisPoolConfig JedisPoolConfig(RedisConfigProperties redisConfig) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisConfig.getMaxIdle());
		poolConfig.setMaxTotal(redisConfig.getMaxTotal());
		poolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
		poolConfig.setTestOnBorrow(redisConfig.isTestOnBorrow());
		return poolConfig;
	}
	/**
	 * TODO 更新
	 * @param redisConfig
	 * @return
	 */
	@Bean(name="abRedisConnectionFactory")
	public JedisConnectionFactory abRedisConnectionFactory(RedisConfigProperties redisConfig,JedisPoolConfig abPoolConfig) {
		JedisConnectionFactory jedisConnection = new JedisConnectionFactory();
		jedisConnection.setHostName(redisConfig.getHost());
		jedisConnection.setPort(redisConfig.getPort());
		jedisConnection.setPassword(redisConfig.getPassword());
		jedisConnection.setDatabase(redisConfig.getDatabase());
		jedisConnection.setPoolConfig(abPoolConfig);
		return jedisConnection;
	}
	
	@Bean 
	public RedisTemplate redisTemplate(RedisConfigProperties redisConfig,JedisConnectionFactory abRedisConnectionFactory) {
		RedisTemplate template = new RedisTemplate();
		template.setConnectionFactory(abRedisConnectionFactory);
		return template;
	}
	
	@Bean(name="taskExecutor")
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(1000);
		taskExecutor.setKeepAliveSeconds(300);
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return taskExecutor;
	}
	
}
