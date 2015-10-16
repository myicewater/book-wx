package com.frame.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.TypeReference;
import com.frame.common.util.JsonUtil;
import com.frame.common.util.PropUtil;
import com.frame.weixin.pojo.AccessToken;
import com.frame.weixin.pojo.Fans;
import com.frame.weixin.pojo.Menu;
import com.frame.weixin.pojo.WeiXinTokenCode;

/**
 * 微信工具类
 * @author jianyu
 *
 */
public class WeixinUtil {
	private static final Logger log = Logger.getLogger(WeixinUtil.class); 
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
	// 菜单创建（POST） 限100（次/天）  
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
	private static String APPID = PropUtil.getValue("weixinAppid");
	private static String SECRET = PropUtil.getValue("weixinSecret");
	/** 
	 * 创建菜单 
	 *  
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int createMenu(Menu menu, String accessToken) {  
	    int result = 0;  
	    // 拼装创建菜单的url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    // 调用接口创建菜单  
	    JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);  
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode");  
	            log.error("创建菜单失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");  
	        }  
	    }  
	    System.out.println(jsonMenu);
	    return result;  
	} 
	/** 
	 * 获取access_token 
	 *  
	 * @param appid 凭证 
	 * @param appsecret 密钥 
	 * @return 
	 */  
	public static AccessToken getAccessToken(String appid, String appsecret) {  
	    AccessToken accessToken = null;  
	  
	    String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);  
	    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
	    log.info("===========获取accessToken返回值================："+jsonObject);
	    // 如果请求成功  
	    if (null != jsonObject) {  
	        try {  
	            accessToken = new AccessToken();  
	            accessToken.setToken(jsonObject.getString("access_token"));  
	            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
	        } catch (Exception e) {  
	            accessToken = null;  
	            // 获取token失败  
	            log.error("获取token失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");  
	        }  
	    }  
	    return accessToken;
	}  
	/**
	 * 开发者获取使用凭证，可以使用该凭证对公众账号的自定义菜单进行创建、查询和删除等操作
	 */
	public static WeiXinTokenCode getAccessToken(String code)
			throws IOException {
		System.out.println("++++getAccessToken++++");
		StringBuffer sb = new StringBuffer();
		sb.append("https://api.weixin.qq.com/sns/oauth2/access_token?&appid="
				+ APPID + "&secret=" + SECRET + "&code=" + code
				+ "&grant_type=authorization_code");
		Map<String, String> map = new HashMap<String, String>();
		map.put("f", "json");
		Response response = Jsoup.connect(sb.toString())
				.ignoreContentType(true).method(Method.GET).data(map).execute();
		// 新添加代码
		String json = response.body();
		System.out.println(json);
		WeiXinTokenCode weiXinTokenCode = JsonUtil.toObject(json, new TypeReference<WeiXinTokenCode>() {});
		return weiXinTokenCode;
	}

    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    
    public static Fans getFansInfo(String token, String openId)throws Exception {
		// API_FANSINFO = API_FANSINFO.replace("{{accessToken}}",
		// token).replace("{{openId}}", openId);
		String api_fansinfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				+ token + "&openid=" + openId;
		 log.info(" --------------->getfansInfo:"+api_fansinfo);  
		Document document = Jsoup.connect(api_fansinfo).ignoreContentType(true)
				.get();
		// System.out.println("body.data: " + document.body().outerHtml());
		Elements eles = document.select("body");
		Element element = eles.first();
		String json = element.text().trim().replace(" ", "");
		log.info("json===" + json); 
		//System.out.println("json===" + json);
		Map<Object, Object> map2 = JsonUtil.toObject(json, new TypeReference<HashMap>() {});
		Fans fans = new Fans();
		fans.setSubscribe((Integer) map2.get("subscribe"));
		fans.setOpenId((String) map2.get("openid"));
		String nickname = (String) map2.get("nickname");
//		fans.setNickName(new String(nickname.getBytes(), "UTF-8"));
//		nickname = TranCharset.XmlFormalize(nickname);
		//nickname = BASE64.encryptBASE64(nickname.getBytes());
		fans.setNickName(nickname);
		fans.setSex((Integer) map2.get("sex"));
		fans.setLanguage((String) map2.get("language"));
		fans.setCity((String) map2.get("city"));
		fans.setProvince((String) map2.get("province"));
		fans.setCountry((String) map2.get("country"));
		String headimgurl = (String) map2.get("headimgurl");
		fans.setHeadimgurl(headimgurl);
		//fans.setSubscribe_time((Integer) map2.get("subscribe_time"));
		/*if (!headimgurl.equals("")) {
			downloadImg1((String) map2.get("headimgurl"), openId,request);
		}else{
			copyImg1(openId, request);
		}*/
		return fans;
		}
    
    
   
    /*public static List<String> getFansOpenIds(String token)
			throws IOException {
		// 星标组groupid = 2 ； 黑名单groupid=1
		// https://mp.weixin.qq.com/cgi-bin/contactmanagepage?t=wxm-friend&token=1899231582&lang=zh_CN&pagesize=10&pageidx=0&type=0&groupid=0
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+token;
		Document document = Jsoup.connect(url).ignoreContentType(true)
				.get();
		Elements eles = document.select("body");
		Element element = eles.first();
		String json = element.text().trim().replace(" ", "");
		System.out.println("json===" + json);
		Map jMap = GsonUtil.fromJson(json, Map.class);
		double total = (Double)jMap.get("total");
		long total_long = (long)total;
		if(total_long > 0){
			Map dataMap = (Map) jMap.get("data");
			List openidList = (List) dataMap.get("openid");
			log.info("共有" + total_long + "个粉丝");
			log.info(openidList.toString());
			return openidList;
		}
		log.info("没有粉丝！");
		return null;

	}*/
    /**
	 * 发送文本信息 24小时内互动过的
	 * 
	 * @param token
	 * @param openid
	 * @param groupid
	 * @throws IOException
	 */
	public static boolean sendText(String token, String openid, String text)
			throws IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ token;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter("http.protocol.content-charset",
				"UTF-8");
		HttpPost httppost = new HttpPost(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String canshu = "{\"touser\":\"" + openid
				+ "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + text
				+ "\"}}";
		StringEntity reqEntity = new StringEntity(canshu, "utf-8");
		// 设置请求的数据
		httppost.setEntity(reqEntity);
		String responseBody = httpclient.execute(httppost, responseHandler);
		System.out.println(responseBody);
		Map m = JsonUtil.toObject(responseBody, new TypeReference<HashMap>() {});
		String errCode  = (String) m.get("errmsg");
		if(errCode.equals("ok")){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 发送图文
	 * 
	 * @param token
	 * @param openid
	 * @param groupid
	 * @throws IOException
	 */
	public static boolean sendTextImg(String token, String openid, String json)
			throws IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ token;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter("http.protocol.content-charset",
				"UTF-8");
		HttpPost httppost = new HttpPost(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		/*String canshu = "{\"touser\":\"" + openid
				+ "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + text
				+ "\"}}";*/
		StringEntity reqEntity = new StringEntity(json, "utf-8");
		// 设置请求的数据
		httppost.setEntity(reqEntity);
		String responseBody = httpclient.execute(httppost, responseHandler);
		System.out.println(responseBody);
		Map m = JsonUtil.toObject(responseBody, new TypeReference<HashMap>() {});
		String errCode  = (String) m.get("errmsg");
		if(errCode.equals("ok")){
			return true;
		}else{
			return false;
		}
	}
}
