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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "BOT")
@NamedQueries({
@NamedQuery(name = "Bot.findByUser", query = "SELECT b FROM Bot b WHERE b.botUser.userName=:userName"),
@NamedQuery(name = "Bot.findByBotUserName", query = "SELECT b FROM Bot b WHERE b.botUserName=:botUserName")})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bot implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 742782824068455603L;

	
	@Id
	@GenericGenerator(name="bot" , strategy="increment")
	@GeneratedValue(generator="bot")
	@Column(name = "BOT_ID")
	private long botId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User botUser;
	
	@Column(name = "BOT_USER_NAME", nullable = false, unique = true)
	private String botUserName;
	
	@Column(name = "BOT_TOKEN", nullable = false, unique = true)
	private String botToken;
	
	
	@Column(name = "BOT_NAME")
	private String botName;
	
	@Column(name = "BOT_DESC")
	private String botDesc;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;


	public long getBotId() {
		return botId;
	}


	public void setBotId(long botId) {
		this.botId = botId;
	}


	public User getBotUser() {
		return botUser;
	}


	public void setBotUser(User botUser) {
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


	@PrePersist
	protected void onCreate() {
		if (insertionDate == null) {
			insertionDate = new Date();
		}
	}


	@Override
	public String toString() {
		return "Bot [botId=" + botId + ", botUser=" + botUser + ", botUserName=" + botUserName + ", botToken="
				+ botToken + ", botName=" + botName + ", insertionDate=" + insertionDate + "]";
	}
	
	
}
