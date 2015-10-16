package com.frame.dao.bpublisher;

import java.util.List;
import java.util.Map;

import com.frame.model.bpublisher.BPublisher;

public interface BPublisherMapper {
    int deleteByPrimaryKey(Integer publishId);

    int insert(BPublisher record);

    int insertSelective(BPublisher record);

    BPublisher selectByPrimaryKey(Integer publishId);

    int updateByPrimaryKeySelective(BPublisher record);

    int updateByPrimaryKey(BPublisher record);
    
    BPublisher getPublisherByMemberId(Map map);

	List<BPublisher> getBooks(Map paramMap);

	BPublisher getBookDetail(Integer publishId);

	void updatePublisherState(Integer publisherId);

	void updatePublisherStatusFree(Map param);

	List<BPublisher> getMyBooks(Integer memberId);

	List<BPublisher> getBooksByKewWord(Map paramMap);
}