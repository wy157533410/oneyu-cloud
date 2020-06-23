package com.oneyu.cloud.mini.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.oneyu.cloud.base.dto.WxMiniPayOrderDTO;
import com.oneyu.cloud.base.dto.WxUserDTO;
import com.oneyu.cloud.mini.wechat.WeixinAPI;
import com.oneyu.cloud.mini.wechat.dao.WxUserDAO;
import com.oneyu.cloud.mini.wechat.entity.WxUser;
import com.oneyu.cloud.mini.wechat.pay.client.WechatPayClient;
import com.oneyu.cloud.mini.wechat.pay.exception.WechatPayException;
import com.oneyu.cloud.mini.wechat.pay.protocol.UnifiedOrder;
import com.oneyu.cloud.mini.wechat.service.WechatService;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 同一个部署后台，支持多个微信小程序对接
 * 
 * @author Oneyu
 *
 * @CreateDate 12:07:41 PM Jun 13, 2020
 */
@Service
@Slf4j
@RefreshScope
public class WechatServiceImpl implements WechatService {
	private static String SEPARATOR_ITEM = ",";
	private static String SEPARATOR_LINE = ";";

	@Autowired
	WxUserDAO wxuserDAO;

	@Autowired
	WeixinAPI wxAPI;

	@Value("${wechat.pay.mchhost}")
	String mchhost;
	
	@Value("${wechat.pay.notifyurl}")
	String notifyurl;
	

	// 20200623搞不定yaml的[{}]方式，用，；来处理
	@Value("${wechat.apps}")
	String apps;// appid,sec,pushtoken,mchid,apikey

	/**
	 * 授权使用记录
	 */
	@Override
	public boolean auth(WxUserDTO req) {
		log.info("apps=" + apps);
		String[] loop = apps.split(SEPARATOR_LINE);
		boolean include = false;
		for (int i = 0; i < loop.length; i++) {
			if (loop[i].length() > 3 && loop[i].startsWith(req.getAppid())) {
				include = true;
				break;
			}
		}

		if (!include) {
			log.error("不支持的AppID：" + req.getAppid());
			return false;
		}
		WxUser entity = new WxUser();
		entity.fromMiniProgram(req);
		return wxuserDAO.insert(entity) > 0;

	}

	@Override
	public WxUserDTO login(WxUserDTO req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOpenid(String code, String appid) {
		String seckey = this.getConfigValue(appid, 1);
		if (seckey == null) {
			log.error("取不到seckey，AppID：" + appid);
			return null;
		}
		return wxAPI.getOpenId(appid, code, seckey);
	}
	
	/**
	 * 获取appid对应seckey
	 * @return
	 */
	private String getConfigValue(String appid, int idx) {
		String seckey = null;
		String[] loop = apps.split(SEPARATOR_LINE);
		for (int i = 0; i < loop.length; i++) {
			if (loop[i].length() > 3 && loop[i].startsWith(appid)) {
				String[] one = loop[i].split(SEPARATOR_ITEM);
				if (one.length > 2) {
					seckey = one[idx];
					break;
				}
			}
		}
		return seckey;
	}
	
	

	/**
	 * 创建支付订单
	 */
	@Override
	public String createOrder(WxMiniPayOrderDTO dto) {
		String appId = dto.getAppid();
		String mchId = getConfigValue(appId, 3);
		String apiKey = getConfigValue(appId, 4);
		
		UnifiedOrder p = new UnifiedOrder();
		p.setBody(dto.getSubject());
		p.setTotalFee(dto.getTotalfee());
		p.setTradeType(dto.getTradetype());
		p.setSpbillCreateIp(mchhost);
		p.setNotifyUrl(notifyurl);
		p.setOutTradeNo(IdUtil.createSnowflake(1, 1).nextIdStr());
		
	
	
		WechatPayClient client = new WechatPayClient(appId,mchId, apiKey);
		try {
			client.send(p);
		} catch (WechatPayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

}
