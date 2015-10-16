package com.frame.weixin.util;

import org.apache.log4j.Logger;

public class LogUtil {
	/**
	 * 日志打印
	 */
	private static final Logger logger = Logger.getLogger(LogUtil.class);
	
	public static void printLog(String msg){
		logger.error("--------------------------Exception Start---------------------------------------");
		logger.error(msg);
		logger.error("--------------------------Exception End---------------------------------------");
	}
	public static void printLog(String msg,Exception e){
		logger.error("--------------------------Exception Start---------------------------------------");
		logger.error(msg, e);
		logger.error("--------------------------Exception End---------------------------------------");
	}
	public static void printInfoLog(String msg){
		logger.info("--------------------------info Start---------------------------------------");
		logger.info(msg);
		logger.info("--------------------------info End---------------------------------------");
	}
}
