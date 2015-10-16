package com.frame.common.util;

import java.io.InputStream;
import java.util.Properties;

public class PropUtil
{

	private static PropUtil instance = null;
	private Properties props = null ;
	private static String FILEPATH = "/config.properties";
	
	private static synchronized PropUtil getInstatance(){
		if(instance == null){
			instance = new PropUtil();
		}
		return instance;
	}
	
	private PropUtil(){
		loadProps();
	}

	private void loadProps() {
		props = new Properties();
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream(FILEPATH);
			props.load(in);
		}
		catch (Exception e) {
			//此处可根据你的日志框架进行记录
			System.err.println("Error reading conf properties in PropertyManager.loadProps() " + e);
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				//此处可根据你的日志框架进行记录
			}
		}
	}
	
	private String getProp(String key) {
		String value = props.getProperty(key);
		return value == null ? "" : value.trim();
	}
	
	/**
	 * 根据key获取对应value
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		return getInstatance().getProp(key);
	}
	
	/**
	 * 根据key获取对应value，如果为空则返回默认的value
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String key,String defaultValue){
		String value = getInstatance().getProp(key);
		return "".equals(value) ? defaultValue : value;
	}

}
