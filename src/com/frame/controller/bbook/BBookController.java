package com.frame.controller.bbook;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.common.constant.BookConstant;
import com.frame.common.util.ClassGenerateUtil;
import com.frame.common.util.HttpRequestUtil;
import com.frame.common.util.PropUtil;
import com.frame.common.util.WeiXinJsUtil;
import com.frame.model.bauthor.BAuthor;
import com.frame.model.bbook.BBook;
import com.frame.model.bpublisher.BPublisher;
import com.frame.service.bauthor.BAuthorService;
import com.frame.service.bbook.BBookService;
import com.frame.service.bcredit.BCreditScoreService;
import com.frame.service.bpulisher.BPublisherService;
import com.frame.weixin.comfig.WeiXinConfig;


@Controller
public class BBookController {

	
	private static final Logger logger = LoggerFactory.getLogger(BBookController.class);
	
	
	@Autowired
	private BBookService bBookService;
	
	@Autowired
	private BPublisherService bPublisherService ;
	
	@Autowired
	private BAuthorService bAuthorService;
	
	@Autowired
	private BCreditScoreService bCreditScoreService;
	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/deleteBook.htm")
	  @ResponseBody
	  public Map  deleteBook(HttpServletRequest request,Model model){
	   Map resultMap = new HashMap();
	   
	      String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      String publishId = request.getParameter("publishId");
	      
	      int result = bBookService.deleteBook(publishId);
	      
	      if(result >0 ){
	    	  resultMap.put("resultCode", "00");
	      }else{
	    	  resultMap.put("resultCode", "99");
	      }
	      
	      
	      return resultMap;
	  }


	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/toDiscovery.htm")
	  public String  toDiscovery(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	   
	   
	      return "/bbook/discovery.html";
	  }
	

	
	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/getBookDetail.htm")
	  public String  getBookDetail(HttpServletRequest request,Model model){
	   WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	   request.setAttribute("config", config);
	   
	   String openId = (String)request.getSession().getAttribute("openId");
	   Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	   
	   String publishId = request.getParameter("publishId");
	   logger.info("param publishId:"+publishId);
		  BPublisher bPublisher = bPublisherService.getBookDetail(Integer.valueOf(publishId));
		  logger.info("borrowType:"+bPublisher.getMember().getBorrowType());
	   
		  request.setAttribute("bPublisher", bPublisher);
	      return "/bbook/book-detail.html";
	  }
	
	
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/getMyBooks.htm")
	  public String  getMyBooks(HttpServletRequest request,Model model){
		  WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	      request.setAttribute("config", config);
	   
	      String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      
	     
	      List<BPublisher> publishers = bPublisherService.getMyBooks(memberId);
	   
	      request.setAttribute("publishers", publishers);
	   
	      return "/bbook/my-books.html";
	  }
	  
	  
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/toBookIndex.htm")
	  public String  toBookIndex(HttpServletRequest request,Model model){
		  WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	      request.setAttribute("config", config);
	   
	      String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      
	      String indexBookNumber = PropUtil.getValue("indexBookNumber");
	      int listNum;
	      if(indexBookNumber != null){
	    	  listNum = Integer.valueOf(indexBookNumber);
	      }else{
	    	  listNum = 8;
	      }
	      Map paramMap = new HashMap();
	      paramMap.put("listNum", listNum);
	      List<BPublisher> publishers = bPublisherService.getBooks(paramMap);
	      logger.info("Search book count:"+publishers.size());
	      String uploadRelatedPath = PropUtil.getValue("uploadRelatedPath");
	   
	      request.setAttribute("publishers", publishers);
	      request.setAttribute("uploadRelatedPath", uploadRelatedPath);
	   
	      return "/bbook/book-index.html";
	  }
	  
	  
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/searchBook.htm")
	  @ResponseBody
	  public Map  searchBook(HttpServletRequest request,Model model){
		  WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	      request.setAttribute("config", config);
	   
	      String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      String keyWord = request.getParameter("keyWord");
	      
	     
	      Map paramMap = new HashMap();
	      paramMap.put("keyWord", keyWord);
	      List<BPublisher> publishers = bPublisherService.getBooksByKewWord(paramMap);
	      
	      String uploadRelatedPath = PropUtil.getValue("uploadRelatedPath");
	   
	      request.setAttribute("publishers", publishers);
	      request.setAttribute("uploadRelatedPath", uploadRelatedPath);
	      
	      Map resultMap = new HashMap();
	      resultMap.put("publishers", publishers);
	   
	      return resultMap;
	  }
	
	
	 @RequestMapping(value="/bbook/fetchBookInfo.htm")
	 @ResponseBody
	 public Map  fetchBookInfo(HttpServletRequest request,Model model) {
		 
		 Map resultMap = new HashMap();
		 
		 String openId = (String)request.getSession().getAttribute("openId");
		 Integer memberId = (Integer)request.getSession().getAttribute("memberId");
		 String isbn = request.getParameter("isbn");
		 BBook bookE= bBookService.getBookByIsbn(isbn);
		 
		 if(bookE != null){
			 resultMap.put("book", bookE);
			 resultMap.put("resultCode", "00");
		 }else{
			 
				 String douBanIsbnUrl = PropUtil.getValue("douBanIsbnUrl");
				 String sr=HttpRequestUtil.sendGet(douBanIsbnUrl+isbn);
				 logger.info("请求地址："+douBanIsbnUrl+isbn);
				 
				 if(sr == null){
					 logger.info("扫描异常，没有获取到图书信息。");
					 resultMap.put("resultCode", "88");//没有获取到图书信息
					 return resultMap;
				 }
				 
			     BBook book = ClassGenerateUtil.generateBBook(sr);
			     List<BAuthor> bauthors = ClassGenerateUtil.generateAuthor(sr) ;
			     book.setBauthors(bauthors);
			     resultMap.put("book", book);
				 resultMap.put("resultCode", "00");
			 
		     
		 }
		 return resultMap;
		 
	 }
	
	
	
