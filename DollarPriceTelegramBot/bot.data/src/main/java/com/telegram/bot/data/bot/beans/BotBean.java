package com.telegram.bot.data.bot.beans;

import java.io.Serializable;
import java.util.Date;

import com.telegram.bot.data.bot.model.Bot;

public class BotBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3691645122747289143L;
	
	private String botUser ;
	private String botUserName;
	private String botToken;
	private String botName;
	private String botDesc;
	private Date insertionDate;
	
	
	public BotBean(Bot bot)
	{
		botUser = new String(bot.getBotUser().getUserName());
		botUserName = new String(bot.getBotUserName());
		botToken = new String(bot.getBotToken());
		botName = new String(bot.getBotName());
		botDesc = new String(bot.getBotDesc());
		insertionDate = new Date(bot.getInsertionDate().getTime());
	}


	public String getBotUser() {
		return botUser;
	}


	public void setBotUser(String botUser) {
		this.botUser = botUser;
	}


	public String getBotUserName() {
		return botUserName;
	}


	public void setBotUserName(String botUserName) {
		this.botUserName = botUserName;
	}


	public String getBotToken() {
		return botToken;
	}


	public void setBotToken(String botToken) {
		this.botToken = botToken;
	}


	public String getBotName() {
		return botName;
	}


	public void setBotName(String botName) {
		this.botName = botName;
	}


	public String getBotDesc() {
		return botDesc;
	}


	public void setBotDesc(String botDesc) {
		this.botDesc = botDesc;
	}


	public Date getInsertionDate() {
		return insertionDate;
	}


	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	@Override
	public String toString() {
		return "BotBean [botUser=" + botUser + ", botUserName=" + botUserName + ", botToken=" + botToken + ", botName="
				+ botName + ", botDesc=" + botDesc + ", insertionDate=" + insertionDate + "]";
	}
}
