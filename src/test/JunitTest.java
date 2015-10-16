package test;

import java.util.HashMap;
import java.util.Map;

//import org.junit.Test;

import com.frame.common.util.HttpClientUtils;

/**
 * 测试工具类
 * @author zhangshaoliang
 * 2015-6-17下午3:04:50
 */
public class JunitTest {
	
	private String URL_Channel = "http://localhost:80/lmlc-wx/channel/";
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
//	@Test
    public void testGetAcccountById() { 
    
    	Map map = new HashMap<String, String>();
    	map.put("data", "123");
    	String rStr = HttpClientUtils.sendPostRequest(URL_Channel+"asyncCall.htm", map);
    	System.out.println("返回数据为：\n"+rStr);
    } 
}
