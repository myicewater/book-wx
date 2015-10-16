package com.frame.service.bmember;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.bmember.BMemberMapper;
import com.frame.model.bmember.BMember;

@Service
@Transactional
public class BMemberService {

	@Autowired
	private BMemberMapper bMemberMapper;
	

	public BMember getBMemeberById(Integer i) {
		// TODO Auto-generated method stub
		return bMemberMapper.selectByPrimaryKey(i);
	}

	/**
	 * 用户注册,建立与微信表关联，添加事务性
	 * @param bMember
	 * @param openId 
	 * @return
	 */
	public int memberRegister(BMember bMember, String openId) {
		// TODO Auto-generated method stub
		int InsertResult = bMemberMapper.insert(bMember);
		BMember bMemeber = bMemberMapper.selectMemberByPhoneNum( bMember.getMobile());
		
		Map paramMap = new HashMap();
		paramMap.put("memberId", bMemeber.getMemberId());
		paramMap.put("openId", openId);
		int upResult = bMemberMapper.updateWxInfo(paramMap);//微信信息 用户信息关联
		
		if(InsertResult>0 && upResult>0){
			return bMemeber.getMemberId();
		}else{
			return 0;
		}
		
	}
	
	/**
	 * 关联微信信息和用户信息
	 * @param map
	 * @return
	 */
	public int setLoginState(Map map){
		return bMemberMapper.updateWxInfo(map);//微信信息 用户信息关联
	}

	/**
	 * 根据手机号查询用户是否存在
	 * @param telphoneNum
	 * @return
	 */
	public BMember selectMemberByPhoneNum(String telphoneNum) {
		// TODO Auto-generated method stub
		return bMemberMapper.selectMemberByPhoneNum( telphoneNum);
	}

	/**
	 * 查询用户
	 * @param paramMap
	 * @return
	 */
	public BMember login(Map paramMap) {
		// TODO Auto-generated method stub
		return bMemberMapper.selectByMobileAndPas(paramMap);
	}

	public int editUserInfo(BMember member) {
		// TODO Auto-generated method stub
		return bMemberMapper.updateByPrimaryKeySelective(member);
	}
	
	
}
