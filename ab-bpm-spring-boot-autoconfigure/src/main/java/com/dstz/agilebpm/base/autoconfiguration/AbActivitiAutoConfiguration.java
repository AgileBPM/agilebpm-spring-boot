package com.dstz.agilebpm.base.autoconfiguration;

import com.alibaba.druid.pool.DruidDataSource;
import com.dstz.base.dao.baseinterceptor.SaveInterceptor;
import com.dstz.base.db.datasource.DynamicDataSource;
import com.github.pagehelper.PageInterceptor;
import com.github.pagehelper.QueryInterceptor;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * 数据源配置
 *
 * @author wacxhs
 * @date 2018-07-10
 */
@Configuration
public class AbActivitiAutoConfiguration {
	@Bean
	SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource) {
		SpringProcessEngineConfiguration processConfiguration = new SpringProcessEngineConfiguration();
		
		processConfiguration.setDataSource(dataSource);
		processConfiguration.setDatabaseType("mysql");// TODO 读配置
		processConfiguration.setDbIdentityUsed(false);
		processConfiguration.setHistory("audit");
		processConfiguration.setTransactionManager(transactionManager);
		processConfiguration.setDatabaseSchemaUpdate(databaseSchemaUpdate)
		processConfiguration.setJobExecutorActivate(jobExecutorActivate)
		processConfiguration.setIdGenerator(idGenerator)
		processConfiguration.setProcessDefinitionCache(processDefinitionCache)
		
		processConfiguration.setLabelFontName(labelFontName)
		processConfiguration.setActivityFontName(activityFontName)
		processConfiguration.setEventListeners(eventListeners);
		
		return processConfiguration;
	}
	
	
	
}
