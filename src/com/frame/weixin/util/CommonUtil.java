package com.frame.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
//import org.json.simple.JSONObject;


public class CommonUtil {
	private static final Logger logger = Logger.getLogger(CommonUtil.class);
	public final static String token_url="http://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&";
	public static String urlEncodeUTF8(String source){
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static JSONObject httpsRequest(String requestUrl,String requestMethod,String outputStr){
		JSONObject jsonObject =null;
		try{
			TrustManager[] tm ={new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			//
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			//
			conn.setRequestMethod(requestMethod);
			
			//
			if(null!=outputStr){
				OutputStream outputStream = conn.getOutputStream();
				//utf
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			//
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while((str=bufferedReader.readLine())!=null){
				buffer.append(str);
			}
			//释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream =null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		}catch(ConnectException ce){
			logger.error("连接超时：{}",ce);
			
		}catch(Exception e){
			logger.error("https请求异常：{}",e);
			
		}
		return jsonObject;
	}
}
