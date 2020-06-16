package com.oneyu.cloud.mini.wechat.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oneyu.cloud.mini.wechat.entity.WxUser;

@Mapper
public interface WxUserDAO {
	
	int create(WxUser user);
	
	WxUser getUserByOpenId(@Param("openid") String openid);

}
