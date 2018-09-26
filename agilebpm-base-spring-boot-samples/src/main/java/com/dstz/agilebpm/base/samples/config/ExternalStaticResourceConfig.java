package com.dstz.agilebpm.base.samples.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dstz.base.core.util.StringUtil;
/**
 * 方便开发部署前端资源（也可以使用node  npm run dev 来运行前端项目）
 * @author jeff
 */
@Configuration
public class ExternalStaticResourceConfig extends WebMvcConfigurerAdapter {
	
	@Value("${ab.external.resource.directory}")
	private String externalResourceDirectory;
	
	@Value("${ab.external.resource.path}")
	private String externalResourcePath;
	   
	   
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 	if(StringUtil.isEmpty(externalResourceDirectory) || StringUtil.isEmpty(externalResourcePath) )return;
		 	
	        registry.addResourceHandler(externalResourcePath +"/**").addResourceLocations("file:"+externalResourceDirectory);   
	        // registry.addResourceHandler("/bpm-explorer/**").addResourceLocations("file:C:\Users\miao_pc\git\agile-bpm-basic\bpm-explorer");   
	    }
	 //https://blog.csdn.net/u013194072/article/details/79014238
	  @Bean
	    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
	        TomcatEmbeddedServletContainerFactory embeddedServletContainerFactory = new TomcatEmbeddedServletContainerFactory();
	     //   embeddedServletContainerFactory.addContextCustomizers(tomcatContextCustomizers);
	        return embeddedServletContainerFactory;
	    }


}
