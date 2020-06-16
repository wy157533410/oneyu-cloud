package com.oneyu.cloud.mini.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oneyu.cloud.base.ResultObject;
import com.oneyu.cloud.base.dto.WxUserDTO;
import com.oneyu.cloud.mini.wechat.entity.WxUser;
import com.oneyu.cloud.mini.wechat.service.WechatService;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RefreshScope
@Slf4j
@RequestMapping("/wechat")
public class LoginController {

	@Value("${server.port}")
	private String SERVER_PORT;

	@Autowired
	WechatService wechatSrv;

//	@Value("${appkey}")
	private String appkey;

	@RequestMapping("/login")
	public ResultObject<WxUserDTO> login(WxUserDTO req) {
		log.info(this.getClass().getName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName()+ " "+req);


		return new ResultObject<WxUserDTO>(200, "", req);

	}

}

@Data
@NoArgsConstructor
class WxApp {
	private String appid;
	private String appkey;
	private String appsec;

}
