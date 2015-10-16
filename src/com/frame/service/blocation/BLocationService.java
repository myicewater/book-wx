package com.frame.service.blocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.blocation.BLocationMapper;
import com.frame.model.blocation.BLocation;

@Service
@Transactional
public class BLocationService {
	@Autowired
	private BLocationMapper bLocationMapper;

	public int addLocation(BLocation bLocation) {
		// TODO Auto-generated method stub
		return bLocationMapper.insert(bLocation);
	}
}
