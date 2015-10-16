package com.frame.dao.blocation;

import com.frame.model.blocation.BLocation;

public interface BLocationMapper {
    int deleteByPrimaryKey(Integer locationId);

    int insert(BLocation record);

    int insertSelective(BLocation record);

    BLocation selectByPrimaryKey(Integer locationId);

    int updateByPrimaryKeySelective(BLocation record);

    int updateByPrimaryKey(BLocation record);
}