package com.frame.dao.bcredit;

import java.util.Map;

import com.frame.model.bcredit.BCreditScore;

public interface BCreditScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BCreditScore record);

    int insertSelective(BCreditScore record);

    BCreditScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BCreditScore record);

    int updateByPrimaryKey(BCreditScore record);

	void updateUserScore(Map map);
}