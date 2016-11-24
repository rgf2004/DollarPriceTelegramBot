package com.telegram.bot.engine.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:**/applicationContext*.xml");
		/*
		 * the main entry point is BotsManager.initializeAllBots() which is PostConstruct 
		 * and will be loaded automatically during startup
		*/
	}

}
