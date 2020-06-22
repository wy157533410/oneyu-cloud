package com.oneyu.cloud.mini.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.oneyu.cloud.base.dto.WxUserDTO;
import com.oneyu.cloud.mini.wechat.WeixinAPI;
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
	
	@Autowired
	WeixinAPI wxAPI;
	


	@Value("${wechat.appid}")
	String appid;


	@Value("${wechat.seckey}")
	String seckey;
	
	
	/**
	 * 授权使用记录
	 */
	@Override
	public boolean auth(WxUserDTO req) {
		if(req.getAppid().compareTo(appid)!=0) {
			return false;
		}
		WxUser entity = new WxUser();
		entity.fromMiniProgram(req);
		return wxuserDAO.insert(entity)>0;
		
		
		
	}


	@Override
	public WxUserDTO login(WxUserDTO req) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getOpenid(String code, String appid) {		
		return wxAPI.getOpenId(appid, code, seckey);
	}

}
