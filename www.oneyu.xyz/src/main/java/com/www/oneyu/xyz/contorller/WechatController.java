package com.www.oneyu.xyz.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.oneyu.cloud.base.CloudServiceEnum;
import com.oneyu.cloud.base.ResultObject;
import com.oneyu.cloud.base.dto.WxUserDTO;
import com.www.oneyu.xyz.service.WechatFeignService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WechatController {
	
	@Autowired
	WechatFeignService wechatService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/login/wechat")
	public ResultObject<WxUserDTO> loginMiniWechat(String code, String appId, String module) {
		log.info("loginMiniWechat");
		log.info("code="+code+",appId="+appId+",module="+module);
//		return (ResultObject<WxUserDTO>)restTemplate.getForObject("http"+ CloudServiceEnum.MINI_WECHAT.getName()+"/login", ResultObject.class);
		
		return (ResultObject<WxUserDTO>)restTemplate.getForObject("http://cloud-gateway/wechat/login", ResultObject.class);
	}
	
	
	@RequestMapping(value="/feign/wechat")
	public String feignWechat() {
		log.info("feignWechat");
	
		return wechatService.feign();
		
	}

}
