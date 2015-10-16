package com.frame.dao.bcode;

import com.frame.model.bcode.BCode;

public interface BCodeMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(BCode record);

    int insertSelective(BCode record);

    BCode selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(BCode record);

    int updateByPrimaryKey(BCode record);
    
    BCode selectCodeByCode(String inviteCode);
}