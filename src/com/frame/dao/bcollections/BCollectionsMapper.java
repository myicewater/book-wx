package com.frame.dao.bcollections;

import java.util.List;

import com.frame.model.bcollections.BCollections;

public interface BCollectionsMapper {
    int deleteByPrimaryKey(Integer collectId);

    int insert(BCollections record);

    int insertSelective(BCollections record);

    BCollections selectByPrimaryKey(Integer collectId);

    int updateByPrimaryKeySelective(BCollections record);

    int updateByPrimaryKey(BCollections record);

	List<BCollections> getMyCollections(Integer memberId);

	int cancelCollection(String collectId);
}