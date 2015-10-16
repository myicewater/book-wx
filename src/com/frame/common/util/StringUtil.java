package com.frame.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil {
	private static Log log = LogFactory.getLog(StringUtil.class);
	private StringUtil(){
	}
	
	/**
	 * 判断字符串是否为空，如果为空返回true，否则返回false
	 * @param s
	 * @return
	 */
	public static final boolean isEmpty(final String str) {
		if (str == null || str.trim().length() < 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为空，如果不为空返回true，否则返回false
	 * @param s
	 * @return
	 */
	public static final boolean notEmpty(final String str) {
		if (str == null || str.trim().length() < 1) {
			return false;
		}
		return true;
	}

	/**
	 * 把null转换成""，如果不为null则转型
	 * @param o
	 * @return
	 */
	public static String nullToString(Object o) {
		String s = "";
		return o != null ? String.valueOf(o) : s;
	}
	
	/**
	 * TODO 转义没有实现，下版实现。
	 * 功能说明:去掉字符串2端空格或空白。如果参数字符串为null，那么返回结果为空白字符串，即"";
	 * @param s 需要过滤的字符串
	 * @param isTransferred 是否对html特殊字符转义 
	 * @return
	 */
	private static String trim(String s,boolean isTransferred){
		if(isTransferred){
			return s == null ? "" : s.trim();
		}else{
			return s == null ? "" : s.trim();
		}
	}
	/**
	 * 功能说明:去掉字符串2端空格或空白。如果参数字符串为null，那么返回结果为空白字符串，即"";
	 * @param s 需要过滤的字符串
	 * @return
	 */
	public static String trim(String s){
		return trim(s,false);
	}
	
	public static String encode(String arg){
		try {
			return URLEncoder.encode(arg,"utf-8");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return StringUtil.trim(arg,true);
		}
	}
	
	
	public static String decode(String arg){
		try {
			
			return URLDecoder.decode(arg,"utf-8");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return StringUtil.trim(arg,true);
		}
	}
	
	

    public static String escape(String str){
        if(str == null)
            return null;
        StringBuffer sb = new StringBuffer();
        sb.ensureCapacity(str.length() * 6);
        for(int i = 0; i < str.length(); i++)        {
            char ch = str.charAt(i);
            if(Character.isDigit(ch) || Character.isLowerCase(ch) || Character.isUpperCase(ch)){
                sb.append(ch);
            }else if(ch < '\u0100'){
                sb.append("%");
                if(ch < '\020'){
                    sb.append("0");
                    sb.append(Integer.toString(ch, 16));
                } else{
                    sb.append(Integer.toString(ch, 16));
                }
            } else {
                sb.append("%u");
                sb.append(Integer.toString(ch, 16));
            }
        }

        return sb.toString();
    }

    public static String unescape(String str){
        if(str == null){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.ensureCapacity(str.length());
        int pos = 0;
        for(int lastPos = 0; lastPos < str.length();){
            pos = str.indexOf("%", lastPos);
            if(pos == lastPos){
                if(str.charAt(pos + 1) == 'U' || str.charAt(pos + 1) == 'u'){
                    char ch = (char)Integer.parseInt(str.substring(pos + 2, pos + 6), 16);
                    sb.append(ch);
                    lastPos = pos + 6;
                } else if(str.charAt(pos + 1) == ' ' || str.charAt(pos + 1) == ';'){
                    sb.append(str.substring(pos, pos + 1));
                    lastPos = pos + 1;
                } else {
                    char ch = (char)Integer.parseInt(str.substring(pos + 1, pos + 3), 16);
                    sb.append(ch);
                    lastPos = pos + 3;
                }
            } else if(pos == -1){
                sb.append(str.substring(lastPos));
                lastPos = str.length();
            } else {
                sb.append(str.substring(lastPos, pos));
                lastPos = pos;
            }
        }
        return sb.toString();
    }
    
    
    /**
	 * Function: splitString<BR>
	 * Description: 此方法用于拼接sql    in <BR>
	 * @param str  需要传入的字符串  id,id,id,  或  id,id  
	 * @param type 需要截取的符号  比如：","
	 * @return 'id','id'    如果没截取出来就会返回：''
	 * 
	 */
	public static String splitString(String str,String type ){
		String sql = "";
		StringBuffer sbf = new StringBuffer();
		if (!StringUtil.isEmpty(str) && !StringUtil.isEmpty(type)) {
			String[] ids = str.split(type);
			for (int i = 0; i < ids.length; i++) {
				if (ids.length == (i + 1)) {
					sbf.append("'").append(ids[i]).append("'");
				} else {
					sbf.append("'").append(ids[i]).append("'").append(",");
				}
			}
			sbf.append("");
		}else {
			sbf.append("''");
		}
		return sbf.toString();
		
	}
	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toLowerCase(firstChar) + tail;
		return str;
	}
	
	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toUpperCase(firstChar) + tail;
		return str;
	}
}
