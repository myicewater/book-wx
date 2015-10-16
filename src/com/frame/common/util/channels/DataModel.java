package com.frame.common.util.channels;

public class DataModel {
	private static String API = "http://182.92.169.65:8895/wlcapi/general2/NLM009/idValidate.html";
//	1代表UTF-8; 2代表GBK; 3代表GB2312
	private static String inputCharset="1";
//	#接口版本
	private static String version="1.0";
//	#数字串，由玖富平台分配
	private static String channelId="1019";
//	0 身份证 (目前只支持身份证)
	private static String idType="0";
	
	
//	用户身份证号
	private static String idCard = "4312231991030205815";
//	用户姓名
	private static String idName = "张少亮";
//	渠道方用户唯一标示
	private static String userId = "13";
}
