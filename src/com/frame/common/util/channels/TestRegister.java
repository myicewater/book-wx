package com.frame.common.util.channels;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试注册接口
 * @author zhangshaoliang
 * 2015-6-12上午11:51:09
 */
public class TestRegister {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		///注册
		Map register = TestRegister.testRegister();
		System.out.println(register+"\n");
	}

	public static Map testRegister(){
		Map<String,String> mapParam = new HashMap<String,String>();
		mapParam.put("userId", IdentificationValidator.userId);
		mapParam.put("mobile", "15072387205");
		mapParam.put("idType", "0");
		mapParam.put("idCard", IdentificationValidator.idCard);
		mapParam.put("idName", IdentificationValidator.idName);
		
		/**
		 * 调用渠道注册接口
		 */
		return APIUtils.sendPost("http://182.92.169.65:8895/wlcapi/general2/{utmSource}/register.html", mapParam);
	}
}
