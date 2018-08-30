package com.dstz.agilebpm.base.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ID生成器配置
 *
 * @author wacxhs
 * @date 2018-07-10
 */
@ConfigurationProperties(prefix = "agile-bpm.id-generator")
public class IdGeneratorProperties {

    private long machine = 1;

	private byte machineBits = 3;

    private byte sequenceBits = 15;

    private byte timeSequence = 45;
  
    public long getMachine() {
		return machine;
	}

	public void setMachine(long machine) {
		this.machine = machine;
	}

	public byte getMachineBits() {
		return machineBits;
	}

	public void setMachineBits(byte machineBits) {
		this.machineBits = machineBits;
	}

	public byte getSequenceBits() {
		return sequenceBits;
	}

	public void setSequenceBits(byte sequenceBits) {
		this.sequenceBits = sequenceBits;
	}

	public byte getTimeSequence() {
		return timeSequence;
	}

	public void setTimeSequence(byte timeSequence) {
		this.timeSequence = timeSequence;
	}
}
