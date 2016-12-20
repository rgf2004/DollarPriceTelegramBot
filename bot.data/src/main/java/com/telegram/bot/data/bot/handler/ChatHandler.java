package com.telegram.bot.data.bot.handler;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.bot.data.bot.dao.ChatDao;
import com.telegram.bot.data.bot.model.Chat;

@Service
public class ChatHandler {

	@Autowired
	ChatDao chatDao;
	
	@Transactional
	public boolean saveChat(Chat chat) throws Exception 
	{
		try
		{
			chatDao.createEntity(chat);
			return true;
		}
		catch (Exception ex)
		{
			throw ex;
		}		
	}	
	
	public List<Chat> getChatsByBotId(long botId)
	{
		return chatDao.getChatByBotId(botId);
	}
	
	public List<Chat> getChatByBotIdAndChatId(long botId, long chatId)
	{
		return chatDao.getChatByBotIdAndChatId(botId, chatId);
	}
	
	
}
