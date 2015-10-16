package com.frame.weixin.thread;


import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.frame.common.util.PropUtil;
import com.frame.weixin.pojo.AccessToken;
import com.frame.weixin.pojo.JsapiTicket;
import com.frame.weixin.util.WeixinUtil;

/**
 * 定时获取微信access_token的线程
 * 
 * @author liuyq
 * @date 2013-05-02
 */
public class TokenThread implements Runnable {
	private static final Logger log = Logger.getLogger(TokenThread.class);
	public static AccessToken accessToken = null;
	private final static String jsapi_ticket_url = " https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=TOKEN&type=jsapi";
	public static JsapiTicket jsapi_ticket = null;

	public void run() {
		while (true) {
			try {
				String appid = PropUtil.getValue("weixinAppid");
				String secret = PropUtil.getValue("weixinSecret");
				accessToken = WeixinUtil.getAccessToken(appid, secret);
				if (null != accessToken) {
					log.info("获取access_token成功，有效时长{"+accessToken.getExpiresIn()+"}秒 token:{"+accessToken.getToken()+"}");
					
					String url = jsapi_ticket_url.replace("TOKEN", accessToken.getToken());
					JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
					if (jsonObject != null || "".equals(jsonObject)) {
						jsapi_ticket = new JsapiTicket();
						jsapi_ticket.setErrcode((Integer) jsonObject.get("errcode"));
						jsapi_ticket.setErrmsg((String) jsonObject.get("errmsg"));
						jsapi_ticket.setTicket((String) jsonObject.get("ticket"));
						jsapi_ticket.setExpiresIn((Integer) jsonObject.get("expires_in"));

					}
					if (null != jsapi_ticket) {
						log.info("获取jsapi_ticket成功，有效时长{"+jsapi_ticket.getExpiresIn()+"}秒 ticket:{"+jsapi_ticket.getTicket()+"}");
						// 休眠7000秒
//						Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
						Thread.sleep(jsapi_ticket.getExpiresIn() * 1000);
					} else {
						// 如果access_token为null，60秒后再获取
						Thread.sleep(60 * 1000);
					}
					
					
					
					// 休眠7000秒
//					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
//					Thread.sleep(accessToken.getExpiresIn() * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}
				
				
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}
}
