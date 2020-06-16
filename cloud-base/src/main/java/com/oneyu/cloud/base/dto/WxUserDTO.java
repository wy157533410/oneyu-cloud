package com.oneyu.cloud.base.dto;

import lombok.Data;

/**
 * 微信小程序 mini-wechat-service
 * 微服务之间的数据传递对象
 * 
 * @author Oneyu
 *
 * @CreateDate  5:50:50 PM Jun 14, 2020
 */

@Data
public class WxUserDTO implements java.io.Serializable{
	private static final long serialVersionUID = -392635536829165405L;
	private String code;
	private String appid;
	
	
	private String openid;
	
}
