package com.frame.weixin.template;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.frame.common.util.PropUtil;

public class MsgTemplate {
	
	/**
	 * 获取验证身份消息
	 * @param fromUserName
	 * @param nickName
	 * @return
	 */
	public static String getYzsfMsg(String fromUserName, String nickName){
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("移动互联网理财轻松享,已注册用户验证身份即可开通以下功能：\n")
		.append(" \n ")
		.append("1. 购买优质理财计划；\n ")
		.append("2. 查询投资记录资金流水；\n ")
		.append("3. 查询维护个人信息；\n ")
		.append("4. 理财动态早知道 ；\n");
//		.append("\n ")
//		.append("<a href=\"http://218.241.201.156/9fbank-weiM/login/bound.htm?openId=").append(fromUserName).append("&nickName=").append(nickName).append("\">点击立即验证身份</a> \n ")
//		.append("\n ")
//		.append("您将进入玖富微理财官方验证页面,安全可靠。\n ")
//		.append("\n ")
//		.append("还不是微理财注册用户？ \n")
//		.append("\n")
//		.append("<a href=\"http://218.241.201.156/9fbank-weiM/login/registerInit.htm\">立即注册</a> \n")
//		.append("\n ");
		return msgBuf.toString();
	}
	
	/**
	 * 验证身份成功后的消息
	 * @return
	 */
	public static String getValidateSuccessMsg(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("身份验证成功\n")
		//.append("\n")
		.append(sdf.format(new Date()) +"\n")
		//.append("\n")
		.append("您会收到您的每一笔理财投资的微信提醒。\n ")
		.append("\n")
		.append("点击下方菜单,购买微理财优质理财计划、查询投资记录资金流水、查询维护个人信息、参与微理财热门活动。\n")
		.append("\n")
		.append("欢迎使用玖富微理财官方微信服务,欢迎您的加入！\n");
		return msgBuf.toString();
	}
	
	/**
	 * 组装投资记录数据
	 * @param tzjlMap
	 * @return
	 * @throws ParseException 
	 */
	public static String getTzjlMsg(Map tzjlMap,Map profitMap) {
		StringBuffer msgBuf = new StringBuffer();
		try {
			String cyzCount = String.valueOf(tzjlMap.get("cyzcount"));
			Double cyzAmount = Double.valueOf(String.valueOf(tzjlMap.get("cyzamount")));
			String ytcCount = String.valueOf(tzjlMap.get("ytccount"));
			Double ytcAmount = Double.valueOf(String.valueOf(tzjlMap.get("ytcamount")));
			String ddCount = String.valueOf(tzjlMap.get("ddocount"));
			Double ddAmount = Double.valueOf(String.valueOf(tzjlMap.get("ddamount")));
			DecimalFormat df = new DecimalFormat("#,##0.00");
			
			
			String cyzProfit = String.valueOf(profitMap.get("cyzProfit"));
			String ddProfit = String.valueOf(profitMap.get("ddProfit"));
			String tcProfit = String.valueOf(profitMap.get("tcProfit"));
			
			msgBuf.append("您当前名下投资记录：\n")
			.append("\n")
			.append("待确认理财计划：\n")
			.append("       计划个数：").append(ddCount).append("个 \n")
			.append("       预期收益：￥").append(ddProfit).append("\n")
			.append("       投资金额：￥").append(df.format(ddAmount)).append("\n")
			.append("\n")
			.append("持有中理财计划：\n")
			.append("       计划个数：").append(cyzCount).append("个 \n")
			.append("       预期收益：￥").append(cyzProfit).append("\n")
			.append("       投资金额：￥").append(df.format(cyzAmount)).append("\n")
			.append("\n")
			.append("已退出理财计划： \n")
			.append("       计划个数：").append(ytcCount).append("个\n")
			.append("       预期收益：￥").append(tcProfit).append("\n")
			.append("       投资金额：￥").append(df.format(ytcAmount)).append("\n")
			.append("\n")
			.append("点击查看全文,可查看名下投资记录详情。\n");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return msgBuf.toString();
	}
	
	/**
	 * 拼装资金流水消息
	 * @param kyye 可用余额	
	 * @param ztye 在途余额
	 * @param monthIn 最近一个月总流入
	 * @param monthOut 最近一个月总流出
	 * @return
	 */
	public static String getZjlsMsg(Double kyye, Double ztye, Double monthIn, Double monthOut){
		DecimalFormat df = new DecimalFormat( "#,##0.00");
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("\n")
		.append("当前您的微理财账户下\n")
		//.append("\n")
		.append("       到账资金总额：￥")
		.append(df.format(kyye))
		.append("\n")
		.append("       充值在途资金总额：￥")
		.append(df.format(ztye))
		.append("\n")
		.append("\n")
		.append("最近一个月 \n")
		//.append("\n")
		.append("       资金总流入：￥")
		.append(df.format(monthIn))
		.append("\n")
		.append("       资金总流出：￥")
		.append(df.format(monthOut))
		.append("\n")
		.append("\n")
		.append("点击查看全文,可查看名下资金流水详情。 ");
		return msgBuf.toString();
	}
	
	/**
	 * 获取微理财官网的返回信息
	 * @return
	 */
	public static String getWlcGwMsg(String boundName){
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("您可以使用您的注册账号\n")
		.append("（"+boundName+"）\n ")
		.append("登录微理财官网\n ")
		.append("welicai.9fbank.com,\n")
		.append("可以直接了解优质理财计划详情、")
		.append("开通富友账户充值购买理财、")
		.append("查询资金流水、")
		.append("投资记录等操作,")
		.append("建议使用电脑登录,体验更佳。 ");
		return msgBuf.toString();
	} 
	
	/**
	 * 获取关注微信后发送给用户的消息
	 * @param toUserName  公众号ID
	 * @param fromUserName 用户openID
	 * @returnhttp://124.205.181.93/wlcWx/
	 */
	public static String getSubscribeMsg(String toUserName,String fromUserName){
		//获取项目路径
		String path = PropUtil.getValue("weixinServerAddr");
		StringBuilder sb = new  StringBuilder();
		sb.append("书山有路勤为径，\n学海无涯苦作舟。\n欢迎关注书为径！\n惊喜正在等待你！");
		
		
		 StringBuffer str = new StringBuffer();  
         str.append("<xml>");  
         str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");  
         str.append("<FromUserName><![CDATA[" +toUserName + "]]></FromUserName>");  
         str.append("<CreateTime>12345678</CreateTime>");  
         str.append("<MsgType><![CDATA[text]]></MsgType>");  
         str.append("<Content><![CDATA["+sb.toString()+"]]></Content>");  
         str.append("</xml>"); 
		return str.toString();
		//return str;
	}
	
}
