package com.frame.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.frame.common.constant.ReturnCode;
import com.frame.common.util.RequestUtil;

/**
 * 权限拦截器，当用户未登录时，拒绝某些访问（跳到登陆页面）
 * @author zhangshaoliang
 * 2015-7-1下午3:12:24
 */
public class IdentificationInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = Logger.getLogger(HandlerInterceptorAdapter.class);
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 预处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//、访问前进行验证
		//、1、无需登陆的资源直接访问
		String requestURL = request.getRequestURL().toString();
		String param = RequestUtil.getAllParam(request);
		requestURL = StringUtil.isBlank(param) ? requestURL :  requestURL+"?"+param;
		if(isIgnoreUrl(requestURL)){
			return super.preHandle(request, response, handler);
		}
		//、2、需要登陆的资源先验证身份
		Integer memberId = (Integer) request.getSession().getAttribute("memberId");
		//if(StringUtil.isBlank(memberId)){
		if(memberId == null){
			//未登陆，跳转到登陆页面
			logger.info("被拦截，跳转到登录页面。。。");
			request.getSession().setAttribute("login_redirect_url",requestURL);
			request.getRequestDispatcher("/bmember/toLoginPage.htm").forward(request, response);
			return false;
		}
		/*LmMember member = memberDao.getMemberById(memberId);
		if(null ==member){
			//跳转到登陆页面
			request.getSession().setAttribute("login_redirect_url",requestURL);
//			response.reset();
			request.getRequestDispatcher("/login/showLogin.htm").forward(request, response);
//			response.sendRedirect("/lmlc-wx/login/showLogin.htm");
			return false;////stop handling by the interceptors list
		}*/
		
		//、已登录则继续执行
		return super.preHandle(request, response, handler);
	}
	
	
	
	/**
	 * 判断请求URL是否在忽视列表url里,<font color="red">忽视规则:当请求url包含忽视列表中某个字符串时，就判定为该忽视的url</font>
	 * @return
	 */
	private boolean isIgnoreUrl(String url){
		if(null==ReturnCode.ignoreUrls||ReturnCode.ignoreUrls.size()==0){
			///未设置忽视列表，所有地址都需要权限访问
			return false;
		}
		for(String str:ReturnCode.ignoreUrls){
			if(url.contains(str)){
				return true;
			}
		}
		return false;
	}

	
}
