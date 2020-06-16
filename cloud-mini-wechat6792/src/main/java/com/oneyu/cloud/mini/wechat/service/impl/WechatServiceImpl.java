package com.oneyu.cloud.mini.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneyu.cloud.mini.wechat.dao.WxUserDAO;
import com.oneyu.cloud.mini.wechat.entity.WxUser;
import com.oneyu.cloud.mini.wechat.service.WechatService;

/**
 * 
 * @author Oneyu
 *
 * @CreateDate  12:07:41 PM Jun 13, 2020
 */
@Service
public class WechatServiceImpl implements WechatService{

	@Autowired
	WxUserDAO wxuserDAO;
	
	
	@Override
	public boolean login(String code, String appId, String module) {
		WxUser user = new WxUser();
		user.setOpenid(System.currentTimeMillis()+"");
		return wxuserDAO.create(user)>0;
	}

}
