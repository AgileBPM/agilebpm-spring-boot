package com.dstz.agilebpm.base.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wacxhs
 * @date 2018-07-11
 */
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceExtraProperties {

    /**
     * 数据源类别
     */
    private String dbType;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
