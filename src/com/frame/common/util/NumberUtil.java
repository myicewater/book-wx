package com.frame.common.util;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberUtil {
	
	/**
	 * 获取0-9的数字随机码
	 * @param num 位数
	 * @return
	 */
	public static String randomNumCode(int num){
		String returnStr = "";
		
		for(int i = 0;i<num;i++){
			double random = Math.random();
			String str = Double.toString(random);
			
			returnStr += str.substring(2, 3);
			
		}
		
		return returnStr;
	}
	
	/**
	   * 过滤数值类型（double,float....）中的科学技术法 (注：以便以后可扩展及页面处理方便) 另注：float
	   * 类型的如果小数点保留六位，数值也会出错。 例如（7775.54f 转化后为：“7775.540039”）
	   * 
	   * @param num
	   * @return
	   */
	  public static String numberToString(Object obj) {
	    DecimalFormat df = null;
	    if (obj instanceof Double) {
	      df = new DecimalFormat("##.##");
	    }
	    if (obj instanceof Float) {
	      df = new DecimalFormat("##.##");
	    }
	    return df.format(obj);
	  }	
	  
	  
	  /**
	   * 获取double值
	 * @param bigDecimal
	 * @return
	 */
	public static double getDoubleVal(BigDecimal bigDecimal){
		  return bigDecimal == null ? 0.00D : bigDecimal.doubleValue();
	}
	/**
	 * 获取double值
	 * @param Object
	 * @return
	 */
	public static double getDouble(Object obj){
		if(obj == null){
			return 0.00D;
		}
		if(obj instanceof Double){
			return (Double)obj;
		}else if(obj instanceof BigDecimal){
			return ((BigDecimal)obj).doubleValue();
		}
		return 0.00D;
	  }
	
	/**
	 * 去除double的末几位0，或小数点及其后的0 
	 * <p>例如 1000.300 -> 1000.3 ， 1000.00 -> 1000
	 * 
	 * @param orgin 转为String类型的原始数据
	 * @return
	 */
	public static String doubleRidOfLastZero(String orgin) {
		if (orgin.indexOf(".") == -1) // 无小数位
			return orgin;
		
		char[] chars = orgin.toCharArray();
		int index = chars.length - 1;
		for (int i = chars.length - 1; i >= 0 ; i--) { 
			if (chars[i] == '0')  // 末位为0，截取index向前
				continue;
			else if (chars[i] == '.') { // 前进到小数点，退出循环。截掉小数点，index-1
				index = i - 1;
				break;
			} else {  // 前进到非0，退出循环
				index = i;
				break;
			}
		}
		return orgin.substring(0, index + 1);
	}
	
	public static void main(String[] args) {
		String s1="aa,bb,cc";
		String s2="failer,success,success";
		List<Map<String,String>>mapList=new ArrayList<Map<String,String>>();
		Map<String,String> map=null;
		String[]s11=s1.split(",");
		String[]s22=s2.split(",");
		for(int i=0;i<s11.length;i++){
			map=new HashMap<String,String>();
			map.put(i+"",s22[i]);
			mapList.add(map);
		}
		boolean flg=true;
		for(int i=0;i<mapList.size();i++){
			if("failer".equals(mapList.get(i).get(i+""))){
				flg=false;
			}
			//System.out.println(mapList.get(i).get(i+""))
		}
		System.out.println(flg+"");
	}
}
