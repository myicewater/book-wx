package com.frame.common.constant;


public class ChannelConstant {
	/** 渠道统一参数 */
	/** 字符集 固定选择值：1、2、3 1代表UTF-8; 2代表GBK; 3代表GB2312 */
	public static final String inputCharset="1";
	/** 版本号 */
	public static final String version="1.0";
	/** 数字串，由玖富平台分配 */
	public static final String channelId="1019";
	
	/**购买支付参数*/
	/**接入类型必填	1：PC接入    2：手机Wap App接入    3：手机Native App接入*/
	public static final String inputType = "1";
	/**1：连连认证支付    2：财付通支付    3：连连手机快捷支付*/
	public static final String payType = "3";
	/**银行类型*/
	public static final String bankType="";
	
	/**特权本金订单*/
	public static final String privilege_order="20";
	/**购买支付订单*/
	public static final String pay_order="10";
	/**小于50的订单*/
	public static final String less_than_fifty_order="50";
}
