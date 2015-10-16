package com.frame.dao.bmember;

import java.util.Map;

import com.frame.model.bmember.BMember;

public interface BMemberMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(BMember record);

    int insertSelective(BMember record);

    BMember selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(BMember record);

    int updateByPrimaryKey(BMember record);
    
    BMember selectMemberByPhoneNum(String telphoneNum);

	int updateWxInfo(Map paramMap);//关联用户信息和微信信息

	BMember selectByMobileAndPas(Map paramMap);
}