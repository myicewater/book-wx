package com.frame.controller.bcollection;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.common.util.WeiXinJsUtil;
import com.frame.model.bcollections.BCollections;
import com.frame.service.bcollection.BCollectionService;
import com.frame.weixin.comfig.WeiXinConfig;

@Controller
public class BCollectionController {

	@Autowired
	private BCollectionService bCollectionService;
	
	
	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bcollection/cancelCollection.htm")
	  @ResponseBody
	  public Map  cancelCollection(HttpServletRequest request,Model model){
	      Map resultMap = new HashMap();
	   
	      String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      String collectId = request.getParameter("collectId");
	      
	      int result = bCollectionService.cancelCollection(collectId);
	      if(result>0){
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
	  @RequestMapping(value="/bcollection/getMyCollections.htm")
	  public String  getMyCollections(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	   
	       List<BCollections> collections = bCollectionService.getMyCollections(memberId);
	       
	       request.setAttribute("collections", collections);

	       return "/bcollection/my-collections.html";
	  }
	
	  /**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bcollection/collectBook.htm")
	  @ResponseBody
	  public Map  collectBook(HttpServletRequest request,Model model){
	      Map resultMap = new HashMap();
	   
	      String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      
	      String publishId = request.getParameter("publishId");
	       
	       BCollections bCollections =  new BCollections();
	       bCollections.setCollecteUser(memberId);
	       bCollections.setCreateTime(new Date());
	       bCollections.setIsCancel("F");
	       bCollections.setPublishId(Integer.valueOf(publishId));
	      
	      int result = bCollectionService.collectBook(bCollections);
	      
	      if(result>0){
		        resultMap.put("resultCode", "00");
		      }else{
		        resultMap.put("resultCode", "99");
		       }   
	      return resultMap;
	  }
}
