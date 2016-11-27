package com.telegram.bot.data.bot.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CHAT")
@NamedQueries({
@NamedQuery(name = "Chat.findByBotID", query = "SELECT c FROM Chat c WHERE c.bot.botId=:botId"),
@NamedQuery(name = "Chat.findByBotIDAndChatID", query = "SELECT c FROM Chat c WHERE c.bot.botId=:botId and c.telegramChatId=:telegramChatId")})
public class Chat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4252147539771182679L;

	@Id
	@GenericGenerator(name="chat" , strategy="increment")
	@GeneratedValue(generator="chat")
	@Column(name = "CHAT_ID")
	private long chatId;
	
	@Column(name = "TELEGRAM_CHAT_ID")
	private long telegramChatId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BOT_ID")
	private Bot bot;
	
	@Column(name = "CUSTOMER_PHONE_NO")
	private String customerPhoneNo;
	
	@Column(name = "CUSTOMER_USER_NAME")
	private String customerUserName;
	
	@Column(name = "CUSTOMER_FIRST_NAME")
	private String customerFirstName;
	
	@Column(name = "CUSTOMER_LAST_NAME")
	private String customerLastName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_VISIT_DATE")
	private Date lastVisitDate;

	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}
	
	public long getTelegramChatId() {
		return telegramChatId;
	}

	public void setTelegramChatId(long telegramChatId) {
		this.telegramChatId = telegramChatId;
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
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

	public Date getLastVisitDate() {
		return lastVisitDate;
	}

	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}

	@PrePersist
	protected void onCreate() {
		if (insertionDate == null) {
			insertionDate = new Date();
		}
		
		if (lastVisitDate == null) {
			lastVisitDate = new Date();
		}
	}

	@Override
	public String toString() {
		return "Chat [chatId=" + chatId + ", telegramChatId=" + telegramChatId + ", bot=" + bot + ", customerPhoneNo="
				+ customerPhoneNo + ", customerUserName=" + customerUserName + ", customerFirstName="
				+ customerFirstName + ", customerLastName=" + customerLastName + ", insertionDate=" + insertionDate
				+ "]";
	}
			
}
