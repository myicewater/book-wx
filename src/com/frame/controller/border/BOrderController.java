package com.frame.controller.border;

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

import com.frame.common.util.DateUtil;
import com.frame.common.util.PropUtil;
import com.frame.common.util.WeiXinJsUtil;
import com.frame.model.bmemberwx.BMemberwx;
import com.frame.model.border.BOrder;
import com.frame.service.bcredit.BCreditScoreService;
import com.frame.service.bmemberwx.BMemberWxService;
import com.frame.service.border.BOrderService;
import com.frame.weixin.comfig.WeiXinConfig;
import com.frame.weixin.template.TemplateUtil;

@Controller
public class BOrderController {

	private static final Logger logger = LoggerFactory.getLogger(BOrderController.class);
	@Autowired
	private BOrderService bOrderService;
	
	
	@Autowired
	private BCreditScoreService bCreditScoreService;
	
	@Autowired
	private BMemberWxService bMemberWxService;
	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/border/buildBookOrder.htm")
	  @ResponseBody
	  public Map  buildBookOrder(HttpServletRequest request,Model model){
		  Map resultMap = new HashMap();
	   
	   	  String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      String publishId = request.getParameter("publishId");
	      String location = request.getParameter("location");
	      String borrowType = request.getParameter("borrowType");
	      String borrowDays = request.getParameter("borrowDays");
	      String borrowId = request.getParameter("borrowId");
	      
	      BOrder order = new BOrder();
	      order.setBorrower(memberId);
	      order.setPublisherId(Integer.valueOf(publishId));
	      order.setCreateTime(new Date());
	      order.setGetType(borrowType);
	      order.setAddress(location);
	      order.setBorrowDays(Integer.valueOf(borrowDays));
	      order.setStatus("0");
	      order.setReturnDate(DateUtil.nextDate(new Date(), Integer.valueOf(borrowDays)));
	      int result = bOrderService.addOrder(order);
	      if(result >0 ){
	    	  
	    	 BMemberwx wx = bMemberWxService.getMemberWxByUserId(Integer.valueOf(borrowId));
	    	 
	    	 String title = "有人借你书了";
	 		 String content = "点击去处理吧";
	 		 String templateId = "UpdD76XBWd4XRFZK824fcU0gmbe5lEBUES1RFs2kmsM";
	 		 String toUser = wx.getOpenId();
	 		 String toUrl = "http://www.9fbank.com.cn";
	 		 TemplateUtil.sendTemplateMsg(title, content, templateId, toUser, toUrl);
	 		 
	    	  bCreditScoreService.editCreditScore(memberId, -5, "2");
	    	  bCreditScoreService.editCreditScore(Integer.valueOf(publishId), 5, "2");
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
	  @RequestMapping(value="/border/updateOrderStatus.htm")
	  @ResponseBody
	  public Map  updateOrderStatus(HttpServletRequest request,Model model){
	   Map resultMap = new HashMap();
	   
	      String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      
	      String status = request.getParameter("status");
	      String orderId = request.getParameter("orderId");
	      Map param = new HashMap();
	      param.put("status", status);
	      param.put("orderId",orderId);
	      int result = bOrderService.updateOrderStatus(param);
	      
	      
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
	  @RequestMapping(value="/bbook/toBookOrderDetail.htm")
	  public String  toBookOrderDetail(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       String orderId = request.getParameter("orderId");
	      
	       BOrder bOrder = bOrderService.getBookOrderDetail(orderId);
	       String uploadRelatedPath = PropUtil.getValue("uploadRelatedPath");
	       
	       request.setAttribute("bOrder", bOrder);
	       logger.info("发布人地址："+bOrder.getbPublisher().getMember().getbLocation().getLocationName());
	       request.setAttribute("memberId", memberId);
	       request.setAttribute("uploadRelatedPath", uploadRelatedPath);
	       return "/border/order-detail.html";
	  }
	  
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/tounDealBookOrders.htm")
	  public String  tounDealBookOrders(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       Map map = new HashMap();
	       map.put("memberId", memberId);
	       //map.put("status", memberId);
	       List<BOrder> bOrders = bOrderService.toDealBookOrders(memberId);
	       
	       request.setAttribute("bOrders", bOrders);
	       return "/border/undealed-order.html";
	  }
	  
	  
	  
	  /**
	   * 从模板消息跳转到待处理订单
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/tounDealBookOrdersFromTempMsg.htm")
	  public String  tounDealBookOrdersFromTempMsg(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	       
	       String memberId = request.getParameter("memberId");
	       BMemberwx wx = bMemberWxService.getMemberWxByUserId(Integer.valueOf(memberId));
	       request.getSession().setAttribute("memberId", memberId);
	       request.getSession().setAttribute("openId", wx.getOpenId());
	       
	       List<BOrder> bOrders = bOrderService.toDealBookOrders(Integer.valueOf(memberId));
	       
	       request.setAttribute("bOrders", bOrders);
	       return "/border/undealed-order.html";
	  }
	  
	  
	  
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/toConfirmBookOrders.htm")
	  public String  toConfirmBookOrders(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       Map map = new HashMap();
	       map.put("memberId", memberId);
	       //map.put("status", memberId);
	       List<BOrder> bOrders = bOrderService.getConfirmBookOrders(memberId);
	       
	       request.setAttribute("bOrders", bOrders);
	       return "/border/unconfirm-order.html";
	  }
	  
	  
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/toUnCommentBookOrders.htm")
	  public String  toUnCommentBookOrders(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       Map map = new HashMap();
	       map.put("memberId", memberId);
	       //map.put("status", memberId);
	       List<BOrder> bOrders = bOrderService.getUnCommentBookOrders(memberId);
	       
	       request.setAttribute("bOrders", bOrders);
	       return "/border/uncomment-order.html";
	  }
	  
	  
	  
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/toFinishedBookOrders.htm")
	  public String  toFinishedBookOrders(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       Map map = new HashMap();
	       map.put("memberId", memberId);
	       //map.put("status", memberId);
	       List<BOrder> bOrders = bOrderService.getFinishedBookOrders(memberId);
	       
	       request.setAttribute("bOrders", bOrders);
	       return "/border/finished-order.html";
	  }
	  
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/toMyInBookOrder.htm")
	  public String  toMyInBookOrder(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       List<BOrder> bOrders = bOrderService.getMyInBookOrder(memberId);
	       
	       request.setAttribute("bOrders", bOrders);
	       return "/border/in-book-order.html";
	  }
	  
	  
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bbook/toOutBookOrder.htm")
	  public String  toOutBookOrder(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       List<BOrder> bOrders = bOrderService.getMyOutBookOrder(memberId);
	       
	       request.setAttribute("bOrders", bOrders);
	       return "/border/out-book-order.html";
	  }
	  
	  
}
