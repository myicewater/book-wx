package com.frame.dao.bauthor;

import java.util.List;

import com.frame.model.bauthor.BAuthor;

public interface BAuthorMapper {
    int deleteByPrimaryKey(Integer authorId);

    int insert(BAuthor record);

    int insertSelective(BAuthor record);

    BAuthor selectByPrimaryKey(Integer authorId);

    int updateByPrimaryKeySelective(BAuthor record);

    int updateByPrimaryKey(BAuthor record);
    
    int addAuthors(List<BAuthor> bauthors);
}