package com.frame.controller.bcomment;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.common.util.WeiXinJsUtil;
import com.frame.controller.border.BOrderController;
import com.frame.service.bcomment.BCommentService;
import com.frame.weixin.comfig.WeiXinConfig;

@Controller
public class BCommentController {

	@Autowired
	private BCommentService bCommentService;
	
	private static final Logger logger = LoggerFactory.getLogger(BCommentController.class);
	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bcomment/toComment.htm")
	  public String  toComment(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       String orderId = request.getParameter("orderId");
	       
	       

	       return "/bcomment/add-comment.html";
	  }
	
}
