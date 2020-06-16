package com.oneyu.cloud.mini.wechat.entity;

import lombok.Data;

/**
 * 微信用户
 * @author Oneyu
 *
 * @CreateDate  Jun 12, 2020
 */

@Data
public class WxUser implements java.io.Serializable{
	private static final long serialVersionUID = 6553859006063995446L;
	
	private Long id;
	private String openid;
}
