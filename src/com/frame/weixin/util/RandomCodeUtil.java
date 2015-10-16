package com.frame.weixin.util;

/**
 * 获取随机码
 * @author Administrator
 *
 */
public class RandomCodeUtil {

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
	 * 获取从1到num的随机数
	 * @param num
	 * @return
	 */
	public static String randomNumCodeForNum(Integer num){
		
		String temp = randomNumCode(2);
		
		if(Integer.parseInt(temp) <= num && Integer.parseInt(temp) > 0){
			Integer it = Integer.parseInt(temp);
			return it.toString();
		}else{
			return randomNumCodeForNum(num);
		}
		
	}
	
	/**
	 * 获取两个人之间的随机数
	 * @param fromNum
	 * @param toNum
	 * @return
	 */
	public static int randomNumForBetween(int fromNum, int toNum){
		int random = (int) (Math.random()*(fromNum+1-toNum)+toNum);
		return random;
	}
	
	public static void main(String[] args) {
//		double random = Math.random();
//		String str = Double.toString(random);
//		
//		System.out.println(str);
//		System.out.println(str.substring(2, 3));
		
		String str = randomNumCode(6);
		
		System.out.println(str);
	}
}
