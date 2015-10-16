package com.frame.dao.bbook;

import java.util.List;
import java.util.Map;

import com.frame.model.bbook.BBook;

public interface BBookMapper {
    int deleteByPrimaryKey(Integer bookId);

    int insert(BBook record);

    int insertSelective(BBook record);

    BBook selectByPrimaryKey(Integer bookId);

    int updateByPrimaryKeySelective(BBook record);

    int updateByPrimaryKey(BBook record);
    
    BBook getBookByIsbn(String isbn);

	List<BBook> getBooks(Map paramMap);
	
	int checkIfExist(String isbn);

	int deleteBook(String publishId);
}