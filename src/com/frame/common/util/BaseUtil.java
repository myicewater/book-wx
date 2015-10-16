package com.frame.common.util;

import java.util.regex.Pattern;

import jodd.util.StringUtil;

public class BaseUtil {

	public static String hideMobile(String mobile){
		String str = "";
		if(StringUtil.isBlank(mobile)){
			return str;
		}
		if(mobile.length() == 11){
			str = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
		}else{
			str = mobile;
		}
		return str;
	}
	public static String hideMobile5(String mobile){
		String str = "";
		if(StringUtil.isBlank(mobile)){
			return str;
		}
		if(mobile.length() == 11){
			str = mobile.substring(0, 3) + "*****" + mobile.substring(8, 11);
		}else{
			str = mobile;
		}
		return str;
	}
	/**
	 * 隐藏中间几位的邮箱  ，如823*******@qq.com
	 * 
	 * @param email
	 * @return
	 */
	public static String hideEmail(String email){
		String str = "";
		if(StringUtil.isBlank(email)){
			return str;
		}		
		
		int index = email.indexOf("@");
		if(index == -1)
			return str;
		
		if(index < 3){
			str = email.substring(0, index) + "******" + email.substring(index);
		}else{
			str = email.substring(0, 3) + "******" + email.substring(index);
		}
		return str;
	}
	
	/**
	 * 隐藏身份证号
	 * @param idCardNo
	 * @return
	 */
	public static String hideIdCardNo(String idCardNo){
		String str = "";
		if(StringUtil.isBlank(idCardNo)){
			return str;
		}
		if(idCardNo.length() == 18){
			str = idCardNo.substring(0, 8) + "******" + idCardNo.substring(14, 18);
		}else{
			str = "************" + idCardNo.substring(idCardNo.length() - 4, idCardNo.length());
		}
		return str;
	}
	/**
	 * 是否开启用户中心
	 * @return
	 */
	public static boolean isOpenSSO(){
		String flag = PropUtil.getValue("sso.open.flag");
		if("T".equals(flag)){
			return true;
		}
		return false;
	}
	
	/*public static void main(String[] args) {
		String mobile ="130681198904152215";
		
		System.out.println(BaseUtil.hideIdCardNo(mobile));
		
		//String email ="123456789@qq.com";
		
		//System.out.println(BaseUtil.hideEmail(email));
	}*/
	
	/**
	 * 手机号中间四位变星
	 * @param str
	 * @return
	 */
	public static String stringChangeStars(String str) {
		return str = str.substring(0, 4) + "********" + str.substring(12, str.length());
	}
	
	/**
	 * 获取银行卡的后4位
	 * @param bankCard
	 * @return
	 */
	public static String getBankCardLast4(String bankCard) {
		String str = bankCard.replace(" ", "");
		return str.substring(str.length() - 4, str.length());
	}
	
	/**
	 * 密码(以字母开头，长度在6~20之间，只能包含字母、数字和下划线)
	 * @param pwd
	 * @return
	 */
	public static boolean checkPassword(String pwd) {
		if (pwd == null) {
			return false;
		}
		String regex = "^(?!\\d+$)(?![A-Za-z]+$)[a-zA-Z0-9]{6,20}$";
		return Pattern.matches(regex, pwd);
	}
}
