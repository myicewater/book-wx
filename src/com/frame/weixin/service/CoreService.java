package com.frame.weixin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.frame.common.util.HttpClientUtils;
import com.frame.common.util.PropUtil;
import com.frame.model.bmemberwx.BMemberwx;
import com.frame.service.bmemberwx.BMemberWxService;
import com.frame.weixin.pojo.Fans;
import com.frame.weixin.template.MsgTemplate;
import com.frame.weixin.thread.TokenThread;
import com.frame.weixin.util.MessageUtil;
import com.frame.weixin.util.WeixinUtil;

public class CoreService {
	private static String path = PropUtil.getValue("weixinServerAddr");
	private static final Logger logger = Logger.getLogger(CoreService.class);
	/**
	 * 处理微信发来的请求
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request,BMemberWxService bMemberWxService) {
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型，event
			String msgType = requestMap.get("MsgType");
			// 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
			String event = requestMap.get("Event");
			String eventKey = requestMap.get("EventKey");
			

			// 推送类型如果是关注或取消关注
			if (MessageUtil.isEvent(msgType)) {
				/**
				 * 关注
				 */
				if (MessageUtil.isSubscribe(event)) {
					System.out.println(" 关注了！！！！！！！！！！！！！！！！！！！");
					BMemberwx wxMember = bMemberWxService.getMemberWxByOpenId(fromUserName);
					if(wxMember == null){
						Fans fans = WeixinUtil.getFansInfo(TokenThread.accessToken.getToken(), fromUserName);
						 wxMember = new BMemberwx();
						wxMember.setOpenId(fromUserName);
						wxMember.setIscanced("T");
						wxMember.setImgurl(fans.getHeadimgurl());
						wxMember.setCreateTime(new Date());
						wxMember.setUpdateTime(new Date());
						bMemberWxService.addWxMember(wxMember);
					}
					
					
					
					
					return MsgTemplate.getSubscribeMsg(toUserName,fromUserName);
				}
				/*if(MessageUtil.isClick(event)){
					////点击事件
					if("wlc_wxz".endsWith(eventKey)){
						return  new MsgTemplate().getWzxMsg(toUserName, fromUserName);
					}
				}*/
				/**
				 * 取消关注
				 */
				if (MessageUtil.isUnsubscribe(event)) {
					////取消关注时，解绑！
					String URL = path + "weixin/loginOut";					
					Map<String,String> m = new HashMap<String,String>();
					m.put("openId", fromUserName);
					///此处调用wlcApp的方法
					String rsStr = HttpClientUtils.sendPostRequest(URL, m);
					
					logger.info("取消关注，openid为：" + rsStr);
					logger.info("openid为：" + fromUserName+"的用户已解绑微理财");
					
					Map paramMap = new HashMap();
					paramMap.put("openId", fromUserName);
					bMemberWxService.cancelBind(paramMap);
				}
				/**
				 * 捕获用户点击事件
				 */
				if(MessageUtil.isClick(event) || MessageUtil.isView(event)){
					Fans fans = WeixinUtil.getFansInfo(TokenThread.accessToken.getToken(), fromUserName);
					
					//LoginServiceI loginService = (LoginServiceI) SpringWiredBean.getInstance().getBeanById("loginService");
					//Map memberMap = loginService.findMemberWeixinCount(fromUserName);
					//if(memberMap != null && memberMap.size() > 0){
						//如果未绑定，发送验证的信息
					//	respMessage = BusinessService.sendMsg(fromUserName, toUserName, eventKey, fans); 
					//}
				}
				// 判断是否为文本
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				//String content = requestMap.get("Content");
//				 StringBuffer str = new StringBuffer();  
//		            str.append("<xml>");  
//		            str.append("<ToUserName><![CDATA[" + "zhshl" + "]]></ToUserName>");  
//		            str.append("<FromUserName><![CDATA[" + "ZHSHL" + "]]></FromUserName>");  
//		            str.append("<CreateTime>123456789</CreateTime>");  
//		            str.append("<MsgType><![CDATA[text]]></MsgType>");  
//		            str.append("<Content><![CDATA[你实在逗我吗？]]></Content>");  
//		            str.append("</xml>"); 
//		            
//		            
//		            
//				return str.toString();
			}
		} catch (Exception e) {
			logger.error("核心service异常", e);
			e.printStackTrace();
		}
		return "123";
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	
}
