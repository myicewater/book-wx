package com.frame.service.bauthor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.dao.bauthor.BAuthorMapper;
import com.frame.model.bauthor.BAuthor;

@Service
public class BAuthorService {
	
	@Autowired
	private BAuthorMapper bAuthorMapper;

	public int addAuthors(List<BAuthor> bauthors) {
		// TODO Auto-generated method stub
		return bAuthorMapper.addAuthors( bauthors);
	}

	
}
