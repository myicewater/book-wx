package com.frame.service.bcomment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.bcomment.BCommentMapper;

@Service
@Transactional
public class BCommentService {

	
	@Autowired
	private BCommentMapper bCommentMapper;
	
	
}
