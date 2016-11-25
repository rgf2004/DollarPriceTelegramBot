package com.telegram.bot.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TelegramConfig {

	private static final String propertiesFileName = "application.properties";
	
	private Properties config = new Properties();

	private InputStream input = null;
	
	private static TelegramConfig singelton = new TelegramConfig();	
	
	private TelegramConfig()
	{
		input = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
		try {
			config.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key)
	{
		return singelton.getConfig().getProperty(key);
	}
	
	private Properties getConfig()
	{
		return config;
	}
}