package com.oneyu.cloud.mini.wechat.service;

import com.oneyu.cloud.base.dto.WxMiniPayOrderDTO;
import com.oneyu.cloud.base.dto.WxUserDTO;

/**
 * 
 * @author Oneyu
 *
 * @CreateDate  12:09:51 PM Jun 13, 2020
 */

public interface WechatService {
	/**
	 * 获取OpenID
	 * @param code
	 * @param appid
	 * @return
	 */
	String getOpenid(String code, String appid);
	
	/**
	 * 授权后在后台添加一条记录
	 * @param req
	 * @return
	 */
	boolean auth(WxUserDTO dto);
	WxUserDTO login(WxUserDTO req);

	/**
	 * 创建支付订单
	 * @return 返回预支付订单号
	 */
	String createOrder(WxMiniPayOrderDTO dto);
}
