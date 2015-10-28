package com.frame.dao.bcredit;

import com.frame.model.bcredit.BCreditScoreChangeInfo;

public interface BCreditScoreChangeInfoMapper {
    int deleteByPrimaryKey(Integer changeId);

    int insert(BCreditScoreChangeInfo record);

    int insertSelective(BCreditScoreChangeInfo record);

    BCreditScoreChangeInfo selectByPrimaryKey(Integer changeId);

    int updateByPrimaryKeySelective(BCreditScoreChangeInfo record);

    int updateByPrimaryKey(BCreditScoreChangeInfo record);
}