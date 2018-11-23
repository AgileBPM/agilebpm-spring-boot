package com.dstz.agilebpm.base.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wacxhs
 * @date 2018-07-11
 */
@ComponentScan("com.dstz.*")
@SpringBootApplication
public class SamplesApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(SamplesApplication.class, args);
    }
    
}
