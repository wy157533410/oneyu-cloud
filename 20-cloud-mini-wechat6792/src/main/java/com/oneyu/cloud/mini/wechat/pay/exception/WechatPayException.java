package com.oneyu.cloud.mini.wechat.pay.exception;

/**
 * 
 * @author Oneyu
 *
 * @CreateDate  3:03:16 PM Jun 24, 2020
 */
public class WechatPayException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8950369376853179949L;
	private String errCode;
    private String errMsg;

    public WechatPayException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public WechatPayException(Throwable cause) {
        super(cause);
        this.errMsg = cause.getMessage();
    }

    public WechatPayException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg = message;
    }

    public WechatPayException(String message) {
        super(message);
        this.errMsg = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
