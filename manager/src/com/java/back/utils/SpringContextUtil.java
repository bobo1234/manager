package com.java.back.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"spring.xml");// Spring应用上下文环境

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}
	public static <T> T getBean(Class T) throws BeansException {
		return (T)applicationContext.getBean(T);
	}

}