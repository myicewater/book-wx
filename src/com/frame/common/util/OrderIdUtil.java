package com.frame.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.frame.common.util.channels.APIUtils;

public class OrderIdUtil {

	public static String createNewOrdId(){
		Date date=new Date();
//		生成订单号
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=sdf.format(date);
		Random r = new Random();
		 String ordId="";
		 boolean b=true;
		 while(b){
			 int x = r.nextInt(999999); 
			 if(x > 100000) {
				 ordId=time+x;
				 b=false;
			  }
		 }
		 return APIUtils.utmSource+ordId;
	}
	
}