	/**
	  *
	  * @param request
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/bbook/publishBook.htm")
	 @ResponseBody
	 public Map  publishBook(HttpServletRequest request,Model model){
		 Map resultMap = new HashMap();
		 WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
		 request.setAttribute("config", config);
		 
		 String openId = (String)request.getSession().getAttribute("openId");
		 Integer memberId = (Integer)request.getSession().getAttribute("memberId");
		 String isbn = request.getParameter("isbn");
		 String lendFee = request.getParameter("lendFee");
		 String lendDays = request.getParameter("lendDays");
		 
		 BBook bookE= bBookService.getBookByIsbn(isbn);
		 if(bookE != null){//添加发布记录
			 Map map = new HashMap();
			 map.put("bookId",bookE.getBookId());
			 map.put("memberId",memberId);
			 BPublisher publisherExist = bPublisherService.getPublisherByMemberId(map);
			 if(publisherExist == null){//不存在发布记录则添加
				 BPublisher publisher = new BPublisher();
				 publisher.setBookId(bookE.getBookId());
				 publisher.setCreateTime(new Date());
				 publisher.setMemberId(memberId);
				 publisher.setStatus(BookConstant.PUBLISHER_STATUS_PUBLISH);
				 publisher.setLendDays(Integer.valueOf(lendDays));
				 publisher.setLendFee(Double.valueOf(lendFee));
				
				 bPublisherService.addPublisher(publisher);
				 bCreditScoreService.editCreditScore(memberId, 10, "1");
			 }
			 
			 resultMap.put("resultCode", "00");
			 
		 }else{//获取图书信息，作者信息
			 String douBanIsbnUrl = PropUtil.getValue("douBanIsbnUrl");
			 String sr=HttpRequestUtil.sendGet(douBanIsbnUrl+isbn);
			 logger.info("请求地址："+douBanIsbnUrl+isbn);
			 
		     BBook book = ClassGenerateUtil.generateBBook(sr);
		     int result = bBookService.addBook(book);
		     Integer bookId = bBookService.checkIfExist(isbn);
		    //生成发布记录
		     BPublisher publisher = new BPublisher();
			 publisher.setBookId(bookId);
			 publisher.setCreateTime(new Date());
			 publisher.setMemberId(memberId);
			 publisher.setStatus(BookConstant.PUBLISHER_STATUS_PUBLISH);
			 publisher.setLendDays(Integer.valueOf(lendDays));
			 publisher.setLendFee(Double.valueOf(lendFee));
			 bPublisherService.addPublisher(publisher);
			 
			 bCreditScoreService.editCreditScore(memberId, 10, "1");
			 
			 //生成作者信息
			 List<BAuthor> bauthors = ClassGenerateUtil.generateAuthor(sr, bookId) ;
			 logger.info("authors:"+bauthors.toString());
			 if(bauthors.size() >0){
				 int r2 = bAuthorService.addAuthors(bauthors);
				 logger.info("insert author result:"+r2);
				 if(result > 0){
					 
					 resultMap.put("resultCode", "00");
				 }else{
					 resultMap.put("resultCode", "99"); 
				 }
			 }else{
				 resultMap.put("resultCode", "00");
			 }
			 
			 
		 }
		 
		 
		 
	     return resultMap;
	 }
	 
	
	
	
	/**
	  *
	  * @param request
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/bbook/toPublishBook.htm")
	 public String  toPublishBook(HttpServletRequest request,Model model){
		 WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
		 request.setAttribute("config", config);
	     return "/bbook/publish-book.html";
	 }
	 
	
	 
}
