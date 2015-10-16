package com.frame.service.bcollection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.bcollections.BCollectionsMapper;
import com.frame.model.bcollections.BCollections;

@Service
@Transactional
public class BCollectionService {

	@Autowired
	private BCollectionsMapper bCollectionsMapper;

	public int collectBook(BCollections bCollections) {
		// TODO Auto-generated method stub
		return bCollectionsMapper.insert(bCollections);
	}

	public List<BCollections> getMyCollections(Integer memberId) {
		// TODO Auto-generated method stub
		return bCollectionsMapper.getMyCollections( memberId) ;
	}

	public int cancelCollection(String collectId) {
		// TODO Auto-generated method stub
		return bCollectionsMapper.cancelCollection( collectId);
	}
}
