package com.frame.weixin.util;

import org.apache.log4j.Logger;

import utils.MD5JM;
import cn.entinfo.WebService;
import cn.entinfo.WebServiceSoap;


/**
 * 手机发送
 * @author Administrator
 */
public class SmsSendUtil {
	
	private static final Logger logger = Logger.getLogger(SmsSendUtil.class);

	
	/**
	 * 发送验证码
	 * @param mobile 手机号
	 * @return
	 */
	public static String sendValidCode(String mobile, String validCode){
		String result_mt="";
		try {
			logger.info("-----------------------发送短信开始------------------------------");
			String sn = "SDK-WSS-010-05956";
			String pwd = "a-d5ab-[";
			logger.info("向手机号"+ mobile + "发送短信验证码"+validCode);
			String content = "谢天谢地您来了！您的验证码：" + validCode + "，欢迎注册玖富理财【玖富微理财】";
			WebService factory = new WebService();
			WebServiceSoap webServiceSoap = factory.getWebServiceSoap();
			result_mt = webServiceSoap.mdsmssend(sn,MD5JM.getMD5(sn+pwd), mobile, content, "", "", "", "");
			
			if(result_mt.startsWith("-")||result_mt.equals(""))//发送短信，如果是以负号开头就是发送失败。
			{
				logger.info("发送失败！返回值为："+result_mt+"请查看webservice返回值对照表");
				return "-1";
			}
			else
			{
				logger.info("发送成功，返回值为："+result_mt);
			}
		} catch (Exception e) {
			logger.error("发送验证码错误",e);
		}
		return result_mt;
	}
	
	public static void main(String[] args) {
		//sendMsg("15201236821","今天能看球赛了");
		//SmsSendUtil.sendMsg("18710129113", MyConstant.TEMPLATEID_2423, new String[]{"123456"});
		//sendMsg("15010897330","2376",new String[]{"500.00"});
		//17701138484
		String validCode = RandomCode.getRandomString();
		new SmsSendUtil().sendValidCode("17710058646", validCode);
	}
	
	
	
	
}
