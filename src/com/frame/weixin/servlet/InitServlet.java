package com.frame.weixin.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.common.constant.ReturnCode;
import com.frame.weixin.thread.TokenThread;
import com.frame.weixin.util.WeixinUtil;

/**
 * 初始化servlet
 * 
 * @author liuyq
 * @date 2013-05-02
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	public void init() throws ServletException {
		// 启动定时获取access_token的线程
		new Thread(new TokenThread()).start();
		ReturnCode.init();
		
	}
}
