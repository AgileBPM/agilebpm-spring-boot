package com.dstz.agilebpm.sys.autoconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.dstz.sys.simplemq.RedisConsumer;
import com.dstz.sys.util.EmailUtil;

import cn.hutool.extra.mail.MailAccount;

/**
 * 缓存相关配置
 *
 * @author jeff
 * @date 2018-8-12 22:36:19
 */
@Configuration
@EnableConfigurationProperties(MQMailConfigProperties.class)
public class SimpleMQAutoConfiguration {
	@Autowired
	MQMailConfigProperties mQMailConfigProperties;
	@Bean
	public JdkSerializationRedisSerializer abSerialization() {
		return new JdkSerializationRedisSerializer();
	}
	
	@Bean
	public StringRedisSerializer abStringserialization() {
		return new StringRedisSerializer();
	}
	
	@Bean
	public RedisConsumer abMessageListener() {
		return new RedisConsumer();
	}
	
	/**
	 * 订阅者适配器
	 * @param abStringserialization 上面的注入实现类
	 * @param abSerialization
	 * @param abMessageListener
	 * @return
	 */
	@Bean
	public MessageListenerAdapter abMessageListenerAdapter(StringRedisSerializer abStringserialization,JdkSerializationRedisSerializer abSerialization
			,RedisConsumer abMessageListener) {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
		
		messageListenerAdapter.setSerializer(abSerialization);
		messageListenerAdapter.setStringSerializer(abStringserialization);
		messageListenerAdapter.setDefaultListenerMethod("receiveMessage");
		messageListenerAdapter.setDelegate(abMessageListener);
		
		setEmailConfiguration();
		
		return messageListenerAdapter;
	}

	private void setEmailConfiguration() {
		MailAccount account = new MailAccount();
		
		account.setHost(mQMailConfigProperties.getSendHost());
		account.setPort(mQMailConfigProperties.getSendPort());
		account.setFrom(mQMailConfigProperties.getMailAddress());
		account.setUser(mQMailConfigProperties.getNickName());
		account.setPass(mQMailConfigProperties.getPassword());
		account.setSslEnable(mQMailConfigProperties.isSSL());
		
		EmailUtil.setAccount(account);
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(JedisConnectionFactory abRedisConnectionFactory,StringRedisSerializer abStringserialization
			,MessageListenerAdapter abMessageListenerAdapter) {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		
		redisMessageListenerContainer.setConnectionFactory(abRedisConnectionFactory);
		redisMessageListenerContainer.setTopicSerializer(abStringserialization);
		
		redisMessageListenerContainer.addMessageListener(abMessageListenerAdapter, new PatternTopic("message"));
		
		return redisMessageListenerContainer;
	}
	 
}
