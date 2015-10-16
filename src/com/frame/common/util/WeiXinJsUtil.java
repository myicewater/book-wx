package com.frame.common.util;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.weixin.comfig.WeiXinConfig;
import com.frame.weixin.pojo.AccessToken;
import com.frame.weixin.thread.TokenThread;
import com.frame.weixin.util.CreatSignature;

public class WeiXinJsUtil {

	private static final Logger logger = LoggerFactory.getLogger(WeiXinJsUtil.class);
	public static WeiXinConfig fetchConfig(HttpServletRequest request ){
		//配置微信js
		// 这部分是拦截sdk-js接口所需要的参数
		String appId = PropUtil.getValue("weixinAppid");
		Long datetime = new Date().getTime();
		String da = datetime.toString().substring(0, 9);
		Long time = Long.parseLong(da);
		String nonceStr = UUID.randomUUID().toString();
		
		String url = request.getRequestURL().toString();
		String paramters = request.getQueryString();
		String URL = url;
		if(paramters != null){
			URL +="?"+paramters;
		}
		
		AccessToken accessToken = TokenThread.accessToken;
		String accessTokenStr = accessToken.getToken();
		logger.info("jsapi_ticket:"+accessTokenStr);
		logger.info("nonceStr:"+nonceStr);
		logger.info("timestamp:"+time);
		logger.info("url:"+URL);
		String signature = CreatSignature.getSignature(accessTokenStr,time, nonceStr, URL);
		
		WeiXinConfig wc = new WeiXinConfig();
		wc.setAppId(appId);
		wc.setTime(time);
		wc.setNonceStr(nonceStr);
		wc.setSignature(signature);
		
		return wc;
	}
}
