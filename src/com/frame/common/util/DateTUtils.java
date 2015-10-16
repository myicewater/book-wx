package com.frame.common.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateTUtils {
	//计算开始计算收益时间
	public static final String T_ADD = "http://dayapi.welicai.com/date";
//	public static final String T_ADD = "http://192.168.50.7:8090/date";
	
	public static Date getDate(Date da,String day){
		Date date= new Date();
		Map<String,String> map =new HashMap<String,String>();
		String beginDate=DateUtils.dateToOrderDate(date);
		map.put("currdate", beginDate);
		map.put("T", day);
		String str=HttpClientUtils.sendPostRequest(T_ADD, map);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 根据指定日期格式格式化日期为String型
	 * 
	 * @param date
	 * @param formater
	 * @return
	 */
	public static String format(Date date, String formater){
		SimpleDateFormat sdf = new SimpleDateFormat( formater );
		return sdf.format(date);
	}
	public static void main(String[] args) {
		Date date=getDate(new Date(),2+"");
		System.out.println(date);
	}

}
