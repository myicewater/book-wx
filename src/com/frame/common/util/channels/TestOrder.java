package com.frame.common.util.channels;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.frame.common.util.DateUtil;
import com.frame.common.util.OrderIdUtil;

/**
 * 测试下单接口
 * @author zhangshaoliang
 * 2015-6-12上午11:50:31
 */
public class TestOrder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		///注册
		Map register = TestRegister.testRegister();
		System.out.println(register+"\n");
		
		//下单
		String orderNo = OrderIdUtil.createNewOrdId();
		Map order = TestOrder.testOrder(register.get("memberId")+"", orderNo);
		System.out.println(order+"\n");
	}

	public static Map testOrder(String jf_memberId,String orderNo){
		Map<String,String> mapParam = new HashMap<String,String>();
		mapParam.put("memberId", jf_memberId);//玖富理财的用户id
		mapParam.put("productId", "162");
		mapParam.put("orderNo", orderNo);
		mapParam.put("orderTime", DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		mapParam.put("amount","0.01");
		mapParam.put("orderSts", "0");
		mapParam.put("interestDays", "0");
		
		return APIUtils.sendPost("http://182.92.169.65:8895/wlcapi/general2/{utmSource}/ordering.html", mapParam);
	}
}
