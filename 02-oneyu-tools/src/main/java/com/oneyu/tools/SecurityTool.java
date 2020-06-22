package com.oneyu.tools;

import java.util.ArrayList;
import java.util.Comparator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;

/**
 * 
 * @author Oneyu
 *
 * @CreateDate  12:26:42 PM Jun 19, 2020
 */
public class SecurityTool {
	
	/**
	 * sha1
	 * @param src
	 * @return
	 */
	public static String sha1(String src) {
		return SecureUtil.sha1(src);
	}
	
	/**
	 * 微信推送消息signature
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String wxPushSign( String token, String timestamp, String nonce) {
		Assert.notNull(token);
		Assert.notNull(timestamp);
		Assert.notNull(nonce);
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(token);
		arr.add(timestamp);
		arr.add(nonce);
		
		arr.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
			
		});
		
		return SecureUtil.sha1(CollUtil.join(arr, ""));
		
		
	}

}
