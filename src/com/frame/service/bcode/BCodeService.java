package com.frame.service.bcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.bcode.BCodeMapper;
import com.frame.model.bcode.BCode;

@Service
@Transactional
public class BCodeService {

	@Autowired
	private BCodeMapper bCodeMapper;

	/**
	 * 
	 * @param inviteCode
	 * @return
	 */
	public BCode selectCodeByCode(String inviteCode) {
		// TODO Auto-generated method stub
		return bCodeMapper.selectCodeByCode(inviteCode);
	}

	
	
	
}
