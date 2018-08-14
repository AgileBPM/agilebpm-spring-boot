package com.dstz.agilebpm.base.autoconfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.db.api.IdGenerator;
import com.dstz.bpm.act.cache.ActivitiDefCache;
import com.dstz.bpm.act.id.ActivitiIdGenerator;
import com.dstz.bpm.act.listener.ActEventListener;
import com.dstz.bpm.act.listener.GlobalEventListener;
import com.dstz.bpm.api.exception.BpmStatusCode;
import com.dstz.bpm.engine.listener.ActivityComplatedListener;
import com.dstz.bpm.engine.listener.ActivityStartedListener;
import com.dstz.bpm.engine.listener.InstanceEndEventListener;
import com.dstz.bpm.engine.listener.InstanceStartEventListener;
import com.dstz.bpm.engine.listener.TaskCompleteListener;
import com.dstz.bpm.engine.listener.TaskCreateListener;
import com.dstz.org.api.service.UserService;

/**
 * activiti 整合配置
 * 
 * @author Jeff
 * @date 2018-8-14 15:42:44
 */
@Configuration
public class AbBpmAutoConfiguration {
	@Autowired DataSourceExtraProperties dataSourceExtraProperties;
	
	@Bean
	ProcessEngineFactoryBean processEngineFactory(SpringProcessEngineConfiguration processEngineConfiguration){
		ProcessEngineFactoryBean processEngine = new ProcessEngineFactoryBean();
		processEngine.setProcessEngineConfiguration(processEngineConfiguration);
		return processEngine;
	}
	
	@Bean(name="repositoryService")
	RepositoryService processEngine(ProcessEngineFactoryBean processEngineFactory) throws Exception{
		ProcessEngine processEngine = processEngineFactory.getObject();
		 return processEngine.getRepositoryService();
	}
	
	@Bean(name="runtimeService")
	RuntimeService runtimeService(ProcessEngineFactoryBean processEngineFactory) throws Exception{
		ProcessEngine processEngine = processEngineFactory.getObject();
		 return processEngine.getRuntimeService();
	}
	
	@Bean(name="taskService")
	TaskService taskService(ProcessEngineFactoryBean processEngineFactory) throws Exception{
		ProcessEngine processEngine = processEngineFactory.getObject();
		 return processEngine.getTaskService();
	}
	
	@Bean(name="historyService")
	HistoryService historyService(ProcessEngineFactoryBean processEngineFactory) throws Exception{
		ProcessEngine processEngine = processEngineFactory.getObject();
		 return processEngine.getHistoryService();
	}
	
	@Bean(name="managementService")
	ManagementService managementService(ProcessEngineFactoryBean processEngineFactory) throws Exception{
		ProcessEngine processEngine = processEngineFactory.getObject();
		 return processEngine.getManagementService();
	}
	
	@Bean
	SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource,DataSourceTransactionManager transactionManager,
			ActivitiDefCache activitiDefCache,GlobalEventListener globalEventListener) {
		SpringProcessEngineConfiguration processConfiguration = new SpringProcessEngineConfiguration();
		
		processConfiguration.setDataSource(dataSource);
		processConfiguration.setDatabaseType(dataSourceExtraProperties.getDbType());
		processConfiguration.setDbIdentityUsed(false);
		processConfiguration.setHistory("audit");
		processConfiguration.setTransactionManager(transactionManager);
		processConfiguration.setDatabaseSchemaUpdate("true");
		processConfiguration.setJobExecutorActivate(false);
		processConfiguration.setIdGenerator(activitiIdGenerator());
		processConfiguration.setProcessDefinitionCache(activitiDefCache);
		
		processConfiguration.setLabelFontName("宋体");
		processConfiguration.setActivityFontName("宋体");
		processConfiguration.setEventListeners(Collections.singletonList(globalEventListener));
		
		return processConfiguration;
	}
	
	
	@Resource
	TaskCreateListener taskCreateListener;
	@Resource
	TaskCompleteListener taskCompleteListener;
	@Resource
	InstanceEndEventListener instanceEndEventListener;
	@Resource
	InstanceStartEventListener instanceStartEventListener;
	@Resource
	ActivityStartedListener activityStartedListener;
	@Resource
	ActivityComplatedListener activityComplatedListener;
	@Bean
	GlobalEventListener globalEventListener() {
		GlobalEventListener globalEventListener = new GlobalEventListener();
		Map<String, ActEventListener> map = new HashMap<>();
		map.put("TASK_CREATED", taskCreateListener);
		map.put("TASK_COMPLETED", taskCompleteListener);
		map.put("PROCESS_STARTED", instanceStartEventListener);
		map.put("PROCESS_COMPLETED", instanceEndEventListener);
		map.put("ACTIVITY_STARTED", activityStartedListener);
		map.put("ACTIVITY_COMPLETED", activityComplatedListener);
		
		return globalEventListener;
	}
	
	
	// id 生成器
	@Autowired
	IdGenerator idGenerator;
	ActivitiIdGenerator activitiIdGenerator() {
		ActivitiIdGenerator activitiIdGenerator = new ActivitiIdGenerator();
		activitiIdGenerator.setIdGenerator(idGenerator);
		return activitiIdGenerator;
	}
	
	
	@Bean
	void checkUserOrgEnginService(UserService userService) {
		if(userService == null) { 
			throw new BusinessException("BPM 模块 依赖 ORG 服务，请检查！",BpmStatusCode.SYSTEM_ERROR);
		}
	}
}
