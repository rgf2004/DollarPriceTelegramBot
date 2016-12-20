package com.telegram.bot.data.bot.beans;

import java.io.Serializable;
import java.util.Date;

import com.telegram.bot.data.bot.model.Chat;

public class ChatBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5035037221543892077L;

	private long telegramChatId;
	private String botUserName;
	private String customerPhoneNo;
	private String customerUserName;
	private String customerFirstName;
	private String customerLastName;
	private Date insertionDate;

	public ChatBean(Chat chat) {
		telegramChatId = chat.getTelegramChatId();
		botUserName = new String(chat.getBot().getBotUserName());
		customerPhoneNo = new String(chat.getCustomerPhoneNo());
		customerUserName = new String(chat.getCustomerUserName());
		customerFirstName = new String(chat.getCustomerFirstName());
		customerLastName = new String(chat.getCustomerLastName());
	}

	public long getTelegramChatId() {
		return telegramChatId;
	}

	public void setTelegramChatId(long telegramChatId) {
		this.telegramChatId = telegramChatId;
	}

	public String getBotUserName() {
		return botUserName;
	}

	public void setBotUserName(String botUserName) {
		this.botUserName = botUserName;
	}

	public String getCustomerPhoneNo() {
		return customerPhoneNo;
	}

	public void setCustomerPhoneNo(String customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	@Override
	public String toString() {
		return "ChatBean [telegramChatId=" + telegramChatId + ", botUserName=" + botUserName + ", customerPhoneNo="
				+ customerPhoneNo + ", customerUserName=" + customerUserName + ", customerFirstName="
				+ customerFirstName + ", customerLastName=" + customerLastName + ", insertionDate=" + insertionDate
				+ "]";
	}
}
