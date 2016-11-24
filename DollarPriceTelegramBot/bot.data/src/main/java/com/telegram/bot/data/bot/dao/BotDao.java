package com.telegram.bot.data.bot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.telegram.bot.data.bot.model.Bot;
import com.telegram.bot.data.dao.impl.JPADao;

@Repository
public class BotDao extends JPADao<Bot>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4580641183813778337L;

	public List<Bot> getBotByUser(String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);

		return super.findByNamedQuery("Bot.findByUser", params);
	}
	
	public List<Bot> getBotByBotUserName(String botUserName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("botUserName", botUserName);

		return super.findByNamedQuery("Bot.findByBotUserName", params);
	}
	
}
