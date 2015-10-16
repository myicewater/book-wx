package com.frame.weixin.util;

import java.security.MessageDigest;
import java.util.Arrays;

import com.frame.common.util.PropUtil;
import com.frame.weixin.pojo.JsapiTicket;
import com.frame.weixin.thread.TokenThread;

/**
 * 创建signature
 *
 */

public class CreatSignature {
	private static String APPID = PropUtil.getValue("weixinAppid");
	private static String SECRET = PropUtil.getValue("weixinSecret");
	private final static String jsapi_ticket_url = " https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=TOKEN&type=jsapi";
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取signature
	 * @param token
	 * @param time
	 * @param nonceStr
	 * @param URL
	 * @return
	 */
	public static String getSignature(String token, Long time,
			String nonceStr, String URL) {
//		String url = jsapi_ticket_url.replace("TOKEN", token);
//		JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
//		String jsapi_ticket = null;
//		if (jsonObject != null || "".equals(jsonObject)) {
//			jsapi_ticket = (String) jsonObject.get("ticket");
//
//		}
		JsapiTicket jsapiTicket = TokenThread.jsapi_ticket;
		String jsapi_ticket = jsapiTicket.getTicket();
		String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket,
				"timestamp=" + time, "noncestr=" + nonceStr, "url=" + URL };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String String1 = paramArr[0].concat("&" + paramArr[1])
				.concat("&" + paramArr[2]).concat("&" + paramArr[3]);

		String sha1String = encode(String1);
		return sha1String;
	}

	/**
	 * 把密文转换成十六进制的字符串形式
	 * @param bytes
	 * @return
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * SHA1加密
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
