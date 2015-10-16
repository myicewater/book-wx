package com.frame.common.util.channels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.TypeReference;
import com.frame.common.util.JsonUtil;

/**
 * 实名认证工具类
 * @author zhangshaoliang
 * 2015-6-4下午8:49:15
 */
public class IdentificationValidator {
	
	private static Logger logger = Logger.getLogger(IdentificationValidator.class);
	
	private static String API = "http://182.92.169.65:8895/wlcapi/general2/LMLC/idValidate.html";//http://yeqiangwei.wicp.net:47074/wlc-channel/wlcapi/general2/NLM009/idValidate.html";
//	1代表UTF-8; 2代表GBK; 3代表GB2312
	private static String inputCharset="1";
//	#接口版本
	private static String version="1.0";
//	#数字串，由玖富平台分配
	private static String channelId="1019";
//	0 身份证 (目前只支持身份证)
	private static String idType="0";
	
	
//	用户身份证号
	public static String idCard = "420922198901160039";
//	用户姓名
	public static String idName = "李腾海子";
//	渠道方用户唯一标示
	public static String userId = "13";
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		System.out.println(validateID(idCard, idName, userId));
	}
	/**
	 * 调用玖富实名认证接口，判断身份证和用户名是否一致
	 * @param idCard
	 * @param idName
	 * @param memberId
	 * @return null 如果异常，否则返回实名认证结果map数据
	 */
	@SuppressWarnings("rawtypes")
	public static Map validateID(String idCard,String idName,String memberId){
		Map<String,String> dataMap  = new HashMap<String, String>();
		dataMap.put("inputCharset", inputCharset);
		dataMap.put("version", version);
		dataMap.put("idType", idType);
		dataMap.put("idCard", idCard);
		dataMap.put("idName", idName);
		dataMap.put("userId", memberId);
		///使用统一玖富api调用工具类进行调用
		return APIUtils.sendPost(API, dataMap);
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
