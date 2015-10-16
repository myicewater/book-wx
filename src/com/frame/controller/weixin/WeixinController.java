package com.frame.controller.weixin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.common.util.PropUtil;
import com.frame.model.bmemberwx.BMemberwx;
import com.frame.service.bmemberwx.BMemberWxService;
import com.frame.weixin.main.MenuManager;
import com.frame.weixin.pojo.AccessToken;
import com.frame.weixin.pojo.WeiXinTokenCode;
import com.frame.weixin.service.CoreService;
import com.frame.weixin.util.LogUtil;
import com.frame.weixin.util.SHA1;
import com.frame.weixin.util.WeixinUtil;
/**
 * 微信交互controller，主要负责微信菜单交互的回调转发
 * @author zhangshaoliang
 * 2015-6-10下午4:05:38
 */
@Controller
public class WeixinController {
	
	private static String TOKEN = PropUtil.getValue("weixinToken") ;
	private static final Logger logger = Logger.getLogger(WeixinController.class);
	
	private static String APPID = PropUtil.getValue("weixinAppid");
	private static String SECRET = PropUtil.getValue("weixinSecret");
	
	@Autowired
	private BMemberWxService bMemberWxService;
	
	/**
	 * 微信验证接口，GET方式
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/winxin",method=RequestMethod.GET)
	public void validator(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String signature = request.getParameter("signature");//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String timestamp  = request.getParameter("timestamp");//时间戳
		String nonce = request.getParameter("nonce");//随机数
		String echostr = request.getParameter("echostr");////微信随机字符串，原样返回确认来自微信
		
		String[] arr = {TOKEN,timestamp,nonce};
		
		Arrays.sort(arr);///进行字典排序
		String finalStr = arr[0]+arr[1]+arr[2];///拼接成字符串
		finalStr = new SHA1().getDigestOfString(finalStr.getBytes()).toLowerCase();///sha1加密,结果转成小写
		
		if(finalStr.equals(signature)){
			////确认来自微信，将echostr原样返回
			response.getWriter().write(echostr);
		}
	}
	
	
	/**
	   *
	   * @param request
	   * @param model
	   * @return
	   */
	  @RequestMapping(value="/weixin/loadWeixinMenue.htm")
	  @ResponseBody
	  public Map  loadWeixinMenue(HttpServletRequest request,Model model){
	      Map resultMap = new HashMap();
	      
	      
	   
	      AccessToken at = WeixinUtil.getAccessToken(APPID, SECRET);
			if (at != null) {
				// 调用接口创建菜单
				int result = WeixinUtil.createMenu(MenuManager.getMenu(), at.getToken());
				System.out.println(result);
				// 判断菜单创建结果
				if (0 == result){
					resultMap.put("result", "菜单创建成功！");
					System.out.println("菜单创建成功！");
				}else{
					resultMap.put("result", "菜单创建失败，错误码");
				}
			}
	      
	      
	      return resultMap;
	  }
	
	
	/**
	 * 微信核心交互接口，POST方式, 本接口用于响应粉丝用户的对话消息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/winxin",method=RequestMethod.POST)
	public void mainService(HttpServletRequest request,HttpServletResponse response) throws IOException{
		LogUtil.printInfoLog("【微信发起请求】");
		 // 将请求、响应的编码均设置为UTF-8（防止中文乱码） 
       request.setCharacterEncoding("UTF-8"); 
       response.setCharacterEncoding("UTF-8"); 

       // 调用核心业务类接收消息、处理消息 
       String respMessage = new CoreService().processRequest(request,bMemberWxService); 
        request.getRequestURI();
       // 响应消息 
       PrintWriter out = response.getWriter(); 
       out.print(respMessage); 
       out.close(); 
		
	}
	
	
	/**
	 * 本接口用于响应菜单按钮的(事件或url链接)请求
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="winxin/winxinFilter")
	public String menuService(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String openId = request.getParameter("openId");
		String menuType = request.getParameter("menuType");
		
		//如果openId为null，说明是view的menu进入，则使用网页授权得到openid
		if(StringUtil.isBlank(openId)){
			String code = request.getParameter("code");
			WeiXinTokenCode tokenCode = WeixinUtil.getAccessToken(code);
			openId = tokenCode.getOpenid();
			logger.info("code:"+code);
			logger.info("openId by code:"+openId);
		}
		request.getSession().setMaxInactiveInterval(60*60);
		request.getSession().setAttribute("openId", openId);
		///根据openId查找member
		//LmMemberWx memberWx = winxinService.getWxMemberByOpenId(openId);
		BMemberwx memberWx = bMemberWxService.getMemberWxByOpenId(openId);
		if(memberWx!=null&&memberWx.getMemberId()!=null){
			////如果存在，说明已经绑定过了
			request.getSession().setAttribute("memberId", memberWx.getMemberId());////设置session的memberId，用于用户免登陆
			request.setAttribute("memberId", memberWx.getMemberId());
			request.setAttribute("openId", openId);
			request.setAttribute("isBinding", "T");///是否绑定
		}else{
			request.getSession().removeAttribute("memberId");
			request.setAttribute("memberId", "");
			request.setAttribute("openId", openId);
			request.setAttribute("isBinding", "F");///是否绑定
		}
		
		
		if("publishBook".equals(menuType)){
			return "redirect:/bbook/toPublishBook.htm";
		}
		
		if("findBook".equals(menuType)){
			return "redirect:/bbook/toBookIndex.htm";
		}
		
		if("userCenter".equals(menuType)){
			return "redirect:/bmember/toMemberCenter.htm";
		}
		
		return "redirect:/bmember/toLoginPage.htm";
	}
	
}
