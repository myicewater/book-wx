package com.frame.common.util.channels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.TypeReference;
import com.frame.common.util.DateUtil;
import com.frame.common.util.JsonUtil;

/**
 * 玖富理财渠道接口调用工具类
 * @author zhangshaoliang
 * 2015-6-9下午12:00:45
 */
public class APIUtils {
	private static Logger logger = Logger.getLogger(IdentificationValidator.class);
	public static String utmSource = "LMLC";
//	1代表UTF-8; 2代表GBK; 3代表GB2312
	private static String inputCharset="1";
//	#接口版本
	private static String version="1.0";
//	#数字串，由玖富平台分配
	private static String channelId="1019";
	
	

	/**
	 * 调用玖富实名认证接口，判断身份证和用户名是否一致
	 * @param idCard
	 * @param idName
	 * @param memberId
	 * @return null 如果异常，否则返回实名认证结果map数据
	 */
	@SuppressWarnings("rawtypes")
	public static Map validateID(String API,String idCard,String idName,String memberId){
		Map<String,String> dataMap  = new HashMap<String, String>();
		dataMap.put("inputCharset", inputCharset);
		dataMap.put("version", version);
		dataMap.put("idType", "0");
		dataMap.put("idCard", idCard);
		dataMap.put("idName", idName);
		dataMap.put("userId", memberId);
		///使用统一玖富api调用工具类进行调用
		return sendPost(API, dataMap);
	}
	
	/**
	 * <pre>用户在合作渠道购买产品时，调用此接口把用户信息传送给微理财。微理财根据传来的数据，
	 * 对于未开户的用户进行微理财平台开户，开户成功后返回用户标识和返回代码；
	 * 如果用户已经在微理财开户，则直接返回用户标识和返回代码。</pre>
	 * @param	API 注册API接口地址，如<pre>http://182.92.169.65:8895/wlcapi/general2/{utmSource}/productInfo.html</pre>
	 * @return	返回结果map{内容详见API文档}
	 */
	public static Map register(String API,String memberId,String mobile,String idCard,String idName){
		Map<String,String> mapParam = new HashMap<String,String>();
		mapParam.put("userId", memberId);
		mapParam.put("mobile", mobile);
		mapParam.put("idType", "0");
		mapParam.put("idCard", idCard);
		mapParam.put("idName", idName);
		/**
		 * 调用渠道注册接口
		 */
		return sendPost(API, mapParam);
	}
	
