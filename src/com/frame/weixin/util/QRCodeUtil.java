package com.frame.weixin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.frame.common.util.PropUtil;


public class QRCodeUtil {
	private static final Logger logger = Logger.getLogger(QRCodeUtil.class);
	/**
	 * 根据ticket 换取二维码
	 * @param ticket
	 * @param savePath
	 * @return
	 */
	public static String getQRCode(String ticket,String savePath){
		String filepath = null;
		String requestUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		requestUrl = requestUrl.replace("TICKET", CommonUtil.urlEncodeUTF8(ticket));
		try{
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			if(!savePath.endsWith("/")){
				savePath += "/";
			}
			ticket = ticket.replace("/", "_");
			//将ticket作为文件名
			filepath = savePath+ticket+".jpg";
			//将微信服务器返回的输入流写入文件
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filepath));
			byte[] buf = new byte[1024];
			int size =0;
			while((size = bis.read(buf)) != -1){
				fos.write(buf, 0, size);
				
			}
			fos.close();
			bis.close();
			conn.disconnect();
			LogUtil.printInfoLog(" 根据ticket 换取二维码成功, filepath="+filepath);
		}catch(Exception e){
			filepath = null;
			LogUtil.printLog(" 根据ticket 换取二维码失败{}",e);
		}
		return filepath;
	}
	/**
	 * 创建永久带参二维码
	 * @param accessToken
	 * @param senceId
	 * @return
	 */
	public static String createQRCode(String accessToken,int senceId){
		
		String ticket = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		//需要提交的json数据
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}}";
		//创建永久带参数二维码
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl,"POST",String.format(jsonMsg, senceId));
		if(null!=jsonObject){
			try{
				ticket = jsonObject.getString("ticket");
				logger.info(" 创建永久带参二维码成功 ticket:{}"+ ticket);
				
			}catch(Exception e){
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error(" 创建永久带参二维码失败 errcode:{"+errorCode+"} errmsg:{"+errorMsg+"}");
				
			}
		}
		return ticket;
	}
	
	private static String APPID = PropUtil.getValue("weixinAppid");
	private static String SECRET = PropUtil.getValue("weixinSecret");
	
	public static void main(String args[]){
		//运行生成二维码
		//
//		AccessToken accessToken =WeixinUtil.getAccessToken(APPID, SECRET);
//		
//		String accessTokenStr = accessToken.getToken();//CommonUtil.getToken("APPID","APPSECRET").getAccessToken();
//		//
//		String ticket = createQRCode(accessTokenStr, 617);
//		
//		logger.info("ticket:"+ticket);
		//gQHm8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2hFalk2a1RreTFHeTA1NVNQV1EtAAIE43mrVAMEAAAAAA==
		String ticket ="gQEw8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL09raFp3REhsc0dESlVzYlRPbVRvAAIEBgzHVAMEAAAAAA==";
		String savePath = "E:/tmp";
		//
		getQRCode(ticket, savePath);
		
	}

}
