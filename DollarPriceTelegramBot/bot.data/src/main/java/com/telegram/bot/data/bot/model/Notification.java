package com.telegram.bot.data.bot.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NOTIFICATION")
@NamedQueries({
@NamedQuery(name = "Notification.findActiveNotificationsByBotId", query = "SELECT n FROM Notification n WHERE n.bot.botId=:botId and n.executed=false"),
@NamedQuery(name = "Notification.findNotificationsByBotUserName", query = "SELECT n FROM Notification n WHERE n.bot.botUserName=:botUserName")
})
public class Notification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4550661829605905227L;

	@Id
	@GenericGenerator(name="notification" , strategy="increment")
	@GeneratedValue(generator="notification")
	@Column(name = "NOTIFICATION_ID")
	private long notificationId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BOT_ID")
	private Bot bot;
	
	@Column(name = "EXECUTED")
	private boolean executed;
	
	@Column(name = "NOTIFICATION_BODY")
	private String notificationBody;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CHAT_ID")
	private Chat chat;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERTION_DATE")
	private Date insertionDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPECTED_RUNNING_DATE")
	private Date expectedRunDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTUAL_RUNNING_DATE")
	private Date actualRunDate;

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
	
	public String getNotificationBody() {
		return notificationBody;
	}

	public void setNotificationBody(String notificationBody) {
		this.notificationBody = notificationBody;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	public Date getExpectedRunDate() {
		return expectedRunDate;
	}

	public void setExpectedRunDate(Date expectedRunDate) {
		this.expectedRunDate = expectedRunDate;
	}

	public Date getActualRunDate() {
		return actualRunDate;
	}

	public void setActualRunDate(Date actualRunDate) {
		this.actualRunDate = actualRunDate;
	}
	
	@PrePersist
	protected void onCreate() {
		if (insertionDate == null) {
			insertionDate = new Date();
		}
	}
	
	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualRunDate == null) ? 0 : actualRunDate.hashCode());
		result = prime * result + ((bot == null) ? 0 : bot.hashCode());
		result = prime * result + (executed ? 1231 : 1237);
		result = prime * result + ((expectedRunDate == null) ? 0 : expectedRunDate.hashCode());
		result = prime * result + ((insertionDate == null) ? 0 : insertionDate.hashCode());
		result = prime * result + (int) (notificationId ^ (notificationId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (actualRunDate == null) {
			if (other.actualRunDate != null)
				return false;
		} else if (!actualRunDate.equals(other.actualRunDate))
			return false;
		if (bot == null) {
			if (other.bot != null)
				return false;
		} else if (!bot.equals(other.bot))
			return false;
		if (executed != other.executed)
			return false;
		if (expectedRunDate == null) {
			if (other.expectedRunDate != null)
				return false;
		} else if (!expectedRunDate.equals(other.expectedRunDate))
			return false;
		if (insertionDate == null) {
			if (other.insertionDate != null)
				return false;
		} else if (!insertionDate.equals(other.insertionDate))
			return false;
		if (notificationId != other.notificationId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", bot=" + bot + ", executed=" + executed
				+ ", insertionDate=" + insertionDate + ", expectedRunDate=" + expectedRunDate + ", actualRunDate="
				+ actualRunDate + "]";
	}
	
	
	
}
