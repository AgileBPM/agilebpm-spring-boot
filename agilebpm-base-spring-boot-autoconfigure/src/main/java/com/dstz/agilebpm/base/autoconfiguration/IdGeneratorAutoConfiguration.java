package com.dstz.agilebpm.base.autoconfiguration;

import com.dstz.base.db.api.IdGenerator;
import com.dstz.base.db.id.DefaultIdGenerator;
import com.dstz.base.db.id.UniqueIdUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * id生成器bean配置
 *
 * @author wdd
 * @date 2018-07-10
 */
@Configuration
@EnableConfigurationProperties(IdGeneratorProperties.class)
public class IdGeneratorAutoConfiguration {

    @Bean(name = "defaultIdGenerator")
    public IdGenerator idGenerator(JdbcTemplate jdbcTemplate, IdGeneratorProperties idGeneratorProperties){
        DefaultIdGenerator idGenerator = new DefaultIdGenerator();
        idGenerator.setJdbcTemplate(jdbcTemplate);
        idGenerator.setIdBase(idGeneratorProperties.getIdBase());
        idGenerator.setIncreaseBound(idGeneratorProperties.getIncreaseBound());
        idGenerator.setMachineName(idGeneratorProperties.getMachineName());
        return idGenerator;
    }

    @Bean
    public UniqueIdUtil uniqueIdUtil(IdGenerator idGenerator){
        UniqueIdUtil uniqueIdUtil = new UniqueIdUtil();
        uniqueIdUtil.setIdGenerator(idGenerator);
        return uniqueIdUtil;
    }
}
