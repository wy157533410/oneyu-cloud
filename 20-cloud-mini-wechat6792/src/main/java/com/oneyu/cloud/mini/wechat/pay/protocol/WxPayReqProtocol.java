package com.oneyu.cloud.mini.wechat.pay.protocol;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.StringUtils;

import com.oneyu.cloud.mini.wechat.pay.annotation.ApiRequestField;
import com.oneyu.cloud.mini.wechat.pay.exception.WechatPayException;
import com.oneyu.cloud.mini.wechat.pay.util.SignUtils;
import com.oneyu.cloud.mini.wechat.pay.util.XmlUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付请求协议
 * @author Oneyu
 *
 * @CreateDate  2:40:28 PM Jun 24, 2020
 */
@Slf4j
public abstract class WxPayReqProtocol {

    /**
     * 协议是否需要证书，默认不需要
     * 
     * @return
     */
    public boolean requireCert(){
    	return false;
    }
	
	/**
	 * 协议对应的微信支付服务器的相对地址
	 * @return
	 */
	public abstract String action();
	
	/**
	 * 签名类型，默认为MD5，支持HMAC-SHA256和MD5
	 * @return
	 */
	String signTye() {
		return "MD5";
	}
	
	
	/**
	 * 最后要发给微信支付服务端的数据
	 * @return
	 */
	public String data(String signKey)throws WechatPayException {
		
		Map<String, String> map = this.convert();
		this.sign = SignUtils.md5(map, signKey);
		map.put("sign", this.sign);
		log.info(this.toString());
		this.validate();
		String data = XmlUtil.toXml(map);
		return data;
	}
	
	
	
    // 将支付参数 class转成map格式
    private Map<String, String> convert() throws WechatPayException {
        Map<String, String> data = new HashMap<String, String>();
        try {
            Class<? extends WxPayReqProtocol> clazz = getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                    continue;
                }
                String fieldName = pd.getName();
                Field field = FieldUtils.getField(clazz, fieldName, true);
                ApiRequestField requestField = field.getAnnotation(ApiRequestField.class);
                if (requestField == null) {
                    continue;
                }

                Object value = pd.getReadMethod().invoke(this);
                String strValue;
                if (value == null) {
                    continue;
                } else if (value instanceof String) {
                    if (StringUtils.isBlank((String) value)) {
                        continue;
                    }
                    strValue = (String) value;
                } else if (value instanceof Integer) {
                    strValue = ((Integer) value).toString();
                } else if (value instanceof Long) {
                    strValue = ((Long) value).toString();
                } else if (value instanceof Float) {
                    strValue = ((Float) value).toString();
                } else if (value instanceof Double) {
                    strValue = ((Double) value).toString();
                } else if (value instanceof Boolean) {
                    strValue = ((Boolean) value).toString();
                } else if (value instanceof Date) {
                    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                    strValue = format.format((Date) value);
                } else {
                    strValue = value.toString();
                }

                data.put(requestField.value(), strValue);
            }
        } catch (Exception e) {
            throw new WechatPayException(e);
        }
        return data;
    }
    
    
    

    // 校验 支付参数
       private void validate() throws WechatPayException {
           try {
               Class<? extends WxPayReqProtocol> clazz = getClass();
               BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
               PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
               for (PropertyDescriptor pd : pds) {
                   if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                       continue;
                   }
                   String fieldName = pd.getName();
                   Field field = FieldUtils.getField(clazz, fieldName, true);
                   ApiRequestField requestField = field.getAnnotation(ApiRequestField.class);
                   if (requestField == null) {
                       continue;
                   }

                   if (requestField.required()) {
                       Object value = pd.getReadMethod().invoke(this);

                       if (value == null) {
                           throw new WechatPayException(fieldName + " can not be empty");
                       }

                       if (value instanceof String) {
                           if (StringUtils.isBlank((String) value)) {
                               throw new WechatPayException(fieldName + " can not be empty");
                           }
                       }
                   }
               }
           } catch (Exception e) {
               throw new WechatPayException(e);
           }
       }
       
    
    
    // 公众账号ID
    @ApiRequestField("appid")
    protected String appId;

    // 随机字符串
    @ApiRequestField("nonce_str")
    protected String nonceStr;

    // 签名
    @ApiRequestField("sign")
    private String sign;

    // 微信支付 商户号
    @ApiRequestField("mch_id")
    protected String mchId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}


	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
    
}
