package com.frame.common.util;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/**
	 * 日期转为“yyyy年MM月dd日"格式
	 * @param date		需要转换的日期
	 * @return   		转换后的字符串
	 */
	public static String dateToStr(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		String st=formatter.format(date);
		return st;
	}
	/**
	 * 日期转为“yyyyMMdd"格式
	 * @param date		需要转换的日期
	 * @return   		转换后的字符串
	 */
	public static String dateToOrderDate(Date date){
//		生成订单日期
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String st=sdf.format(date);
		return st;
	}
	
	
	/**
	 * 计算天数
	 * @param beginTime	开始时间 
	 * @param endtime	结束时间
	 * @return	days	剩余天数
	 */
	public static long getDaysRemaining(Date beginTime,Date endtime){
		long beginMs=beginTime.getTime();
		long endMs=endtime.getTime();
		long days= (endMs-beginMs)/(1000*60*60*24);
		return days;
	}
	/**
	 * @param beginTime		
	 * @param endtime
	 * @return long  
	 * @exception   
	 * @since  1.0.0
	 */
	public static long getDate(Date beginTime,Date endtime){
		long beginMs=beginTime.getTime();
		long endMs=endtime.getTime();
		long l=endMs-beginMs;
		long timeTemp = l;
		  long time = timeTemp;
		  long mSec = time % 1000;
		  time /= 1000;
		  long year = time/(365*24*3600);
		  time = time%(365*24*3600);
		  long month = time/(30*24*3600);
		  time = time % (30*24*3600);
		  long day = time/(24*3600);
		  time = time % (24*3600);
		  long hour = time/3600;
		  time = time % 3600;
		  long min = time/60;
		  time = time % 60;
		  long sec = time;
		  System.out.println(timeTemp+"毫秒是："+year+"年"+month+"月"+day+"天"+hour+"小时"+min+"分钟"+sec+"秒零"+mSec+"毫秒");
		 return l ;
	}
	
	
	
	/**
	 * 计算日期
	 * @param d			日期
	 * @param day		天数
	 * @return			返回日期
	 */
	public static Date getAfterDay(Date d,int day){
		   Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
		   return now.getTime();
	}
	
	/**
	 * 计算日期
	 * @param d			日期
	 * @param day		天数
	 * @return			返回日期
	 */
	public static Date getAfterMinute(Date d,int min){
		   Calendar now =Calendar.getInstance();
		   now.setTime(d);
		   now.set(Calendar.MINUTE,now.get(Calendar.MINUTE)+min);
		   return now.getTime();
	}
	/**
	 * 每天的收益
	 * @param amount	投资金额
	 * @param profit	利率
	 * @return			收益
	 */
	public static double getTake(double amount,double profit){
		double take=amount*profit/365/100;
		return take;
	}
	
	/**
	 * 按月计算时间
	 * @param date		需要计算时间
	 * @param month		月数
	 * @return
	 */
	public static Date getAfterMonth(Date date,int month) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例   
        c.setTime(date);//设置日历时间   
        c.add(Calendar.MONTH,month);//在日历的月份上增加6个月   
        date=c.getTime();//的到你想要得6个月后的日期   
        return date;
	}
	/**
	 * 按年计算时间
	 * @param date		需要计算时间
	 * @param year		年数
	 * @return
	 */
	public static Date getAfterYear(Date date,int year) {
		Calendar c = Calendar.getInstance();//获得一个日历的实例   
		c.setTime(date);//设置日历时间   
		c.add(Calendar.YEAR,year);//在日历的月份上增加6个月   
		date=c.getTime();//的到你想要得6个月后的日期   
		return date;
	}
	//保留后两位
	public static String numberFormat(Double d){
		 DecimalFormat f=new DecimalFormat(",##0.00");
		 f.setGroupingUsed(false);
			String str=f.format(d);
//			System.out.println(str);
		return str;
	}
	//不保留后两位
	public static String numberFormatNoTwo(Double d){
		 DecimalFormat f=new DecimalFormat(",##0");
		 f.setGroupingUsed(false);
			String str=f.format(d);
//			System.out.println(str);
		return str;
	}
	public static Date parse(String date , String formater){
		SimpleDateFormat sdf = new SimpleDateFormat( formater );
		Date result = null;
		try
		{
			 result = sdf.parse( date );
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
		return result;
	}
	//计算两天相差天数
	public static int getDifferDay(String date1, String date2) {
		Date dateTime1_tmp = parse(date1, "yyyy-MM-dd");
		Date dateTime2_tmp = parse(date2, "yyyy-MM-dd");		
		Long d1 = dateTime1_tmp.getTime();
		Long d2 = dateTime2_tmp.getTime();
		return (int) ((d2 - d1) / 86400000);
	}
	//计算两天相差天数
		public static int getDifferDay(Date date1, Date date2) {
				
			Long d1 = date1.getTime();
			Long d2 = date2.getTime();
			return (int) ((d2 - d1) / 86400000);
		}
		//计算两天相差nian数
				public static int getDifferYears(Date date1, Date date2) {
						
					Long d1 = date1.getTime();
					Long d2 = date2.getTime();
					return (int) ((d2 - d1) / 86400000/365);
				}
	public static long getDifferSecond(Date startTime, Date endTime) {
		Date a,b;
		a = startTime;
		b = endTime;
		long c =b.getTime() - a.getTime();
		return c/1000;
	}
	
	public static String getCurrentTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}
