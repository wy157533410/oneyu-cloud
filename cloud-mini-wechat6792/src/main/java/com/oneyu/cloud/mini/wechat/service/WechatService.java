package com.oneyu.cloud.mini.wechat.service;

/**
 * 
 * @author Oneyu
 *
 * @CreateDate  12:09:51 PM Jun 13, 2020
 */

public interface WechatService {
	
	
	public boolean login(String code, String appId, String module) ;
}
