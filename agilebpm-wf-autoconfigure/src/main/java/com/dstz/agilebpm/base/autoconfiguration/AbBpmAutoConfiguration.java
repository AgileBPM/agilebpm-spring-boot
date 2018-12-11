package com.dstz.agilebpm.base.autoconfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.transaction.PlatformTransactionManager;

import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.id.IdGenerator;
import com.dstz.base.db.transaction.AbDataSourceTransactionManager;
import com.dstz.bpm.act.cache.ActivitiDefCache;
import com.dstz.bpm.act.id.ActivitiIdGenerator;
import com.dstz.bpm.act.listener.GlobalEventListener;
import com.dstz.bpm.api.engine.service.BpmEngineService;
import com.dstz.bpm.api.exception.BpmStatusCode;
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
	SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource,PlatformTransactionManager transactionManager,
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
	
	
	@Bean
	GlobalEventListener globalEventListener() {
		GlobalEventListener globalEventListener = new GlobalEventListener();
		
		Map<String, String> map = new HashMap<>();
		map.put("TASK_CREATED", "taskCreateListener");
		map.put("TASK_COMPLETED", "taskCompleteListener");
		map.put("PROCESS_STARTED", "instanceStartEventListener");
		map.put("PROCESS_COMPLETED", "instanceEndEventListener");
		map.put("ACTIVITY_STARTED", "activityStartedListener");
		map.put("ACTIVITY_COMPLETED", "activityComplatedListener");
		
		map.put("SEQUENCEFLOW_TAKEN", "sequenceFlowTakenListener");
		
		globalEventListener.setHandlers(map);
		
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
	BpmEngineService checkUserOrgEnginService(UserService userService) {
		BpmEngineService bpmEnginService = new BpmEngineService();
		if(userService == null) { 
			throw new BusinessException("BPM 模块 依赖 ORG 服务，请检查组织服务是否注入成功！",BpmStatusCode.SYSTEM_ERROR);
		}
		
		bpmEnginService.setUserService(userService);
		return bpmEnginService;
	}
}
