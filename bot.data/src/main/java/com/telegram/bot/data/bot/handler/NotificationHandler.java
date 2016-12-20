package com.telegram.bot.data.bot.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.bot.data.bot.beans.NotificationBean;
import com.telegram.bot.data.bot.dao.BotDao;
import com.telegram.bot.data.bot.dao.NotificationDao;
import com.telegram.bot.data.bot.model.Bot;
import com.telegram.bot.data.bot.model.Notification;

@Service
public class NotificationHandler {

	@Autowired
	NotificationDao notificationDao;

	@Autowired
	BotDao botDao;

	@Transactional
	public boolean saveNotification(String botUserName, Notification notification) throws Exception {
		try {
			List<Bot> bots = botDao.getBotByBotUserName(botUserName);
			if (bots.size() != 1) {
				throw new Exception("Invalid Bot");
			}

			notification.setBot(bots.get(0));

			notificationDao.createEntity(notification);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<NotificationBean> getActiveNotificationsByBotId(long botId) {
		List<Notification> dbNotifications = notificationDao.getActiveNotificationsByBotId(botId);

		List<NotificationBean> notifications = new ArrayList<>();

		for (Notification notification : dbNotifications)
			notifications.add(new NotificationBean(notification));

		return notifications;
	}

	public List<NotificationBean> getNotificationsByBotUserName(String botUserName) {
		List<Notification> dbNotifications = notificationDao.getNotificationsByBotUserName(botUserName);

		List<NotificationBean> notifications = new ArrayList<>();

		for (Notification notification : dbNotifications)
			notifications.add(new NotificationBean(notification));

		return notifications;
	}

	public List<Notification> getActiveNotificationsDbByBotId(long botId) {
		return notificationDao.getActiveNotificationsByBotId(botId);

	}

	@Transactional
	public void setExecutedNotification(Notification notification) {
		notification.setExecuted(true);
		notification.setActualRunDate(new Date());

		notificationDao.updateEntity(notification);
	}

}
