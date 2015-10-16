package com.frame.weixin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeixinPicUrlUtil {
	/**
	 * 根据保存微信头像图片
	 * @param ticket
	 * @param savePath
	 * @return
	 */
	public static String savePic(String requestUrl,String savePath,String openId){
		String filepath = null;
		try{
			URL url = new URL(requestUrl);
//			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			HttpURLConnection conn =(HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			if(!savePath.endsWith("/")){
				savePath += "/";
			}
			//将ticket作为文件名
			filepath = savePath+openId+".jpg";
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
			LogUtil.printInfoLog(" 微信头像获取成功, filepath="+filepath);
		}catch(Exception e){
			filepath = null;
			LogUtil.printLog(" 微信头像获取失败{}",e);
		}
		return filepath;
	}
	
	public static void main(String args[]){
		String savePath = "E:/tmp";
		//
		String requestUrl="http://wx.qlogo.cn/mmopen/bxLfn3MQI9Rfqicmp5WOk1w1UKhBicrRPE9rK4TIbXEeQmOFuCiaWPia9eFq03fzdLkbMYOw7nyacVzRnEw3uul9xyoaQhpncNpic/0";
		//http://wx.qlogo.cn/mmopen/bxLfn3MQI9Rfqicmp5WOk1w1UKhBicrRPE9rK4TIbXEeQmOFuCiaWPia9eFq03fzdLkbMYOw7nyacVzRnEw3uul9xyoaQhpncNpic/0
		String openId="oLEjns14g1XKeydAOWMIqGtNDtF8";
		savePic(requestUrl, savePath, openId);
	}

}
