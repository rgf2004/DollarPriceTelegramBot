package com.telegram.bot.dollarprice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.telegram.bot.business.beans.TelegramBot;
import com.telegram.bot.data.bot.model.Bot;
import com.telegram.bot.dollarprice.DollarPriceApi;

public class DollarPriceTelegramBot extends TelegramBot
{
	
	DollarPriceApi dollarPriceApi;
	
	public DollarPriceTelegramBot(ApplicationContext ctxt, Bot bot) {
		super(ctxt, bot);
		
		dollarPriceApi = ctxt.getBean(DollarPriceApi.class);
	}

	@Override
	public SendPhoto handleIncomingMessage(Update update) {
				
		String chatID = null;
		String userQuery = null;
		
		if (update.getMessage() != null)
		{
			handleFirstTime(update.getMessage());
			chatID = update.getMessage().getChatId().toString();
			userQuery = update.getMessage().getText();
		}
		else
		{
			chatID = update.getCallbackQuery().getMessage().getChatId().toString();
			userQuery = update.getCallbackQuery().getData();
		}
		
		SendPhoto sendPhotoRequest = new SendPhoto();
		sendPhotoRequest.setNewPhoto(dollarPriceApi.getUSDCurrentCurrencyDetailsImage(userQuery));
		//sendPhotoRequest.setCaption(dollarPriceApi.getImageCaption());
		sendPhotoRequest.setReplyMarkup(getKeyboardMarkup(update));
		sendPhotoRequest.setChatId(chatID);
		
		return sendPhotoRequest;

	}
	
	
	private ReplyKeyboard getKeyboardMarkup(Update update)
	{	
		 
		InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
		
		//replyKeyboardMarkup.setSelective(true);
		//replyKeyboardMarkup.setResizeKeyboard(true);
		//replyKeyboardMarkup.setOneTimeKeyboad(false);
		
		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
		replyKeyboardMarkup.setKeyboard(keyboard);
		
		for (String keyboardText : dollarPriceApi.getKeyboards())
		{
			List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
			InlineKeyboardButton button = new InlineKeyboardButton();
			button.setText(keyboardText);
			button.setCallbackData(keyboardText);
			keyboardRow.add(button);
			keyboard.add(keyboardRow);			
		}
					
		return replyKeyboardMarkup;
	}
	
}


