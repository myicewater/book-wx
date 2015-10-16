package com.frame.weixin.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.log4j.Logger;

import com.frame.common.util.PropUtil;
import com.frame.weixin.pojo.Fans;
import com.frame.weixin.resp.Article;
import com.frame.weixin.resp.NewsMessage;
import com.frame.weixin.resp.TextMessage;
import com.frame.weixin.util.MessageUtil;



public class BusinessService {
	private static String path = PropUtil.getValue("weixinServerAddr");
	private static final Logger logger = Logger.getLogger(BusinessService.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String sendMsg(String fromUserName, String toUserName,String eventKey, Fans fans) throws UnsupportedEncodingException {
		TextMessage textMessage = new TextMessage();
		//如果未绑定，发送验证的信息
		textMessage = new TextMessage(); 
        textMessage.setToUserName(fromUserName); 
        textMessage.setFromUserName(toUserName); 
        textMessage.setCreateTime(new Date().getTime()); 
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT); 
        textMessage.setFuncFlag(0);
//		if(StringUtil.isNotEmpty(fromUserName)){
//			Map memberMap = checkBoundStatus(fromUserName);
//			if(!"true".equals((String)memberMap.get("flag"))){
//					//String enNickName = StringUtil.escape(fans.getNickName());
//					//未绑定，发送绑定消息 验证身份
//					return BusinessService.noImgMsg(fromUserName,
//							toUserName,
//							"验证身份",
//							MsgTemplate.getYzsfMsg(fromUserName, fans.getNickName()),
//							null,
//							path+"login/bound.htm?openId="+fromUserName);
//			}else{
//				String nowDate = DateUtil.getCurrenDate();
//				 
//				if("tzjl".equals(eventKey)){
//					//投资记录
//					if(StringUtil.isNotEmpty(fromUserName)){
//						//已经绑定
//						ShareOrderServiceI sos = (ShareOrderServiceI) SpringWiredBean.getInstance().getBeanById("shareOrderService");
//						Map tzjlMap = sos.queryTzjlSum(fromUserName);
//						Map profitMap = sos.queryProfit(fromUserName);
//						if(tzjlMap != null && tzjlMap.size()> 0){
//							String msg = MsgTemplate.getTzjlMsg(tzjlMap,profitMap);
//							return BusinessService.noImgMsg(fromUserName,
//									toUserName,
//									"投资记录",
//									msg,
//									null,
//									path+"weixin/urlFilter.htm?wlcMenuType=tzjl&openId="+fromUserName);
//						}
//					}
//					
//				}else if("zjls".equals(eventKey)){
//					//资金流水
//						String user_id = String.valueOf(memberMap.get("user_id")) ;
//						LoginServiceI loginService = (LoginServiceI) SpringWiredBean.getInstance().getBeanById("loginService");
//						WlcMember wlcMember = loginService.findWlcMember(user_id);
//						if(wlcMember != null){
//							WlcSummaryAccountService wsa = (WlcSummaryAccountService) SpringWiredBean.getInstance().getBeanById("wlcSummaryAccountService");
//							WlcSummaryAccount wlcSummaryAccount = new WlcSummaryAccount(wlcMember.getMobile());
//							Map<String,String> fyRsMap =wsa.getSummaryAccount(wlcSummaryAccount);
//							//查询当前用户的可用余额
//							Double kyye = getKyye(fyRsMap);
//							//查询当前用户的在途余额
//							Double ztye = getZtye(fyRsMap);
//							System.out.println("可用余额:"+String.valueOf(kyye));
//							System.out.println("在途余额:"+String.valueOf(ztye));
//							//查询最近一个月的流入流出金额
//							FundsRecordServiceI fundsRecordService = (FundsRecordServiceI) SpringWiredBean.getInstance().getBeanById("fundsRecordService");
//							List monthList = fundsRecordService.getMonthFundsFlow(wlcMember.getId());
//							Map flMap = new HashMap();
//							if(monthList != null && monthList.size() >0){
//								for(int i=0; i < monthList.size(); i++){
//									Map m = (Map) monthList.get(i);
//									String mytype = String.valueOf(m.get("mytype"));
//									String amt = String.valueOf(m.get("amt"));
//									flMap.put(mytype, amt);
//								}
//							}
//							Double inFunds = getFundsInput(flMap);
//							Double outFunds = getFundsOutput(flMap);
//							String msg = MsgTemplate.getZjlsMsg(kyye, ztye, inFunds, outFunds);
//							return BusinessService.noImgMsg(fromUserName,
//									toUserName,
//									"资金流水",
//									msg,
//									null,
//									path+"weixin/urlFilter.htm?wlcMenuType=zjls&openId="+fromUserName);
//					}
//					
//				}else if("wysd".equals(eventKey)){
//					//我要晒单
//					return BusinessService.noImgMsg(fromUserName,
//							toUserName,
//							"微理财晒单赢大奖，预期年化再加0.5%",
//							"超高年化收益再加0.5%，还能有更高的嘛？",
//							path + "/images/welicaiLogo.jpg",
//							path+"weixin/urlFilter.htm?wlcMenuType=wysd&openId="+fromUserName+"&nowDate="+nowDate);
//					
//				}else if("wylt".equals(eventKey)){
//					//我要领投
//					return BusinessService.noImgMsg(fromUserName,
//							toUserName,
//							"敢为天下先，我做领投人！",
//							"第一时间抢购“行业宝”，即有机会成为领投人，预期年化收益率再加0.5%。",
//							path + "images/welicaiLogo.jpg",
//							path+"weixin/urlFilter.htm?wlcMenuType=wylt&openId="+fromUserName+"&nowDate="+nowDate);
//					
//				}else if("wlcgw".equals(eventKey)){
//					String bound_name = String.valueOf(memberMap.get("bound_name")) ;
//					textMessage.setContent(MsgTemplate.getWlcGwMsg(bound_name));
//				}
//				else if("bz".equals(eventKey)){
//					return BusinessService.noImgMsg(fromUserName,
//							toUserName,
//							"微理财使用帮助",
//							"用户使用常见问题",
//							path + "/images/welicaiLogo.jpg",
//							null);
//				}
//				return  MessageUtil.textMessageToXml(textMessage); 
//			}
//		}
        
//        return BusinessService.noImgMsg(fromUserName,
//				toUserName,
//				"千万风投进驻，打造理财师创业首选平台",
//				"来钱捷径：\n一、销售产品，服务客户，你就来钱\n二、猛戳右下角【我】-【拓展理财师】，组建团队，他出单，你也来钱（玖来钱额外服务费）",
//				path + "images/ee_2.jpg", path + "login/firstImg.htm?openId="+fromUserName
//				);
        return BusinessService.noImgMsg(fromUserName,
				toUserName,
				"一大波流量再度来袭，分享抢流量！",
				"六一大派送，争当好朋友！\n每天6000个， 流量红包天天抢！\n分享朋友圈，来抢流量吧！",
				path + "images/ee_5.jpg", path + "login/firstImg.htm?openId="+fromUserName
				);
        //path+"weixin/urlFilter.htm?wlcMenuType=wylt&openId="+fromUserName+"&nowDate="+new Date()
//		return null;
	}
	
