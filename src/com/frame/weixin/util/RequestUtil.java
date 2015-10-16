package com.frame.weixin.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;

import com.frame.common.util.DateUtil;
import com.frame.common.util.StringUtil;

public class RequestUtil {
	private RequestUtil(){
	}
	
	/**
	 * 
	 * @param id bean id
	 * @param req
	 * @return
	 */
	public static Object getService(String id,HttpServletRequest req){
		WebApplicationContext beanFactory = (WebApplicationContext) req.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return beanFactory.getBean(id);
	}
	
	
	
	public static String getVerificationCode(HttpServletRequest request, String mobile,String flag){
		String req_currentMobile ="";
		String req_randomCode = "";
		String req_getCodeTime = "";
		//先从session中获取验证码等信息
		if("FA".equals(flag)){//忘记密码短信验证码
			req_currentMobile = (String) request.getSession().getAttribute("currentMobile_FA");
			req_randomCode = (String) request.getSession().getAttribute("randomCode_FA");
			req_getCodeTime = (String) request.getSession().getAttribute("getCodeTime_FA");
		}else if("RE".equals(flag)){
			req_currentMobile = (String) request.getSession().getAttribute("currentMobile");
			req_randomCode = (String) request.getSession().getAttribute("randomCode");
			req_getCodeTime = (String) request.getSession().getAttribute("getCodeTime");
		}else if("FQX".equals(flag)){
			req_currentMobile = (String) request.getSession().getAttribute("currentMobile_FQX");
			req_randomCode = (String) request.getSession().getAttribute("randomCode_FQX");
			req_getCodeTime = (String) request.getSession().getAttribute("getCodeTime_FQX");
		}else if("ZF".equals(flag)){
			req_currentMobile = (String) request.getSession().getAttribute("currentMobile_ZF");
			req_randomCode = (String) request.getSession().getAttribute("randomCode_ZF");
			req_getCodeTime = (String) request.getSession().getAttribute("getCodeTime_ZF");
		}else{
			
		}
		
		String new_randomCode = RandomCodeUtil.randomNumCode(6);
		//如果之前存在验证码并没有超时就返回之前的验证码，如果不存在则发送新的验证码
			String now = DateUtil.getCurrentDataTime();
			if(StringUtil.notEmpty(req_currentMobile) && StringUtil.notEmpty(req_randomCode) && StringUtil.notEmpty(req_getCodeTime) ){
			String code="";
			if(DateUtil.minuteDistance(req_getCodeTime, now) > 60 || !req_currentMobile.equals(mobile)){
				//超时或不是一个手机号码
				code =  new_randomCode;
			}else{
				code = req_randomCode;
				now=req_getCodeTime;
			}
			if("FA".equals(flag)){
				request.getSession().setAttribute("currentMobile_FA", mobile);
				request.getSession().setAttribute("randomCode_FA", code);
				request.getSession().setAttribute("getCodeTime_FA", now);
			}else if("RE".equals(flag)){//注册
				request.getSession().setAttribute("currentMobile", mobile);//当前验证码对应的手机号
				request.getSession().setAttribute("randomCode", code );
				request.getSession().setAttribute("getCodeTime", now);
			}else if("FQX".equals(flag)){
				request.getSession().setAttribute("currentMobile_FQX", mobile);//当前验证码对应的手机号
				request.getSession().setAttribute("randomCode_FQX", code );
				request.getSession().setAttribute("getCodeTime_FQX", now);
			}else if("ZF".equals(flag)){
				request.getSession().setAttribute("currentMobile_ZF", mobile);//当前验证码对应的手机号
				request.getSession().setAttribute("randomCode_ZF", code );
				request.getSession().setAttribute("getCodeTime_ZF", now);
			}else{
				
			}
			//String msg = "您的手机验证码是："+code+"，请输入完成验证，欢迎注册微理财。";
			return code;
		}else{
			if("FA".equals(flag)){
				request.getSession().setAttribute("currentMobile_FA", mobile);
				request.getSession().setAttribute("randomCode_FA",  new_randomCode);
				request.getSession().setAttribute("getCodeTime_FA", now);
			}else if("RE".equals(flag)){//注册
				request.getSession().setAttribute("currentMobile", mobile);//当前验证码对应的手机号
				request.getSession().setAttribute("randomCode", new_randomCode);
				request.getSession().setAttribute("getCodeTime",now);
			}else if("FQX".equals(flag)){
				request.getSession().setAttribute("currentMobile_FQX", mobile);//当前验证码对应的手机号
				request.getSession().setAttribute("randomCode_FQX", new_randomCode);
				request.getSession().setAttribute("getCodeTime_FQX", now);
			}else if("ZF".equals(flag)){
				request.getSession().setAttribute("currentMobile_ZF", mobile);//当前验证码对应的手机号
				request.getSession().setAttribute("randomCode_ZF", new_randomCode);
				request.getSession().setAttribute("getCodeTime_ZF", now);
			}else{
				
			}
			
			/*发送短信*/
			//String msg = "您首次的手机验证码是："+new_randomCode+"，请输入完成验证，欢迎注册微理财。";
			return new_randomCode;
		}
	}
	
}
