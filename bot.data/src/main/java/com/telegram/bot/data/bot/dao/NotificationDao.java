package com.telegram.bot.data.bot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.telegram.bot.data.bot.model.Notification;
import com.telegram.bot.data.dao.impl.JPADao;

@Repository
public class NotificationDao extends JPADao<Notification>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7498275873820083520L;

	public List<Notification> getActiveNotificationsByBotId(long botId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("botId", botId);

		return super.findByNamedQuery("Notification.findActiveNotificationsByBotId", params);
	}
	
	public List<Notification> getNotificationsByBotUserName(String botUserName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("botUserName", botUserName);

		return super.findByNamedQuery("Notification.findNotificationsByBotUserName", params);
	}
	
}
