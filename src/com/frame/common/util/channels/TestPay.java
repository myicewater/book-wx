package com.frame.common.util.channels;

import java.util.HashMap;
import java.util.Map;

import com.frame.common.util.OrderIdUtil;

/*
 * 测试支付接口
 */
public class TestPay {
	private static String API = "http://182.92.169.65:8895/wlcapi/general2/{utmSource}/payment.html";
//	用户身份证号
	private static String idCard = IdentificationValidator.idCard;
//	用户姓名
	private static String idName = IdentificationValidator.idName;
//	渠道方用户唯一标示
	private static String userId = IdentificationValidator.userId;
	private static String orderNo = "1314520";
	
	private static String memberId = "12";
	private static String mobile = "13811487450";
	
	private static String orderStatus = "0";
	private static String amount = "0.01";
	private static String productId = "162";
	
	
	private static String payType = "3";
	private static String bankType = "中国建设银行";
	private static String bankCardNo = "6227000014240147623";
	
	
	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		///注册
		Map register = APIUtils.register("http://182.92.169.65:8895/wlcapi/general2/{utmSource}/register.html", memberId, mobile, idCard, idName);//TestRegister.testRegister();
		System.out.println(register+"\n");
		
		//下单
		orderNo = OrderIdUtil.createNewOrdId();
		String orderAPI = "http://182.92.169.65:8895/wlcapi/general2/{utmSource}/ordering.html";
		Map order = APIUtils.order(orderAPI, register.get("memberId")+"", productId, amount, orderStatus, "0", orderNo, null, null);//TestOrder.testOrder(register.get("memberId")+"", orderNo);
		System.out.println(order+"\n");
		
		String payAPI = "http://182.92.169.65:8895/wlcapi/general2/{utmSource}/payment.html";
		Map pay = APIUtils.payment(payAPI, payType, productId, amount, bankType, bankCardNo, "0", idCard, idName, orderNo, register.get("memberId")+"", null, null);
		//、购买
		System.out.println(pay);
	}
	
	@SuppressWarnings("rawtypes")
	public static Map payment(String idCard,String idName,String orderNo,String memberId){
		Map<String,String> dataMap  = new HashMap<String, String>();
		//1：PC接入		2：手机Wap App接入		3：手机Native App接入
		dataMap.put("inputType", "1");
		//1：连连认证支付		2：财付通支付		3：连连手机快捷支付
		dataMap.put("payType", "3");
		dataMap.put("memberId", memberId);
		dataMap.put("productId", "162");
		dataMap.put("orderNo", orderNo);
		//购买金额
		dataMap.put("amount", "0.01");
		//默认到期处理方式A01不支持续投B01本金续投B02本息续投B03到期兑付
//		dataMap.put("continueStatus", "B02");
		dataMap.put("idCard", idCard);
		dataMap.put("idName", idName);
		///签约协议号	非必须	认证支付有效
//		dataMap.put("agreeNo", value);
		//银行类型、网银支付有效，银行名称     招商银行               03080000
		dataMap.put("bankType", "招商银行");
		//银行卡号、认证支付需要   
		dataMap.put("bankCardNo", "6214830115754052");
		//为1是购买后的第一个工作日开始计算利息，为2时购买后的第二个工作日开始计算利息，以此类推••••••
		dataMap.put("interestDays", "0");
		///使用统一玖富api调用工具类进行调用
		return APIUtils.sendPost(API, dataMap);
	}
	
	
	
}
