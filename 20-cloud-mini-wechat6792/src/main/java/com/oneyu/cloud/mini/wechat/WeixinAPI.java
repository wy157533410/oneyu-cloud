package com.oneyu.cloud.mini.wechat;

import org.springframework.stereotype.Component;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 支持多应用
 * 
 * @author wanyu
 *
 */

@Component
@Slf4j
public class WeixinAPI {
	
	/**
	 * 
	 * @param appId
	 * @param code
	 * @param secKey
	 * @return
	 */
	public String getOpenId(String appId, String code, String secKey) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secKey + "&js_code="
				+ code + "&grant_type=authorization_code";

		String res = HttpUtil.get(url);
		log.info("url="+url+",res="+res);
		return JSONUtil.parseObj(res).getStr("openid");
	}

	/**
	 * 获取小程序 access_token
	 * 
	 * @return
	 */
	public String getAccessToken(String appId, String secret) {

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret="
				+ secret;
		String res = HttpUtil.get(url);
		log.info("url="+url+",res="+res);
		return JSONUtil.parseObj(res).getStr("access_token");

	}



	public static void main(String[] args) {


	}
}
