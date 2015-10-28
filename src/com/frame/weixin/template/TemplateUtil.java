package com.frame.weixin.template;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.weixin.thread.TokenThread;
import com.frame.weixin.util.MyX509TrustManager;
import com.frame.weixin.util.WeixinUtil;

public class TemplateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateUtil.class);

	/**
	 * 
	 * @param toUser
	 * @param template
	 */
	public static JSONObject sendTemplateMsg(String titleValue ,String contentValue,String templateId, String toUser,String url){
		
		Template t =new Template();
		TemplateData td = new TemplateData();
		TemplateItem content = new TemplateItem();
		TemplateItem title = new TemplateItem();
		
		title.setColor("#173177");
		title.setValue(titleValue);
		content.setColor("#173177");
		content.setValue(contentValue);
		
		td.setContent(content);
		td.setTitle(title);
		
		t.setData(td);
		t.setTemplate_id(templateId);
		t.setTouser(toUser);
		t.setUrl(url);
		
		
		String msg = JSONObject.fromObject(t).toString();
		String accessToken = TokenThread.accessToken.getToken();
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
		logger.info("模板消息："+msg);
		logger.info("accessToken:"+accessToken);
		
		JSONObject jsonobj = WeixinUtil.httpRequest(requestUrl, "POST", msg);
		
		
        logger.info("发送模板消息结果："+jsonobj.toString());
		return jsonobj;
		
	}
	
	
	public static void main(String[] args) {
		Template t =new Template();
		TemplateData td = new TemplateData();
		TemplateItem content = new TemplateItem();
		TemplateItem title = new TemplateItem();
		
		title.setColor("#173177");
		title.setValue("锄禾日当午");
		content.setColor("#173177");
		content.setValue("汗滴禾下土");
		
		td.setContent(content);
		td.setTitle(title);
		
		t.setData(td);
		t.setTemplate_id("UpdD76XBWd4XRFZK824fcU0gmbe5lEBUES1RFs2kmsM");
		t.setTouser("o36rev-R6h79UWwdc03benr70smI");
		t.setUrl("http://www.yizhifan.com");
		
		//sendTemplateMsg(t);
		
	}
}
