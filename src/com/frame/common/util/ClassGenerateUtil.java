package com.frame.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.frame.model.bauthor.BAuthor;
import com.frame.model.bbook.BBook;

public class ClassGenerateUtil {

	private static final Logger logger = Logger.getLogger(ClassGenerateUtil.class);
	public static BBook generateBBook(String sr){
		JSONObject jb = JSONObject.fromObject(sr);
	     String subtitle = jb.getString("subtitle");
	     
	     String originTitle = jb.getString("origin_title");
	     String binding = jb.getString("binding");
	     String page = jb.getString("pages");
	     Integer pages = 0;
	     if(page != null && !"".equals(page) ){
	    	  pages = Integer.valueOf(page);
	     }
	    	 
	     
	     JSONObject images = jb.getJSONObject("images");
	     String imagesSmall = images.getString("small");
	     String imagesLarge = images.getString("large");
	     String imagesMedium = images.getString("medium");
	     String publisher = jb.getString("publisher");
	     String title = jb.getString("title");
	     
	     String summary = jb.getString("summary");
	     String price = jb.getString("price");
	     String isbn = jb.getString("isbn13");
	        
		 BBook book = new BBook();
		 book.setSubtitle(subtitle);
		 book.setOriginTitle(originTitle);
		 book.setBinding(binding);
		 book.setPages(pages);
		 book.setImagesSmall(imagesSmall);
		 book.setImagesLarge(imagesLarge);
		 book.setImagesMedium(imagesMedium);
		 book.setPublisher(publisher);
		 book.setTitle(title);
		 book.setSummary(summary);
		 book.setPrice(getBookPrice(price));
		 book.setIsbn(isbn);
		 book.setIsPass("F");
		 book.setCreateTime(new Date());
		 return book;
	}
	
	public static List<BAuthor> generateAuthor(String sr,int bookId){
		JSONObject jb = JSONObject.fromObject(sr);
		JSONArray authors = jb.getJSONArray("author");
		logger.info("authors:"+authors.toString());
		String authorIntro = jb.getString("author_intro");
		
		List<BAuthor> bauthors = new ArrayList<BAuthor>();
		for(int i=0;i<authors.size();i++){//把jsonArray 转换成List
			//JSONObject object = (JSONObject)authors.get(i); 
			String authorName = authors.getString(i);
			BAuthor author = new BAuthor();
			author.setAuthorName(authorName);
			author.setAuthorProfile(authorIntro);
			author.setBookId(bookId);
			author.setCreateTime(new Date());
			bauthors.add(author);
		}
		return bauthors;
	}
	
	public static List<BAuthor> generateAuthor(String sr){
		JSONObject jb = JSONObject.fromObject(sr);
		JSONArray authors = jb.getJSONArray("author");
		logger.info("authors:"+authors.toString());
		String authorIntro = jb.getString("author_intro");
		
		List<BAuthor> bauthors = new ArrayList<BAuthor>();
		for(int i=0;i<authors.size();i++){//把jsonArray 转换成List
			//JSONObject object = (JSONObject)authors.get(i); 
			String authorName = authors.getString(i);
			BAuthor author = new BAuthor();
			author.setAuthorName(authorName);
			author.setAuthorProfile(authorIntro);
			author.setCreateTime(new Date());
			bauthors.add(author);
		}
		return bauthors;
	}
	
	public static Double getBookPrice(String str){
		String pp = str;
		if(pp == null || pp.length() == 0){
			return 0.0;
		}
		int start = 0;
		int end =0;
		char c;
		for(int i= 0;i<pp.length();i++){
			c=pp.charAt(i);
			if(Character.isDigit(c)){
				start = i;
				break;
			}
		}
		//System.out.println("starter:"+start);
		
		for(int i=pp.length()-1;i>-1;i--){
			c=pp.charAt(i);
			if(Character.isDigit(c)){
				end = i+1;
				break;
			}
		}
		//System.out.println("end:"+end);
		
		return Double.valueOf(pp.substring(start, end));
		
	}
	
	public static void main(String[] args) {
		System.out.println(getBookPrice("cytdf77.00fjeifj"));
	}
	
	
}
