package com.dstz.agilebpm.base.autoconfiguration;

import java.util.Properties;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 数据源配置
 */
@Configuration
public class AbTransationAutoConfiguration {
	@Bean(name="abTransactionInterceptor")
	TransactionInterceptor transactionInterceptor(PlatformTransactionManager platformTransactionManager) {
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
		transactionInterceptor.setTransactionManager(platformTransactionManager);
		
		Properties transactionProp = new Properties();
		transactionProp.setProperty("get*", "PROPAGATION_REQUIRED,-Throwable,readOnly");
		transactionProp.setProperty("query*", "PROPAGATION_REQUIRED,-Throwable,readOnly");
		transactionProp.setProperty("select*", "PROPAGATION_REQUIRED,-Throwable,readOnly");
		transactionProp.setProperty("*", "PROPAGATION_REQUIRED");
		
		transactionInterceptor.setTransactionAttributes(transactionProp);
		
		return transactionInterceptor;
	}
	
	@Bean(name="abtansactionAutoProxy")
	BeanNameAutoProxyCreator tansactionAutoProxy() {
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		beanNameAutoProxyCreator.setProxyTargetClass(true);
		beanNameAutoProxyCreator.setBeanNames("com.dstz.*.*.manager.*.*(..)","com.dstz.*.manager.*.*(..)");
		beanNameAutoProxyCreator.setInterceptorNames("abTransactionInterceptor");
		
		return beanNameAutoProxyCreator;
	}
	
	
}
