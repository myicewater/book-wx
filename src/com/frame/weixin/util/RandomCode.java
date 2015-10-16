package com.frame.weixin.util;

import java.util.Random;

import org.apache.log4j.Logger;

public class RandomCode {
	private static final Logger logger = Logger.getLogger(SmsSendUtil.class);
	    public static String getRandomString()  
	    {  
	        String str = "0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,";  
	        String str2[] = str.split(",");//将字符串以,分割  
	        int sum = 0;//计数器  
	        for (int i=0; i<str2.length; ++i)  
	        {  
	            ++sum;  
	            if (0 == sum % 10)  
	            {  
	            }  
	        }  
	        Random rand = new Random();//创建Random类的对象rand  
	        int index = 0;  
	        String randStr = "";//创建内容为空字符串对象randStr  
	            randStr = "";//清空字符串对象randStr中的值  
	            for (int i=0; i<6; ++i)  
	            {  
	                index = rand.nextInt(str2.length-1);//在0到str2.length-1生成一个伪随机数赋值给index  
	                randStr += str2[index];//将对应索引的数组与randStr的变量值相连接  
	            } 
	            logger.info("验证码：" + randStr);//输出所求的验证码的值  
				return randStr;
}
	    }