	/**
	 * <pre>用户通过合作渠道购买理财产品，通过购买下单接口在玖富平台生成用户的购买订单；
	 * 如果渠道端有对应的支付渠道，直接在渠道端完成支付后，通过下单接口发送支付成功的订单到玖富平台；
	 * 如果渠道端没有对应的支付渠道，直接通过下单接口发送未支付订单到玖富平台，由玖富平台完成后续的支付功能。</pre>
	 * @param API				下单API接口地址，如<pre>http://182.92.169.65:8895/wlcapi/general2/{utmSource}/productInfo.html</pre>
	 * @param jf_memberId 		微理财用户ID
	 * @param productId			产品Id
	 * @param amount			购买金额
	 * @param orderStatus		订单状态	,0：下单成功未支付 1：下单同时支付成功
	 * @param interestDays		计息所需天数	,为1是购买后的第一个工作日开始计算利息,类推....
	 * @param expandProfit		非必须(可传null)，额外收益率
	 * @param orderNo			订单号
	 * @param continueStatus	非必须(可传null)，默认到期处理方式A01不支持续投B01本金续投B02本息续投B03到期兑付
	 * @return	返回结果map{内容详见API文档}
	 */
	public static Map order(String API,String jf_memberId,String productId,String amount,String orderStatus,String interestDays, String orderNo,String expandProfit,String continueStatus){
		Map<String,String> mapParam = new HashMap<String,String>();
		mapParam.put("memberId", jf_memberId);//玖富理财的用户id
		mapParam.put("productId", productId);
		mapParam.put("orderNo", orderNo);
		mapParam.put("orderTime", DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		mapParam.put("amount",amount);
		mapParam.put("orderSts", orderStatus);
		mapParam.put("interestDays", interestDays);
		if(!StringUtil.isBlank(expandProfit)){
			mapParam.put("expandProfit", expandProfit);
		}
		if(!StringUtil.isBlank(continueStatus)){
			mapParam.put("continueStatus", continueStatus);
		}
		/**
		 * 调用渠道进行下单
		 */
		return sendPost(API, mapParam);
	}
	
	/**
	 * <pre> 用户在渠道合作伙伴购买成功后，通过PC端或手机APP接入后，
	 * 玖富渠道平台判断接入方式分别采用不同的支付方式进行用户购买理财产品的支付服务
	 * ，支付成功后同步返回渠道合作伙伴支付成功通知。</pre>
	 * @param API
	 * @param payType			1：连连认证支付	 2：财付通支付	3：连连手机快捷支付
	 * @param productId			产品Id
	 * @param amount			购买金额
	 * @param bankType			银行名字
	 * @param bankCardNo		银行卡号码
	 * @param interestDays		计息所需天数	,为1是购买后的第一个工作日开始计算利息,类推....
	 * @param idCard			身份证号
	 * @param idName			用户身份证对应的名字
	 * @param orderNo			订单号
	 * @param jf_memberId 		微理财用户ID
	 * @param continueStatus	非必须(可传null)，默认到期处理方式A01不支持续投B01本金续投B02本息续投B03到期兑付
	 * @param agreeNo			非必须(可传null)，签约协议号，认证支付有效
	 * @return	<pre>将加密后的data、sign以及API地址放入map里面返回</pre>
	 */
	@SuppressWarnings("rawtypes")
	public static Map payment(String API, String payType, String productId,
			String amount, String bankType, String bankCardNo,
			String interestDays, String idCard, String idName, String orderNo,
			String jf_memberId,String continueStatus,String agreeNo) {
		Map<String, String> dataMap = new HashMap<String, String>();
		// 1：PC接入 2：手机Wap App接入 3：手机Native App接入
		dataMap.put("inputType", "1");
		// 1：连连认证支付 2：财付通支付 3：连连手机快捷支付
		dataMap.put("payType", payType);
		dataMap.put("memberId", jf_memberId);
		dataMap.put("productId", productId);
		dataMap.put("orderNo", orderNo);
		// 购买金额
		dataMap.put("amount", amount);
		// 默认到期处理方式A01不支持续投B01本金续投B02本息续投B03到期兑付
		// dataMap.put("continueStatus", "B02");
		dataMap.put("idCard", idCard);
		dataMap.put("idName", idName);
		// /签约协议号 非必须 认证支付有效
		// dataMap.put("agreeNo", value);
		// 银行类型、网银支付有效，银行名称 招商银行 等
		dataMap.put("bankType", bankType);
		// 银行卡号、认证支付需要
		dataMap.put("bankCardNo", bankCardNo);// "6214830115754052");
		// 为1是购买后的第一个工作日开始计算利息，为2时购买后的第二个工作日开始计算利息，以此类推••••••
		dataMap.put("interestDays", interestDays);
		// /使用统一玖富api调用工具类进行调用

		if(!StringUtil.isBlank(agreeNo)){
			dataMap.put("expandProfit", agreeNo);
		}
		if(!StringUtil.isBlank(continueStatus)){
			dataMap.put("continueStatus", continueStatus);
		}
		/**
		 * 调用渠道进行支付
		 */
		return sendPost(API, dataMap);
	}
	
	/**
	 * 调用玖富渠道接口API
	 * @param API       玖富API接口地址，如<pre>http://182.92.169.65:8895/wlcapi/general2/{utmSource}/productInfo.html</pre>
	 * @param dataMap	参数map
	 * @return			<pre>1、返回结果map{内容详见API文档} </pre><pre>2、如果是支付请求，则将加密后的data、sign以及API地址放入map里面返回</pre>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map sendPost(String API,Map<String,String> dataMap){
	
		////由于加密后的数据带有很多特殊字符，采用get请求方法可能会导致参数字符被转义，所以用httpClient的post请求; 
		if(API.contains("{utmSource}")){
			API = API.replace("{utmSource}", utmSource);////如果是包含待替换的数字串，则替换掉
		}
		//设置必要参数
		if(StringUtil.isBlank(dataMap.get("channelId"))){
			dataMap.put("channelId", channelId);
		}
		if(StringUtil.isBlank(dataMap.get("version"))){
			dataMap.put("version", version);
		}
		if(StringUtil.isBlank(dataMap.get("inputCharset"))){
			dataMap.put("inputCharset", inputCharset);
		}
		///转成json数据后，进行RSA加密、加签
		String json = JsonUtil.toJson(dataMap);
		String data = SDKUtil.encryptData(json);
		String sign = SDKUtil.generateSignature(data);
		if(API.contains("payment")){
			try {
				///用base64再次处理data和sign
				data = base64Encoder(data, "utf-8");
				sign = base64Encoder(sign, "utf-8");
				/////如果是支付请求，则将加密后的data、sign以及API返回
				Map resultData = new HashMap<String, Object>();
				resultData.put("data", data);
				resultData.put("sign", sign);
				System.out.println(API+"?data="+resultData.get("data")+"&sign="+resultData.get("sign"));
				resultData.put("api", API);
//				String idStr =  sendPostRequest(API, resultData);
//				System.out.println(idStr);
				return resultData;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error("加密支付参数异常");
				return null;
			}
		}
		
		//封装参数data和sign
		Map<String,String> params  = new HashMap<String, String>();
		params.put("data", data);
		params.put("sign", sign);
		
		System.out.println(API+"?data="+params.get("data")+"&sign="+params.get("sign"));
		
		String idStr = null;
		try {
			idStr = sendPostRequest(API, params);// httpPost(API, params);
		} catch (Exception e) {
			logger.error("渠道接口异常");
			e.printStackTrace();
			return null;
		}
		
		return decodeByRSA(idStr);
	}
	
	/**
	 * 对json数据串进行rsa解密
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map decodeByRSA(String json){
		try{
			Map map = JsonUtil.toObject(json,new TypeReference<HashMap>(){});
//			if((SDKUtil.verifySignature(map.get("data").toString(), map.get("sign").toString()))){
//				//验签通过，则继续，也可不验签
//			}
			String result = SDKUtil.decryptData(map.get("data").toString());
			Map resultData = JsonUtil.toObject(result,new TypeReference<HashMap>(){});
			return resultData;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("渠道接口出错,返回数据异常");
			return null;
		}
	}
	
	/**
	 * 对Map数据串行rsa解密
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map decodeByRSA(Map map){
		try{
			String result = SDKUtil.decryptData(map.get("data").toString());
			Map resultData = JsonUtil.toObject(result,new TypeReference<HashMap>(){});
			return resultData;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("渠道接口出错,返回数据异常");
			return null;
		}
	}
	/**
	 * 对Map数据串行rsa解密
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map decodeByRSA_base64(Map map){
		try{
			String data = (String) map.get("data");
			String sign = (String) map.get("sign");
			
			data = new String(new BASE64Decoder().decodeBuffer(data));
			sign = new String(new BASE64Decoder().decodeBuffer(sign));
			
			String result = SDKUtil.decryptData(data);
			Map resultData = JsonUtil.toObject(result,new TypeReference<HashMap>(){});
			return resultData;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("渠道接口出错,返回数据异常");
			return null;
		}
	}
	/**
	 * 先rsa加密，再base64处理
	 * @param parasMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String encodeByRSA(Map parasMap){

		///转成json数据后，进行RSA加密、加签
		String json = JsonUtil.toJson(parasMap);
		String data = SDKUtil.encryptData(json);
		String sign = SDKUtil.generateSignature(data);
		/*
		///用base64再次处理data和sign
		try {
			data = base64Encoder(data, "utf-8");
			sign = base64Encoder(sign, "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("在使用encodeByRSA_base64加密异常");
		}*/
		
		/////如果是支付请求，则将加密后的data、sign以及API返回
		Map resultData = new HashMap<String, Object>();
		resultData.put("data", data);
		resultData.put("sign", sign);
		
		return JsonUtil.toJson(resultData);
	}
	
