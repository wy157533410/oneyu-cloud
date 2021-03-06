package com.oneyu.cloud.mini.wechat.pay.protocol;

import com.oneyu.cloud.mini.wechat.pay.annotation.ApiResponseField;

public class UnifiedOrderResponse extends WxPayReturnSuccess {








    // 以下字段 在return_code 和result_code都为SUCCESS的时候有返回
    // 交易类型 TradeType
    @ApiResponseField("trade_type")
    private String tradeType;

    // 预支付交易会话标识  微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
    @ApiResponseField("prepay_id")
    private String prepayId;

    // trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
    @ApiResponseField("code_url")
    private String codeUrl;

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
    
    
}
