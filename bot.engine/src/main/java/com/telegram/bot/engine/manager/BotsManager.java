package com.telegram.bot.engine.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import com.telegram.bot.business.beans.TelegramBot;
import com.telegram.bot.core.constants.TelegramConstants;
import com.telegram.bot.data.bot.handler.BotHandler;
import com.telegram.bot.data.bot.model.Bot;
import com.telegram.bot.dollarprice.impl.DollarPriceTelegramBot;

@Service
public class BotsManager implements Runnable {

	final static Logger logger = LoggerFactory.getLogger(BotsManager.class);

	@Autowired
	BotHandler botHandler;

	@Autowired
	private ApplicationContext appContext;

	TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

	static Map<String, Bot> botsCache = new HashMap<>();

	@PostConstruct
	public void initializeAllBots() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		
//		try {
//			Thread.sleep(60000);
//		} catch (InterruptedException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		
		while (true) {
			
			
			logger.debug("Start Checking For New Bots ... ");

			long botsCount = botsCache.size();

			List<Bot> bots = null;
			try {
				bots = botHandler.getAllDbBots();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (Bot bot : bots) {
				if (!botsCache.containsKey(bot.getBotToken())) {
					
					botsCache.put(bot.getBotToken(), bot);

					try {
						//telegramBotsApi.registerBot(new TelegramBot(appContext, bot));
						telegramBotsApi.registerBot(new DollarPriceTelegramBot(appContext, bot));
						logger.info("Register New BOT [{}]", bot.getBotName());
					} catch (TelegramApiException e) {
						logger.error("Error While initialized Bot " + bot.getBotUserName(), e);
					}
				}
			}

			if (botsCount != botsCache.size())
				logger.info("Bots Cachee changed, current bots Cache size [{}]", botsCache.size());

			logger.debug("End Checking For New Bots - total No. Of bots [{}]", botsCache.size());

			try {
				Thread.sleep(TelegramConstants.BOTS_CHECK_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
