package com.telegram.bot.business.beans;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.telegram.bot.core.constants.TelegramConstants;
import com.telegram.bot.data.bot.handler.ChatHandler;
import com.telegram.bot.data.bot.handler.NotificationHandler;
import com.telegram.bot.data.bot.model.Bot;
import com.telegram.bot.data.bot.model.Chat;
import com.telegram.bot.data.bot.model.Notification;

public abstract class TelegramBot extends TelegramLongPollingBot implements Runnable {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected ApplicationContext context;

	protected Bot databaseBot;

	protected ChatHandler chatHandler;

	NotificationHandler notificationHandler;

	public TelegramBot(ApplicationContext ctxt, Bot bot) {

		super();

		context = ctxt;
		databaseBot = bot;

		chatHandler = context.getBean(ChatHandler.class);

		notificationHandler = context.getBean(NotificationHandler.class);

		Thread thread = new Thread(this);
		thread.start();
	}

	public void onUpdateReceived(Update update) {

		logger.info("Update Recieved {}", update.toString());

		handleIncomingMessage(update);

	}

	public abstract void handleIncomingMessage(Update update);

	public String getBotUsername() {
		return databaseBot.getBotUserName();
	}

	@Override
	public String getBotToken() {
		return databaseBot.getBotToken();
	}

	protected void handleFirstTime(Message message) {

		long chatID = message.getChatId();
		List<Chat> chats = chatHandler.getChatByBotIdAndChatId(databaseBot.getBotId(), chatID);
		if (chats.isEmpty()) {
			Chat chat = new Chat();
			chat.setBot(databaseBot);
			chat.setTelegramChatId(chatID);
			chat.setCustomerUserName(message.getChat().getUserName());
			chat.setCustomerFirstName(message.getChat().getFirstName());
			chat.setCustomerLastName(message.getChat().getLastName());

			try {
				chatHandler.saveChat(chat);
				logger.info("New User Has been added current users count {}",
						chatHandler.getChatsByBotId(databaseBot.getBotId()).size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return;
		} else {

		}
		return;
	}

	public void run() {
		while (true) {

			List<Notification> notifications = notificationHandler
					.getActiveNotificationsDbByBotId(databaseBot.getBotId());
			for (Notification notification : notifications) {

				logger.info("Bot [{}] sends notification [{}]", databaseBot.getBotUserName(),
						notification.getNotificationBody());

				SendMessage sendMessageRequest = new SendMessage();

				sendMessageRequest.setText(notification.getNotificationBody());
				sendMessageRequest.enableHtml(true);

				if (notification.getChat() != null) {

					sendMessageRequest.setChatId(Long.toString(notification.getChat().getTelegramChatId()));

					try {
						sendMessage(sendMessageRequest);
					} catch (TelegramApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {

					List<Chat> chats = chatHandler.getChatsByBotId(databaseBot.getBotId());
					for (Chat chat : chats) {

						sendMessageRequest.setChatId(Long.toString(chat.getTelegramChatId()));

						try {
							sendMessage(sendMessageRequest);
						} catch (TelegramApiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				notificationHandler.setExecutedNotification(notification);
			}

			try {
				Thread.sleep(TelegramConstants.NOTIFICATION_CHECK_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
