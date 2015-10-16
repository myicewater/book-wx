package com.frame.dao.border;

import java.util.List;
import java.util.Map;

import com.frame.model.border.BOrder;

public interface BOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(BOrder record);

    int insertSelective(BOrder record);

    BOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(BOrder record);

    int updateByPrimaryKey(BOrder record);

	List<BOrder> getMyInBookOrder(Integer memberId);

	List<BOrder> getMyOutBookOrder(Integer memberId);

	int updateOrderStatus(Map param);

	List<BOrder> toDealBookOrders(Integer memberId);

	BOrder getBookOrderDetail(String orderId);

	List<BOrder> getConfirmBookOrders(Integer memberId);

	List<BOrder> getUnCommentBookOrders(Integer memberId);

	List<BOrder> getFinishedBookOrders(Integer memberId);
}