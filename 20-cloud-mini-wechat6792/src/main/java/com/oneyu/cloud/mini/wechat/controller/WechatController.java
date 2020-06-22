package com.oneyu.cloud.mini.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oneyu.cloud.base.ResultObject;
import com.oneyu.cloud.base.dto.WxUserDTO;
import com.oneyu.cloud.mini.wechat.service.WechatService;
import com.oneyu.tools.SecurityTool;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 微信小程序服务端接口
 * @author Oneyu
 *
 * @CreateDate  10:40:36 AM Jun 22, 2020
 */


@RestController
@RefreshScope
@Slf4j
@RequestMapping("/wechat")
public class WechatController {

	@Value("${server.port}")
	private String SERVER_PORT;

	@Autowired
	WechatService wechatSrv;

	@Value("${wechat.push.token}")
	private String wxPushToken;

	/**
	 * 获取用户的openid
	 * @param params
	 * @return
	 */
	@RequestMapping("/getopenid")
	public ResultObject<String> getOpenid(String params) {
		JSONObject req = JSONUtil.parseObj(params);
		String res = wechatSrv.getOpenid(req.getStr("code"), req.getStr("appid"));
		if (res == null) {
			return new ResultObject<String>(ResultObject.CODE_GET_OPENID_ERROR, "获取openid失败");
		}
		return new ResultObject<String>(ResultObject.CODE_OK, "获取openid成功", res);
	}

	/**
	 * 授权登记
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/auth")
	public ResultObject<WxUserDTO> auth(String params) {
		WxUserDTO req = JSONUtil.toBean(params, WxUserDTO.class);
		int code = wechatSrv.auth(req) ? ResultObject.CODE_OK : ResultObject.CODE_GET_OPENID_ERROR;
		String msg = code == ResultObject.CODE_OK ? "授权登记成功" : "授权登记失败";
		return new ResultObject<WxUserDTO>(code, msg);
	}


	/**
	 * 验证接入微信小程序消息推送服务
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/push")
	public String push(String signature, String timestamp, String nonce, String echostr) {
		log.info(this.getClass().getName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("signature=" + signature + ", timestamp=" + timestamp + ", nonce=" + nonce + ", echostr=" + echostr);
		log.info("wxPushToken=" + wxPushToken);
		String mySignature = SecurityTool.wxPushSign(wxPushToken, timestamp, nonce);
		log.info("mySignature=" + mySignature);
		if (signature.compareToIgnoreCase(mySignature) == 0) {
			return echostr;
		}
		return "false";

	}

}
