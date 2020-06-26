package com.oneyu.cloud.mini.wechat.pay.protocol;

import java.util.Map;

import com.oneyu.cloud.base.pay.enums.PayErrorCode;
import com.oneyu.cloud.base.pay.enums.PayResultCode;
import com.oneyu.cloud.mini.wechat.pay.annotation.ApiResponseField;
import com.oneyu.cloud.mini.wechat.pay.util.SignUtils;

/**
 * returncode=true的返回对象
 * @author Oneyu
 *
 * @CreateDate  11:08:00 AM Jun 26, 2020
 */
public class WxPayReturnSuccess extends WxPayResponse {

	@Override
	boolean doAfterReturnSuccess(Map<String, String> data, String apiKey) {
		boolean bResult = this.isSuccess();
		if (!bResult) {
//    		失败，后面不再处理
			return false;
		}
		String strSignContent = SignUtils.getSignContent(data, true);
		bResult = SignUtils.checkMd5Sign(strSignContent, this.sign, apiKey);
		if (!bResult) {
			//本地校验失败
			this.errCode = PayErrorCode.CONTENT_TAMPERED.getValue();
			this.errCodeDes = PayErrorCode.CONTENT_TAMPERED.getDesc();
			return false;
		}
		return true;
	}

	@Override
	boolean doAfterSuccess(String apiKey) {
		return true;
	}

	// 判断操作是否成功，即通信结果和业务结果均为 SUCCESS时候表示操作成功
	public boolean isSuccess() {
		return PayResultCode.SUCCESS.getValue().equalsIgnoreCase(resultCode);
	}

	// 公众账号ID
	@ApiResponseField("appid")
	private String appId;

	// 微信支付商户号
	@ApiResponseField("mch_id")
	private String mchId;
	
    // 设备号
    @ApiResponseField("device_info")
    private String deviceInfo;

    // 随机字符串
    @ApiResponseField("nonce_str")
    private String nonceStr;

	// 签名
	@ApiResponseField("sign")
	private String sign;

	// 业务结果 SUCCESS/FAIL !!!!!!!!!!!!!!!
	@ApiResponseField("result_code")
	protected String resultCode;

	// 错误代码
	@ApiResponseField("err_code")
	private String errCode;

	// 错误代码描述
	@ApiResponseField("err_code_des")
	private String errCodeDes;



}
