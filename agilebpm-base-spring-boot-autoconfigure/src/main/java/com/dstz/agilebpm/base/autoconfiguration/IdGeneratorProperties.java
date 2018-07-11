package com.dstz.agilebpm.base.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ID生成器配置
 *
 * @author wdd
 * @date 2018-07-10
 */
@ConfigurationProperties(prefix = "agile-bpm.id-generator")
public class IdGeneratorProperties {

    /**
     * 增长段值
     */
    private Integer increaseBound = 1000;

    /**
     * 机器名称 多台物理机器集群部署时，需要唯一区分
     */
    private String machineName = "1";

    /**
     * ID的基准长度
     */
    private Long idBase = 10000000000000L;

    public Integer getIncreaseBound() {
        return increaseBound;
    }

    public void setIncreaseBound(Integer increaseBound) {
        this.increaseBound = increaseBound;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Long getIdBase() {
        return idBase;
    }

    public void setIdBase(Long idBase) {
        this.idBase = idBase;
    }
}
