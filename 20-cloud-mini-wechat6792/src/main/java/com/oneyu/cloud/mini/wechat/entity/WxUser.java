package com.oneyu.cloud.mini.wechat.entity;

import com.oneyu.cloud.base.dto.WxUserDTO;

import lombok.Data;

/**
 * 微信用户
 * @author Oneyu
 *
 * @CreateDate  Jun 12, 2020
 */

@Data
public class WxUser implements java.io.Serializable{
	private static final long serialVersionUID = 6553859006063995446L;
	
	private Long id;
	private String openid;
	
	private String nickname; //昵称
	private String gender;
	private String language;
	private String city;
	private String province;
	private String country;
	private String avatarurl;
	private String unionid="";
	
	/**
	 * 微信小程序
	 * @param dto
	 * @return
	 */
	public WxUser fromMiniProgram(WxUserDTO dto) {
		this.avatarurl = dto.getAvatarUrl();
		this.city = dto.getCity();
		this.country = dto.getCountry();
		this.gender = dto.getGender();
		this.language = dto.getLanguage();
		this.nickname = dto.getNickName();
		this.province = dto.getProvince();
		this.openid = dto.getOpenid();
				
		return this;
	}
}
