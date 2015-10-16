package com.frame.service.border;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.border.BOrderMapper;
import com.frame.dao.bpublisher.BPublisherMapper;
import com.frame.model.border.BOrder;

@Service
@Transactional
public class BOrderService {

	@Autowired
	private BOrderMapper bOrderMapper;
	
	@Autowired
	private BPublisherMapper bPublisherMapper;

	public int addOrder(BOrder order) {
		// TODO Auto-generated method stub
		bPublisherMapper.updatePublisherState(order.getPublisherId());
		
		return bOrderMapper.insert(order);
	}

	public List<BOrder> getMyInBookOrder(Integer memberId) {
		// TODO Auto-generated method stub
		return bOrderMapper.getMyInBookOrder( memberId);
	}

	public List<BOrder> getMyOutBookOrder(Integer memberId) {
		// TODO Auto-generated method stub
		return bOrderMapper.getMyOutBookOrder( memberId);
	}

	public int updateOrderStatus(Map param) {
		// TODO Auto-generated method stub
		String status = (String)param.get("status");
		if("3".equals(status) || "4".equals(status)){
			bPublisherMapper.updatePublisherStatusFree(param);
		}
		
		return bOrderMapper.updateOrderStatus( param);
	}

	public List<BOrder> toDealBookOrders(Integer memberId) {
		// TODO Auto-generated method stub
		return bOrderMapper.toDealBookOrders( memberId);
	}

	public BOrder getBookOrderDetail(String orderId) {
		// TODO Auto-generated method stub
		return bOrderMapper.getBookOrderDetail( orderId);
	}

	public List<BOrder> getConfirmBookOrders(Integer memberId) {
		// TODO Auto-generated method stub
		return bOrderMapper.getConfirmBookOrders( memberId) ;
	}

	public List<BOrder> getUnCommentBookOrders(Integer memberId) {
		// TODO Auto-generated method stub
		return bOrderMapper.getUnCommentBookOrders( memberId) ;
	}

	public List<BOrder> getFinishedBookOrders(Integer memberId) {
		// TODO Auto-generated method stub
		return bOrderMapper.getFinishedBookOrders( memberId) ;
	}
	
	
}
