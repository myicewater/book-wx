package com.frame.common.util;


import java.util.List;

public class ListUtil {
	private ListUtil(){
	}
	
	public static boolean isHaveNone(List list){
		if(list == null || list.size() == 0){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isHaveMoreThanOne(List list){
		if(list != null && list.size() > 1){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isHave(List list){
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isHaveOne(List list){
		if(list != null && list.size() == 1){
			return true;
		}else{
			return false;
		}
	}
}
