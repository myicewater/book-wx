package com.frame.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.frame.common.util.FileUtil;

public class SysInitListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent event) {
		FileUtil.setWebRootPath(event.getServletContext().getRealPath(""));
		  
	}

}
