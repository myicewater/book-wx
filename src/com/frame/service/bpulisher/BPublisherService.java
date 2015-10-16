package com.frame.service.bpulisher;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.dao.bpublisher.BPublisherMapper;
import com.frame.model.bpublisher.BPublisher;

@Service
public class BPublisherService {

	@Autowired
	private BPublisherMapper bPublisherMapper;
	public int addPublisher(BPublisher record){
		return bPublisherMapper.insert(record);
	}
	public BPublisher getPublisherByMemberId(Map map) {
		// TODO Auto-generated method stub
		return bPublisherMapper.getPublisherByMemberId( map);
	}
	public List<BPublisher> getBooks(Map paramMap) {
		// TODO Auto-generated method stub
		return bPublisherMapper.getBooks( paramMap) ;
	}
	public BPublisher getBookDetail(Integer publishId) {
		// TODO Auto-generated method stub
		return bPublisherMapper.getBookDetail( publishId);
	}
	public List<BPublisher> getMyBooks(Integer memberId) {
		// TODO Auto-generated method stub
		return bPublisherMapper.getMyBooks( memberId);
	}
	public List<BPublisher> getBooksByKewWord(Map paramMap) {
		// TODO Auto-generated method stub
		return bPublisherMapper.getBooksByKewWord( paramMap);
	}
}
