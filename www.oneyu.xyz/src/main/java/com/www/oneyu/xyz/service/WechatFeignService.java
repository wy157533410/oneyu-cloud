package com.www.oneyu.xyz.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oneyu.cloud.base.ResultObject;
import com.oneyu.cloud.base.dto.WxUserDTO;


@Component
@FeignClient(value="mini-wechat-service")
public interface WechatFeignService {
	
	
	@RequestMapping(value = "/login")
	ResultObject<WxUserDTO> login(String code, String appId, String module ) ;

	@GetMapping(value = "/feign")
	String feign() ;

}
