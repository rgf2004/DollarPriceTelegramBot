package com.telegram.bot.data.bot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.telegram.bot.data.bot.model.Chat;
import com.telegram.bot.data.dao.impl.JPADao;

@Repository
public class ChatDao extends JPADao<Chat> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3175300374017476332L;

	public List<Chat> getChatByBotId(long botId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("botId", botId);

		return super.findByNamedQuery("Chat.findByBotID", params);
	}
	
	public List<Chat> getChatByBotIdAndChatId(long botId, long chatId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("botId", botId);
		params.put("telegramChatId", chatId);

		return super.findByNamedQuery("Chat.findByBotIDAndChatID", params);
	}

}
