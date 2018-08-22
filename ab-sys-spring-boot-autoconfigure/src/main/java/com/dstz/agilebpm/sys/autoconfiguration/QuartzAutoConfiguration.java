package com.dstz.agilebpm.sys.autoconfiguration;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.quartz.simpl.SimpleJobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.dstz.sys.scheduler.QuartzManagerService;

@Configuration
public class QuartzAutoConfiguration {
	@Resource(name="dataSourceDefault")
	DataSource dataSource;
	
	@Bean
	QuartzManagerService quartzManagerService() {
		return new QuartzManagerService();
	}
	
	// TODO 配置化
	@Bean(name="scheduler")
	SchedulerFactoryBean scheduler() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		scheduler.setAutoStartup(false);
		scheduler.setWaitForJobsToCompleteOnShutdown(false);
		scheduler.setDataSource(dataSource);
		
		scheduler.setOverwriteExistingJobs(true);
		scheduler.setJobFactory(new SimpleJobFactory());
		
		Properties properties = new Properties();
		properties.setProperty("org.quartz.scheduler.instanceName", "ClusteredScheduler");
		properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
		properties.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");
		
		properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		properties.setProperty("org.quartz.threadPool.threadCount", "5");
		properties.setProperty("org.quartz.threadPool.threadPriority","5");
		
		properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		properties.setProperty("org.quartz.jobStore.misfireThreshold","60000");
		properties.setProperty("org.quartz.jobStore.useProperties", "false");
		properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
		
		properties.setProperty("org.quartz.jobStore.isClustered", "true");
		properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "20000");
		
		properties.setProperty("org.quartz.scheduler.classLoadHelper.class", "org.quartz.simpl.CascadingClassLoadHelper");
		properties.setProperty("org.quartz.jobStore.lockHandler.class", "org.quartz.impl.jdbcjobstore.UpdateLockRowSemaphore");
		properties.setProperty("org.quartz.jobStore.lockHandler.updateLockRowSQL", "UPDATE QRTZ_LOCKS SET LOCK_NAME = LOCK_NAME WHERE LOCK_NAME = ?");
		
		scheduler.setQuartzProperties(properties);
		
		return scheduler;
		
	}
	
	
}
