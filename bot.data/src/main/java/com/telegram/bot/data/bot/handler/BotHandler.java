package com.telegram.bot.data.bot.handler;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.bot.data.bot.beans.BotBean;
import com.telegram.bot.data.bot.dao.BotDao;
import com.telegram.bot.data.bot.dao.UserDao;
import com.telegram.bot.data.bot.model.Bot;
import com.telegram.bot.data.bot.model.User;

@Service
public class BotHandler {

	@Autowired
	BotDao botDao;

	@Autowired
	UserDao userDao;

	@Transactional
	public boolean saveBot(String userName, Bot bot) throws Exception {

		try {

			List<User> users = userDao.getUserByUserName(userName);
			if (users.size() != 1) {
				throw new Exception("Invalid User");
			}
			bot.setBotUser(users.get(0));
			botDao.createEntity(bot);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<BotBean> getAllBots() throws Exception {

		List<Bot> dbBots = botDao.findAll();
		List<BotBean> bots = new ArrayList<>();

		for (Bot bot : dbBots)
			bots.add(new BotBean(bot));

		return bots;
	}

	public List<Bot> getAllDbBots() throws Exception {

		return botDao.findAll();
	}

	public List<BotBean> getBotByUser(String userName) throws Exception {

		List<Bot> dbBots = botDao.getBotByUser(userName);

		List<BotBean> bots = new ArrayList<>();

		for (Bot bot : dbBots)
			bots.add(new BotBean(bot));

		return bots;
	}

	public List<BotBean> getBotByBotUserName(String botUserName) throws Exception {

		List<Bot> dbBots = botDao.getBotByBotUserName(botUserName);

		List<BotBean> bots = new ArrayList<>();

		for (Bot bot : dbBots)
			bots.add(new BotBean(bot));

		return bots;
	}
}
