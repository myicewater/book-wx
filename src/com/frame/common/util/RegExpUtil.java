package com.frame.common.util;
import java.util.regex.Pattern;

import jodd.util.StringUtil;
/**
 * 参数验证
 * 
 */
public class RegExpUtil {
	private static final String REGEX_MAIL = "^\\+?[A-Za-z0-9](([-+.]|[_]+)?[A-Za-z0-9]+)*@([A-Za-z0-9]+(\\.|\\-))+[A-Za-z]{2,6}$";
	
	/*public static boolean checkMail(String mail) {
		return Pattern.matches(mail, REGEX_MAIL);
	}*/
	
	/**
	 * 验证是否为6位数字取现密码
	 * @param pwd
	 * @return
	 */
	public static boolean checkWithDrawPwd(String pwd){
		if(StringUtil.isBlank(pwd)){
			return false;
		}
		String regex = "^\\d{6}";
		return Pattern.matches(regex, pwd);
	}
	/**
	 * 验证整数
	 * 
	 * @param integer
	 * @return
	 */
	public static boolean checkInteger(String integer) {
		if (integer == null) {
			return false;
		}
		String regex = "^-?\\d+$";
		return Pattern.matches(regex, integer);

	}

	/**
	 * 验证正整数，不包含0
	 * 
	 * @param integer
	 * @return
	 */
	public static boolean checkPositiveInteger(String integer) {
		if (integer == null) {
			return false;
		}
		String regex = "^[1-9]\\d*$";
		return Pattern.matches(regex, integer);
	}
	/**
	 * 验证订单号不包含特殊字符
	 * 
	 * @param integer
	 * @return
	 */
	public static boolean checkTeShuZiFu(String zifu) {
		if (zifu == null) {
			return false;
		}
		String regex = "^%&',;=?$\"@!";
		return Pattern.matches(regex, zifu);
	}
	

	/**
	 * 验证数字，包含小数
	 * 
	 * @param decimal
	 * @return
	 */
	public static boolean checkDecimal(String decimal) {
		if (decimal == null) {
			return false;
		}
		String regex = "^-?\\d+(\\.\\d+)?$";
		return Pattern.matches(regex, decimal);

	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if (mobile == null) {
			return false;
		}
		String regex = "^(\\+[0-9]+)?[0-9]{11}";
		return Pattern.matches(regex, mobile);
	}
	
	/**
	 * 验证邮编
	 * 
	 * @param postcode
	 * @return
	 */
	public static boolean checkPostcode(String postcode) {
		if (postcode == null) {
			return false;
		}
		String regex = "^\\d{6}$";
		return Pattern.matches(regex, postcode);

	}

	/**
	 * 验证邮箱
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean checkMail(String mail) {
		if (mail == null) {
			return false;
		}
		String regex = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		return Pattern.matches(regex, mail);
	}

	/**
	 * 验证身份证
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean checkIdCard(String idCard) {
		if (idCard == null) {
			return false;
		}
		///转成小写格式
		idCard = idCard.toLowerCase();
		//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。 
		String regex = "(^[1-9]\\d{14}$)|(^[1-9]\\d{16}[x[0-9]]{1}$)";
		return Pattern.matches(regex, idCard);
	}

	public static void main(String args[]){
		String idCard = "11111111111111111";
		System.out.println(checkIdCard(idCard));
	}
	/**
	 * 验证固话
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if (phone == null) {
			return false;
		}
		String regex = "^(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * 验证密码，合法的符号加字母和数字6-16位
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean checkPassword1(String pwd) {
		if (pwd == null) {
			return false;
		}
		String regex = "^[\\w!@#\\$%\\^&\\*\\(\\)\\+\\-=`~]{6,16}$";
		return Pattern.matches(regex, pwd);
	}

	/**
	 * 验证密码，字母和数字6-16位
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean checkPassword2(String pwd) {
		if (pwd == null) {
			return false;
		}
		String regex = "^[A-Za-z0-9]{6,16}$";
		return Pattern.matches(regex, pwd);
	}
	
	/**
	 * 验证用户名，用户由字母数字下划线汉字组成，5-25个字符， 一个汉字算两个，不支持生僻字,不能以下划线或者数字开头
	 * 
	 * @param username
	 * @return
	 */
	public static boolean checkUsername(String username) {
		if (username == null) {
			return false;
		}
		String regex = "^[a-zA-Z\\u4e00-\\u9fa5][\\u4e00-\\u9fa5\\w]{2,24}$";
		String str = username.replaceAll("[\\u4e00-\\u9fa5]", "XX");
		return Pattern.matches(regex, username) && str.length() >= 5
				&& str.length() <= 25;

	}
	
	/**
	 * 验证昵称
	 * 
	 * <p>1. 纯汉字 不超过10个	
	 * <p>2. 纯字母纯数字 字母数字组合不超过20个
	 * <p>3. 汉字数字组合或者汉字字母组合 或者汉字字母数字组合的上线为20个
	 * 
	 * @param nickName
	 * 
	 * @return
	 */
	public static boolean checkNickName(String nickName) {
		if (nickName == null)
			return false;
		
		// 纯汉字
		String regex1 = "^[\\u4e00-\\u9fa5]+$";

		// 纯字母、纯数字、字母数字组合、汉字数字组合、汉字字母组合 或者汉字字母数字组合
		String regex2 = "^[A-Za-z0-9\\u4e00-\\u9fa5]+$";

		if (Pattern.matches(regex1, nickName)) {
			if (nickName.length() <= 6)
				return true;
			else
				return false;
		}else if (Pattern.matches(regex2, nickName) && nickName.length() <= 12) {
			return true;
		}else {
			return false;
		}
	}
	
}
