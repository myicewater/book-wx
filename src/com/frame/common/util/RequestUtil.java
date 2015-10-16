package com.frame.common.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;



public class RequestUtil {
	
	public static String getAllParam(HttpServletRequest request) {
		Map paraMap = request.getParameterMap();
		Iterator iter = paraMap.entrySet().iterator();
		String param = "";
		int index = 0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			Object obj = entry.getValue();
			String val = "";
			if (obj instanceof String[]) {
				String[] strs = (String[]) obj;
				val = strs[0];
			} else {
				val = obj.toString();
			}
			param += key + "="+ val + "&";
		}
		if(param != null && param.length()>0){
			param = param.substring(0, param.length()-1);
		}
			
		return param;
	}
	
}