package com.telegram.bot.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(
   {"classpath:db_config.xml"})
public class SpringModuleConfig {

}
