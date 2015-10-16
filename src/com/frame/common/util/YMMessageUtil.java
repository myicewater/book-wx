package com.frame.common.util;

import com.frame.common.util.PropUtil;

import sms.Client;

public class YMMessageUtil {
	private static YMMessageUtil message=new YMMessageUtil();
	private static String content="玖富默认短信提示";
	
	/**
	 * @author 胡萌
	 * result 0短信发送成功 
	 * @param 
	 * con 发送的内容
	 * mobile 手机号
	 * @return
	 * @throws java.lang.Exception 
	 */
	public static String sendMessage(String con,String mobile) throws java.lang.Exception{
		String result="0";
		if(con==null||con.trim().equals("")){
			con=content;
		}
		if(message==null){
			message=new YMMessageUtil();
		}
		String key=PropUtil.getValue("cms_key");
		String pwd=PropUtil.getValue("cms_pwd");
		if(key==null||key==pwd){
			throw new java.lang.Exception("配置文件未配置对应值");
		}
		if(mobile==null){
			throw new java.lang.Exception("手机号码不能为空");
		}
		result = new Client(key, pwd).SmsSend(mobile,"【辣妈钱包】"+con,"","");
		return result;
	}
	
	/*private static void getBalance(){
		String key=PropUtil.getValue("cms_key");
		String pwd=PropUtil.getValue("cms_pwd");
		//String url="http://sdk4report.eucp.b2m.cn:8080/sdkproxy/querybalance.action?cdkey="+key+"&password="+pwd;
		String url="http://sdk4report.eucp.b2m.cn:8080/sdkproxy/regist.action?cdkey="+key+"&password="+pwd;
	}*/
	
	/*public static void main(String[] args) {
		//易美短信注册
		http://sdk4report.eucp.b2m.cn:8080/sdkproxy/regist.action?cdkey=您的序列号&password=您的密码

	}*/
	
}
