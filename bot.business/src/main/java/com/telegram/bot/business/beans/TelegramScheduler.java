package com.telegram.bot.business.beans;

import org.springframework.context.ApplicationContext;

import com.telegram.bot.data.bot.model.NotificationScheduler;

public interface TelegramScheduler {

	public void inti(ApplicationContext ctxt, NotificationScheduler notificationScheduler);

	public void execute();

}
