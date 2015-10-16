package com.temp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.frame.common.util.JsonUtil;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map map  =  new HashMap<String, Object>();
		map.put("data", new Date());
		System.out.println(JsonUtil.toJson(map, "yyyy-MM-dd"));
	}

}
