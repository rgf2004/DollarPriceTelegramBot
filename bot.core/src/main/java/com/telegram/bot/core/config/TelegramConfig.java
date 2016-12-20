package com.telegram.bot.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Configuration
@PropertySources({@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true),
	   @PropertySource(value = "file:${conf.file}", ignoreResourceNotFound = true)})
@Service	
public class TelegramConfig {

	@Autowired
	private Environment env;
	
	public String getProperty(String key)
	{
		return env.getProperty(key);
	}
}