package com.oneyu.cloud.base;

/**
 * 微服务枚举
 * 
 * @author Oneyu
 *
 * @CreateDate  6:13:12 PM Jun 14, 2020
 */


public enum CloudServiceEnum {
	
	MINI_WECHAT(7601, "mini-wehchat-service", ""),
	MINI_DINGDING(7602,"mini-dingding-service", "");
	
	private String name ;
	private Integer port;
	private String desc ;
	
	private CloudServiceEnum(Integer port, String name, String desc) {
		this.name = name;
		this.desc = desc;
		this.port = port;
	}
	
	public String getName() {
		return name;
	}

	public Integer getPort() {
		return port;
	}

	public String getDesc() {
		return desc;
	}
	
	public boolean isSupport(String name) {
		for(CloudServiceEnum ele : CloudServiceEnum.values()) {
			if(ele.name.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
}