	/**
	 * BASE64编码
	 * 
	 * @param src
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String base64Encoder(String src, String charset)
			throws Exception {
		if (src == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(src.getBytes(charset));
	}
	/**
	 * 发送httpURLConnection请求，并得到相应结果字符串(由于加密后的数据带有很多特殊字符，采用这种请求方法可能会导致参数字符被转义）
	 * @param url
	 * @param params 只能是String类型的
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url,Map<String,String> params) throws IOException{
		URL u = null;
		HttpURLConnection connection = null;
		StringBuilder sb = new StringBuilder();
		for(String key:params.keySet()){
			sb.append(key);
			sb.append("=");
			sb.append(params.get(key));
			sb.append("&");
		}
		
		u = new URL(url);
		connection = (HttpURLConnection) u.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
		
		OutputStream out = connection.getOutputStream();
		out.write(sb.toString().getBytes());
		out.flush();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
		StringBuilder buffer = new StringBuilder();
		String temp = null;
		while((temp=reader.readLine())!=null){
			buffer.append(temp).append("\n");
		}
		
		return buffer.toString();
	}

	
	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL,Map<String, String> params, String encodeCharset,String decodeCharset) {
		logger.debug("请求接口地址：" + reqURL);
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 创建参数队列
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formParams,
					encodeCharset == null ? "UTF-8" : encodeCharset));

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
			logger.debug("接口返回数据：" + responseContent);
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	public static String sendPostRequest(String reqURL,
			Map<String, String> params) {
		return sendPostRequest(reqURL, params, null, null);
	}
}
