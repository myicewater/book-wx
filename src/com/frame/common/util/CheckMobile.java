package com.frame.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckMobile {
	public static boolean isMobile(String str){
		Pattern p=null;
		Matcher m=null;
		boolean b=false;
		p=Pattern.compile("^[1][3,4,7,5,8][0-9]{9}$");
		m=p.matcher(str);
		b=m.matches();
		return b;
	}
}
