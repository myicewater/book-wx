package com.frame.common.constant;

import java.util.HashMap;
import java.util.Map;

import jodd.util.StringUtil;
/**
 * 返回对象， 格式：
 * <pre>code:xxxxxx<br>
 * message:xxxx<br>
 * data:HashMap<String, Object>()
 * </pre>
 * @author zhangshaoliang
 * 2015-5-29下午3:04:45
 */
public class ResultObject {
	private String code;
	private String message;
	private Map<String, Object> data;
	
	
	/**
	 * 为返回结果data中添加一行redirect_url记录，用于定位跳转页面
	 * @param url
	 * @return
	 */
	public ResultObject returnWithRedirectUrl(String url){
		///如果跳转url不为空，则记录需要跳转的url
		if(this.data==null){
			this.data = new HashMap<String, Object>();
		}
		if(!StringUtil.isBlank(url)){
			this.data.put("redirect_url", url);
		}
		return this;
	}
	
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	
}