	/**
	 * 根据openid判断是否绑定过
	 * @param openId
	 * @return true 绑定    false 未绑定
	 */
//	public static Map checkBoundStatus(String openId){
//		LoginServiceI loginService = (LoginServiceI) SpringWiredBean.getInstance().getBeanById("loginService");
//		Map memberMap = loginService.findMemberWeixinCount(openId);
//		if(memberMap != null && memberMap.size() > 0){
//			Integer status = (Integer) memberMap.get("bound_status");
//			String bound_name = (String) memberMap.get("bound_name");
//			if(status.compareTo(1) == 0){
//				//绑定
//				memberMap.put("flag", "true");
//				memberMap.put("bound_name", bound_name);
//			}else{
//				memberMap.put("flag", "false");
//			}
//		}
//		return memberMap;
//	}
	
	
	/**
	 * 发送图文
	 * @param openId
	 * @param toUserName
	 * @param title
	 * @param msg
	 * @param picUrl
	 * @param url
	 * @return
	 */
	public static String noImgMsg(String openId,String fromUserName,String title,String msg,String picUrl, String url){
		NewsMessage newsMessage = new NewsMessage(); 
        newsMessage.setToUserName(openId);
        newsMessage.setFromUserName(fromUserName);
        newsMessage.setCreateTime(new Date().getTime()); 
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS); 
        newsMessage.setFuncFlag(0); 
        List<Article> articleList = new ArrayList<Article>(); 
        Article article = new Article(); 
		article.setTitle(title); 
		article.setDescription(msg); 
		article.setPicUrl(picUrl); 
		article.setUrl(url); 
		articleList.add(article); 
		// 设置图文消息个数 
		newsMessage.setArticleCount(articleList.size()); 
		// 设置图文消息包含的图文集合 
		newsMessage.setArticles(articleList); 
		// 将图文消息对象转换成xml字符串 
		//logger.info(MessageUtil.newsMessageToXml(newsMessage));
		return  MessageUtil.newsMessageToXml(newsMessage); 
	}
	
	/**
	 * 获取可用余额
	 * @param fyRsMap
	 * @return
	 */
	private static Double getKyye(Map<String,String> fyRsMap){
		Double kyye = 0.0D;
		if(fyRsMap != null && fyRsMap.size() > 0){
			String ye = fyRsMap.get("ca_balance");
			//可用余额
			//TODO 这里的单位应该是分，暂时还没有除
			if(StringUtil.isNotEmpty(ye)){
				kyye = Double.valueOf(ye) / 100;
			} 
		}
		return kyye;
	}
	
	/**
	 * 获取在途余额
	 * @param fyRsMap
	 * @return
	 */
	private static Double getZtye(Map<String,String> fyRsMap){
		Double ztye = 0.0D;
		if(fyRsMap != null && fyRsMap.size() > 0){
			String ye = fyRsMap.get("cu_balance");
			//在途余额
			//TODO 这里的单位应该是分，暂时还没有除
			if(StringUtil.isNotEmpty(ye)){
				ztye = Double.valueOf(ye) / 100;
			} 
		}
		return ztye;
	}
	/**
	 * 获取最近一个月流入金额
	 * @param fyRsMap
	 * @return
	 */
	private static Double getFundsInput(Map<String,String> fundsMap){
		Double inAmtD = 0.0D;
		if(fundsMap != null && fundsMap.size() > 0){
			String inAmt = fundsMap.get("input");
			//在途余额
			//TODO 这里的单位是分
			if(StringUtil.isNotEmpty(inAmt)){
				inAmtD = Double.valueOf(inAmt) / 100;
			} 
		}
		return inAmtD;
	}
	/**
	 * 获取最近一个月流出金额
	 * @param fyRsMap
	 * @return
	 */
	private static Double getFundsOutput(Map<String,String> fundsMap){
		Double outAmtD = 0.0D;
		if(fundsMap != null && fundsMap.size() > 0){
			String outAmt = fundsMap.get("output");
			//在途余额
			//TODO 这里的单位是分
			if(StringUtil.isNotEmpty(outAmt)){
				outAmtD = Double.valueOf(outAmt) / 100;
			} 
		}
		return outAmtD;
	}
}
