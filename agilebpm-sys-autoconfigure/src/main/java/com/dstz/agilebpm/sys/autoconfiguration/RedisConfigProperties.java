package com.dstz.agilebpm.sys.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis 配置
 *
 * @author wacxhs
 * @date 2018-07-10
 */
@ConfigurationProperties(prefix = "ab.redis")
public class RedisConfigProperties {
	/**
	 * 系统缓存是否使用 Redis:  false 则使用 系统内存  com.dstz.base.core.cache.impl.MemoryCache ConcurrentHashMap
	 */
	private boolean useRedisCache = false;
	private String host = "";
	private int port = 6379;
	private String password = "";
	private int database = 9;
	
	private int maxIdle = 300;
	private int maxTotal = 600;
	private long maxWaitMillis = 2000L;
	private boolean testOnBorrow = true;
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	 
	public int getDatabase() {
		return database;
	}
	public void setDatabase(int database) {
		this.database = database;
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
	 
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}
	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	public boolean isUseRedisCache() {
		return useRedisCache;
	}
	public void setUseRedisCache(boolean useRedisCache) {
		this.useRedisCache = useRedisCache;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}
