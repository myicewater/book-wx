package com.frame.dao.bcomment;

import com.frame.model.bcomment.BComment;

public interface BCommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(BComment record);

    int insertSelective(BComment record);

    BComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(BComment record);

    int updateByPrimaryKey(BComment record);
}