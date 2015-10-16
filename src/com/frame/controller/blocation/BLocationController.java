package com.frame.controller.blocation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.model.blocation.BLocation;
import com.frame.service.blocation.BLocationService;

@Controller
public class BLocationController {

	@Autowired
	private BLocationService bLocationService;
	
	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/blocation/addLocation.htm")
	  @ResponseBody
	  public Map  addLocation(HttpServletRequest request,Model model){
		  Map resultMap = new HashMap();
	   
	      String openId = (String)request.getSession().getAttribute("openId");
	      Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	      String latitude = request.getParameter("latitude");
	      String longitude = request.getParameter("longitude");
	      String locationName = request.getParameter("locationName");
	      
	      BLocation bLocation = new BLocation();
	      bLocation.setCreateTime(new Date());
	      bLocation.setIsUsed("1");
	      bLocation.setLatitude(Double.valueOf(latitude));
	      bLocation.setLocationName(locationName);
	      bLocation.setLongitude(Double.valueOf(longitude));
	      bLocation.setMemberId(memberId);
	      
	      int result = bLocationService.addLocation(bLocation);
	      
	      if(result>0){
	    	  resultMap.put("resultCode", "00");
	      }else{
	    	  resultMap.put("resultCode", "99");
	      }
	      return resultMap;
	  }
}
