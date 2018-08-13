package com.dstz.agilebpm.sys.autoconfiguration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.dstz.sys.email.MailUtil;
import com.dstz.sys.email.model.MailSetting;
import com.dstz.sys.simplemq.RedisConsumer;

/**
 * 缓存相关配置
 *
 * @author jeff
 * @date 2018-8-12 22:36:19
 */
@Configuration
@EnableConfigurationProperties(MQMailConfigProperties.class)
public class SimpleMQAutoConfiguration {
	
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
		
		return messageListenerAdapter;
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
	 
	@Bean
	MailSetting abMailSeting(MQMailConfigProperties mQMailConfigProperties ) {
		MailSetting mailSetting = new MailSetting();
		mailSetting.setSendHost(mQMailConfigProperties.getSendHost());
		mailSetting.setSendPort(String.valueOf(mQMailConfigProperties.getSendPort()));
		mailSetting.setSSL(mQMailConfigProperties.isSSL());
		mailSetting.setProtocal("smtp");
		mailSetting.setValidate(true);
		mailSetting.setNickName(mQMailConfigProperties.getNickName());
		mailSetting.setMailAddress(mQMailConfigProperties.getMailAddress());
		mailSetting.setPassword(mQMailConfigProperties.getPassword());
		return mailSetting;
	}
	
	@Bean
	MailUtil mailSender(MailSetting abMailSeting ) {
		return new MailUtil(abMailSeting);
	}
	
}
