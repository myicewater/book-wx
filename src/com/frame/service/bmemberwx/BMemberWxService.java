package com.frame.service.bmemberwx;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.dao.bmemberwx.BMemberwxMapper;
import com.frame.model.bmemberwx.BMemberwx;

@Service
public class BMemberWxService {

	
	
	
	@Autowired
	private BMemberwxMapper bMemberwxMapper;
	public int addWxMember(BMemberwx wxMember){
		return bMemberwxMapper.insert(wxMember);
	}
	public BMemberwx getMemberWxByOpenId(String openId) {
		// TODO Auto-generated method stub
		return bMemberwxMapper.getMemberWxByOpenId( openId);
	}
	public int cancelBind(Map paramMap) {
		// TODO Auto-generated method stub
		return bMemberwxMapper.cancelBind( paramMap);
	}
	
	
	public BMemberwx getMemberWxByUserId(Integer memberId) {
		// TODO Auto-generated method stub
		return bMemberwxMapper.getMemberWxByUserId( memberId);
	}
	
}
