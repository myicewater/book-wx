package com.frame.common.util;

import java.util.HashMap;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

@SuppressWarnings({ "rawtypes" })
public class JsonUtil {

    /**
     * 将java类型的对象转换为JSON格式的字符串
     * @param object java类型的对象
     * @return JSON格式的字符串
     */
    public static <T> String toJson(T object) {
        return JSON.toJSONString(object);
    }

    /**
     * 处理带有日期格式的json数据
     * @param object
     * @param dataFormat
     * @return
     */
    public static<T> String toJson(T object,String dataFormat){
    	return JSON.toJSONStringWithDateFormat(object, dataFormat);
    }
    /**
     * 将JSON格式的字符串转换为java类型的对象或者java数组类型的对象，不包括java集合类型
     * @param json JSON格式的字符串
     * @param clz java类型或者java数组类型，不包括java集合类型
     * @return java类型的对象或者java数组类型的对象，不包括java集合类型的对象
     */
    /*public static <T> T toObject(String json, Class<T> clz) {
        return JSON.parseObject(json, clz);
    }*/

    /**
     * 将JSON格式的字符串转换为List<T>类型的对象
     * @param json JSON格式的字符串
     * @param clz 指定泛型集合里面的T类型
     * @return List<T>类型的对象
     */
   /* public static <T> List<T> toList(String json, Class<T> clz) {
        return JSON.parseArray(json, clz);
    }*/

    /**
     * 将JSON格式的字符串转换成任意Java类型的对象
     * @param json JSON格式的字符串
     * @param type 任意Java类型
     * @return 任意Java类型的对象
     */
    public static <T> T toObject(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }

	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("key1", 1);
		map.put("key2", "value2");
		System.out.println(JsonUtil.toJson(map));
		
		String s = "{\"key1\":1,\"key2\":\"value2\"}";
		System.out.println(JsonUtil.toObject(s,new TypeReference<HashMap>() {}));

	}
}
