package com.frame.service.bbook;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.dao.bbook.BBookMapper;
import com.frame.model.bbook.BBook;


@Service
public class BBookService {

	@Autowired
	private BBookMapper bbookMapper;

	public int addBook(BBook book) {
		// TODO Auto-generated method stub
		return bbookMapper.insert(book);
	}

	public int checkIfExist(String isbn) {
		// TODO Auto-generated method stub
		return bbookMapper.checkIfExist( isbn);
	}

	public BBook getBookByIsbn(String isbn) {
		// TODO Auto-generated method stub
		return bbookMapper.getBookByIsbn( isbn);
	}

	public List<BBook> getBooks(Map paramMap) {
		// TODO Auto-generated method stub
		return bbookMapper.getBooks(paramMap);
	}

	public int deleteBook(String publishId) {
		// TODO Auto-generated method stub
		return bbookMapper.deleteBook( publishId);
	}
}
