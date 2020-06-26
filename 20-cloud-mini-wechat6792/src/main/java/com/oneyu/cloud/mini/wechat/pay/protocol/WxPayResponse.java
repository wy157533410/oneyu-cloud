package com.oneyu.cloud.mini.wechat.pay.protocol;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.StringUtils;

import com.oneyu.cloud.base.pay.enums.PayReturnCode;
import com.oneyu.cloud.base.pay.enums.WechatPayTradeStatus;
import com.oneyu.cloud.mini.wechat.pay.annotation.ApiResponseField;
import com.oneyu.cloud.mini.wechat.pay.exception.WechatPayException;
import com.oneyu.cloud.mini.wechat.pay.util.XmlUtil;


public abstract class WxPayResponse implements WxPayProtocol{
	
	/**
	 * 字段在return_code为SUCCESS后处理
	 * @return
	 */
	abstract boolean doAfterReturnSuccess(Map<String, String> data, String apiKey);
	
	/**
	 * 业务成功后处理
	 * @param apiKey
	 * @return
	 */
	abstract boolean doAfterSuccess(String apiKey);
	
	


    //  return_code, return_msg 是所有返回结果的 基础。

    // SUCCESS/FAIL      此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    @ApiResponseField("return_code")
    protected String returnCode;

    // 返回信息，如非空，为错误原因 如，签名失败 ,参数格式校验错误
    @ApiResponseField("return_msg")
    protected String returnMsg;
    
    



    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
    


    /**
     * 通信标识,是否成功
     * @return
     */
    public boolean isNetworkOK() {
    	return PayReturnCode.SUCCESS.getValue().equalsIgnoreCase(returnCode);
    }
    

    
    public boolean parse(String xmlStr, String apiKey)  throws WechatPayException {
    	Map<String, String> data = XmlUtil.parseXml(xmlStr);
    	this.setValueByMap(data);
    	boolean bResult = this.isNetworkOK();
    	if(!bResult) {
//    		失败，后面不再处理
    		return false;
    	}
    	
    	bResult = this.doAfterReturnSuccess(data, apiKey);
    	if(!bResult) {
    		//本地鉴权失败
    		return false;
    	}
    	
    	return this.doAfterSuccess(apiKey);

    }
    

    /**
     * 通过hashmap给对象赋值
     * @param data
     */
    private void setValueByMap(Map<String, String> data)  throws WechatPayException {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : pds) {
                Method writeMethod = pd.getWriteMethod();
                if (writeMethod == null) { // ignore read-only fields
                    continue;
                }

                String itemName = pd.getName();

                Field field = FieldUtils.getField(this.getClass(), itemName, true);
                ApiResponseField apiField = field.getAnnotation(ApiResponseField.class);
                if (apiField == null) {
                    continue;
                }

                String value = data.get(apiField.value());
                if (value == null) {
                    continue;
                }

                Class<?> typeClass = field.getType();
                if (String.class.isAssignableFrom(typeClass)) {
                    writeMethod.invoke(this, value.toString());
                } else if (Long.class.isAssignableFrom(typeClass)) {
                    if (StringUtils.isNumeric(value)) {
                        writeMethod.invoke(this, Long.valueOf(value.toString()));
                    }
                } else if (Integer.class.isAssignableFrom(typeClass)) {
                    if (StringUtils.isNumeric(value)) {
                        writeMethod.invoke(this, Integer.valueOf(value.toString()));
                    }
                } else if (Boolean.class.isAssignableFrom(typeClass)) {
                    if (value != null) {
                        writeMethod.invoke(this, Boolean.valueOf(value.toString()));
                    }
                } else if (Date.class.isAssignableFrom(typeClass)) {
                    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                    writeMethod.invoke(this, format.parse(value.toString()));
                } else if (WechatPayTradeStatus.class.isAssignableFrom(typeClass)) {
                    if (StringUtils.isNotBlank(value)) {
                        writeMethod.invoke(this, WechatPayTradeStatus.valueOf(value));
                    }
                } else {
                    if (StringUtils.isNotBlank(value)) {
                        writeMethod.invoke(this, value);
                    }
                }
            }

        } catch (Exception e) {
            throw new WechatPayException(e);
        }


    }
}
