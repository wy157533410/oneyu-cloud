package com.www.oneyu.xyz.service;

import org.springframework.stereotype.Component;

import com.oneyu.cloud.base.ResultObject;
import com.oneyu.cloud.base.dto.WxUserDTO;

/**
 * 
 * @author Oneyu
 *
 * @CreateDate  9:19:25 AM Jun 14, 2020
 */

@Component
public class WechatFeignFallbackService implements WechatFeignService{

	@Override
	public ResultObject<WxUserDTO> login(String code, String appId, String module) {
		return new ResultObject<WxUserDTO>(ResultObject.CODE_FALLBACK, "降级返回");
	}

	@Override
	public String feign() {
		return "feign降级返回";

	}

}
