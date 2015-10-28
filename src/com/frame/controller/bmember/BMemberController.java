package com.frame.controller.bmember;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.common.util.Base64ToPicture;
import com.frame.common.util.PropUtil;
import com.frame.common.util.WeiXinJsUtil;
import com.frame.model.bcode.BCode;
import com.frame.model.bmember.BMember;
import com.frame.service.bcode.BCodeService;
import com.frame.service.bcredit.BCreditScoreService;
import com.frame.service.bmember.BMemberService;
import com.frame.weixin.comfig.WeiXinConfig;
import com.frame.weixin.util.MD5Util;

@Controller
public class BMemberController {

	private static final Logger logger = LoggerFactory.getLogger(BMemberController.class);
	
	@Autowired
	private BMemberService bMemberService;
	
	@Autowired
	private BCodeService bCodeService;
	
	
	@Autowired
	private BCreditScoreService bCreditScoreService;
	
	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/bmember/toEditAddress.htm")
	  public String  toEditAddress(HttpServletRequest request,Model model){
	       WeiXinConfig config = WeiXinJsUtil.fetchConfig(request);
	       request.setAttribute("config", config);
	   
	       String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	   
	   
	      return "/baddress/address-edit.html";
	  }
	
	

	
	/**
	  *
	  * @param request
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/bmember/toEditMemberInfo.htm")
	 public String  toEditMemberInfo(HttpServletRequest request,Model model){
		 
		 String openId = (String)request.getSession().getAttribute("openId");
	       Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	       
	       BMember bMember = bMemberService.getBMemeberById(Integer.valueOf(memberId));
	       request.setAttribute("bMember", bMember);
	       
	       String weixinServerAddr = PropUtil.getValue("weixinServerAddr");
	       String uploadRelatedPath = PropUtil.getValue("uploadRelatedPath");
	       request.setAttribute("weixinServerAddr", weixinServerAddr);
	       request.setAttribute("uploadRelatedPath", uploadRelatedPath);
	     return "/bmember/edit-member-info.html";
	 }
	 
	 
	 
	 
	 /**
	  *	完善信息
	  * @param request
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/bmember/completeUserInfo.htm")
	 @ResponseBody
	 public Map completeUserInfo(HttpServletRequest request,Model model){
		 
	     String nickName = request.getParameter("nickName");
	     String age = request.getParameter("age");
	     String sex = request.getParameter("sex");
	     String city = request.getParameter("city");
	     String urlData = request.getParameter("urlData");
	     String position = request.getParameter("position");
	     String personSign = request.getParameter("personSign");
	     
	     
	     String uploadPath = null;
	     String fileName = null;
	     if(urlData != null && !urlData.equals("")){
	    	 uploadPath = PropUtil.getValue("uploadPath");
		     logger.info("uploadPath:"+uploadPath);
		     String absolutePath =uploadPath;
		     logger.info("absolutePath:"+absolutePath);
		     
		     File originalFile = new File(absolutePath);// if the dir
				// exist.
			 if (!originalFile.exists()) {
				originalFile.mkdir();
			 }
		     
			  fileName = UUID.randomUUID().toString();
		     
			
			 
			 if(!absolutePath.endsWith(File.separator)){
				 absolutePath += File.separator;
			 }
			 
			 
			 String suffix = Base64ToPicture.Base64ToPic(urlData, absolutePath+fileName);
			 fileName +=suffix;
			 logger.info("save path :"+uploadPath);
	     }else{
	    	 
	     }
	     
	     Integer memberId = (Integer)request.getSession().getAttribute("memberId");
		 BMember member  = new BMember();
		 member.setNickName(nickName);
		 member.setUserPicUrl(fileName);
		 member.setMemberId(memberId);
		 member.setSex(sex);
		 member.setAge(Integer.valueOf(age));
		 member.setJobOccupation(position);
		 member.setHometown(city);
		 member.setPersonSign(personSign);
		 
		 int result = bMemberService.editUserInfo(member);
		  
		 
		 Map resultMap = new HashMap();
		 if(result >0){
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
	 @RequestMapping(value="/bmember/toMemberIndex.htm")
	 public String  toMemberIndex(HttpServletRequest request,Model model){
	     String openId = (String)request.getSession().getAttribute("openId");
	     logger.info("memberId:"+request.getSession().getAttribute("memberId"));
	     Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	     BMember member = bMemberService.getBMemeberById(memberId);
	     request.setAttribute("member", member);
	     return "/bmember/member-index.html";
	 }
	
	/**
	  *
	  * @param request
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/bmember/toMemberCenter.htm")
	 public String  toMemberCenter(HttpServletRequest request,Model model){
	     String openId = (String)request.getSession().getAttribute("openId");
	     logger.info("memberId:"+request.getSession().getAttribute("memberId"));
	     Integer memberId = (Integer)request.getSession().getAttribute("memberId");
	     BMember member = bMemberService.getBMemeberById(memberId);
	     request.setAttribute("member", member);
	     return "/bmember/member-center.html";
	 }
	
	/**
	  *跳转到测试页面
	  * @param request
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/bmember/testPage.htm")
	 public String  testPage(HttpServletRequest request,Model model){
	  String openId = request.getParameter("openId");
	  request.getSession().setAttribute("openId", openId);
	  return "/bmember/testPage.html";
	 }
	
	/**
	  * 跳转到登录页面
	  * @param request
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/bmember/toLoginPage.htm")
	 public String  toLoginPage(HttpServletRequest request,Model model){
	  return "/bmember/login.html";
	 }
	 
	 /**
	  * 用户登录
	  * @param request
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/bmember/login.htm")
	 @ResponseBody
	 public Map login(HttpServletRequest request,Model model){
		 
		 String openId = (String)request.getSession().getAttribute("openId");
		 String telphoneNum = request.getParameter("telphoneNum");
		 String passwd = request.getParameter("passwd");
		 
		 Map paramMap = new HashMap();
		 
		 paramMap.put("mobile", telphoneNum);
		 paramMap.put("passwd", MD5Util.MD5Encode(passwd));
		 BMember bMember = bMemberService.login(paramMap);
		 
		 Map resultMap = new HashMap();
		 
		 if(bMember != null){
			 paramMap.put("openId", openId);
			 paramMap.put("memberId", bMember.getMemberId());
			 int result = bMemberService.setLoginState(paramMap);//设置登录状态
			 if(result >0){
				 request.getSession().setAttribute("memberId",bMember.getMemberId());////设置session的memberId，用于用户免登陆
				 resultMap.put("resultCode", "00");//登录成功
			 }else{
				 resultMap.put("resultCode", "99");//登录失败
			 }
			 
		 }else{
			 resultMap.put("resultCode", "99");//登录失败
		 }
		 
		 return resultMap;
	 }

	 
	
	/**
	 * 跳转到注册页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/bmember/toRegister.htm")
	public String  toRegister(HttpServletRequest request,Model model){
		return "/bmember/register.html";
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/bmember/memberRegister.htm")
	@ResponseBody
	public Map memberRegister(HttpServletRequest request,Model model){
		
		String openId = (String)request.getSession().getAttribute("openId");
		String telphoneNum = request.getParameter("telphoneNum");
		String passwd = request.getParameter("passwd");
		String inviteCode = request.getParameter("inviteCode");
		Map resultMap = new HashMap();
		
		BMember checkMember = bMemberService.selectMemberByPhoneNum(telphoneNum);
		if(checkMember != null){
			resultMap.put("resultCode", "01");//用户已经存在
			return resultMap;
		}
		
		BMember bMember = new BMember();
		bMember.setMobile(telphoneNum);
		bMember.setPwd(MD5Util.MD5Encode(passwd));
		bMember.setInInviteCode(inviteCode);
		
		int result = bMemberService.memberRegister(bMember,openId);
		if(result >0){
			bCreditScoreService.regEditScore(result);
			request.getSession().setAttribute("memberId",result);////设置session的memberId，用于用户免登陆
			
			resultMap.put("resultCode", "00");//注册成功
		}else{
			resultMap.put("resultCode", "99");//注册失败
		}
		return resultMap;
		
	}
	
	/**
	 * 查询code是否存在
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/bmember/checkInviteCode.htm")
	@ResponseBody
	public Map checkInviteCode(HttpServletRequest request,Model model){
		
		
		String inviteCode = request.getParameter("inviteCode");
		logger.info("inviteCode:"+inviteCode);
		BCode code = bCodeService.selectCodeByCode(inviteCode);
		Map resultMap = new HashMap();
		if(code != null){
			resultMap.put("resultCode", "00");//存在
			
		}else{
			resultMap.put("resultCode", "99");
		}
		return resultMap;
	}
}
