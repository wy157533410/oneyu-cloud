package com.oneyu.cloud.mini.wechat.pay.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;

import com.oneyu.cloud.mini.wechat.pay.exception.WechatPayException;
import com.oneyu.cloud.mini.wechat.pay.protocol.WxPayReqProtocol;

import lombok.extern.slf4j.Slf4j;

/**
 * 对接微信支付服务器的客户端
 * @author Oneyu
 *
 * @CreateDate  2:32:34 PM Jun 24, 2020
 */

@Slf4j
public class WechatPayClient {
	
	private static final String SERVER_URL = "https://api.mch.weixin.qq.com";
    private String appId; // 微信为公众账号Id
    private String mchId; // 微信支付 商户号Id
    private String apiKey;// 秘钥，用于签名
    private byte[] certFile;// 退款时候 数字证书
    
    public WechatPayClient(String appId, String mchId, String apiKey) {
        this.appId = appId;
        this.mchId = mchId;
        this.apiKey = apiKey;
    }
    
    /**
     * 给微信支付服务器发送请求
     * @param protocol
     * @return
     * @throws WechatPayException
     */
    public String send( WxPayReqProtocol protocol) throws WechatPayException{
    	protocol.setAppId(appId);
        protocol.setMchId(mchId);
        protocol.setNonceStr(RandomStringUtils.random(32, true, true));
        
        log.info(protocol.toString());
        
        String url = SERVER_URL + protocol.action();
        String text = "";
        CloseableHttpClient client = this.getClient(protocol); //判断是否需要带上 支付证书
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");

            StringEntity payload = new StringEntity(protocol.data(this.apiKey), "UTF-8");
            httpPost.setEntity(payload);
//           
//            text = client.execute(httpPost, new ResponseHandler<String>() {
//                @Override
//                public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
//                    StringBuilder builder = new StringBuilder();
//                    HttpEntity entity = response.getEntity();
//                    String text;
//                    if (entity != null) {
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
//                        while ((text = bufferedReader.readLine()) != null) {
//                            builder.append(text);
//                        }
//
//                    }
//                    return builder.toString();
//                }
//            });
        } catch (Exception e) {
            throw new WechatPayException(e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new WechatPayException(e);
            }
        }
        return text;
        
    	
    	
    }
    
    
    
    //判断是否需要带上 支付证书
    private  CloseableHttpClient getClient(WxPayReqProtocol protocol) throws WechatPayException {
        CloseableHttpClient client;
        if (protocol.requireCert()) {
            try {
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                loadCertFile();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(this.certFile);
                try {
                    keyStore.load(inputStream, this.mchId.toCharArray());
                } finally {
                    inputStream.close();
                }
                SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, this.mchId.toCharArray()).build();
                //TODO wanyu
//                SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);               
                SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
                client = HttpClients.custom().setSSLSocketFactory(factory).build();
            } catch (Exception e) {
                throw new WechatPayException(e);
            }

        } else {
            client = HttpClients.createDefault();
        }
        return client;
    }
    
    

    /**
     * 加载证书
     */
    private void loadCertFile() {
    	
    }

}
