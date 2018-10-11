package com.dstz.agilebpm.sys.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * mq 中邮箱的配置
 * @author jeff
 */
@ConfigurationProperties(prefix = "ab.mail")
public class MQMailConfigProperties {
	private String sendHost = "";
	private int  sendPort = 465;
	private boolean SSL = true;
	private String nickName = "";
	private String mailAddress = "";
	private String password = "";
	
	public String getSendHost() {
		return sendHost;
	}
	public void setSendHost(String sendHost) {
		this.sendHost = sendHost;
	}
	public int getSendPort() {
		return sendPort;
	}
	public void setSendPort(int sendPort) {
		this.sendPort = sendPort;
	}
	public boolean isSSL() {
		return SSL;
	}
	public void setSSL(boolean sSL) {
		SSL = sSL;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
