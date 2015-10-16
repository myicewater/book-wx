package com.frame.common.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class SpringBeanUtil extends SpringBeanAutowiringSupport {

	@Autowired
	private BeanFactory beanFactory;

	private SpringBeanUtil() {

	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	private static SpringBeanUtil instance;

	static {
		instance = new SpringBeanUtil();
	}

	public Object getBean(String beanId) {
		return getInstance().getBeanFactory().getBean(beanId);
	}

	public static SpringBeanUtil getInstance() {
		return instance;
	}
}
