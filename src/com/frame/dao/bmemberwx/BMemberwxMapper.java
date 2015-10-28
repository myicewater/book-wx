package com.frame.dao.bmemberwx;

import java.util.Map;

import com.frame.model.bmemberwx.BMemberwx;

public interface BMemberwxMapper {
    int deleteByPrimaryKey(Integer weixinId);

    int insert(BMemberwx record);

    int insertSelective(BMemberwx record);

    BMemberwx selectByPrimaryKey(Integer weixinId);

    int updateByPrimaryKeySelective(BMemberwx record);

    int updateByPrimaryKey(BMemberwx record);
    
    BMemberwx getMemberWxByOpenId(String openId);

	int cancelBind(Map paramMap);

	BMemberwx getMemberWxByUserId(Integer memberId);
}