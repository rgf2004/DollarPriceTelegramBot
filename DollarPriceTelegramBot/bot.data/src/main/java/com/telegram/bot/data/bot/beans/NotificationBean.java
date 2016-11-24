package com.telegram.bot.data.bot.beans;

import java.io.Serializable;
import java.util.Date;

import com.telegram.bot.data.bot.model.Notification;

public class NotificationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7871406865766080762L;

	private String botUserName;
	private boolean executed;
	private String notificationBody;	
	private Date insertionDate;
	private Date expectedRunDate;
	private Date actualRunDate;
	
	public NotificationBean(Notification notification)
	{
		botUserName = new String(notification.getBot().getBotUserName());
		executed = notification.isExecuted();
		notificationBody = new String(notification.getNotificationBody());
		insertionDate = new Date(notification.getInsertionDate().getTime());
		expectedRunDate = new Date(notification.getExpectedRunDate().getTime());
		actualRunDate = new Date(notification.getActualRunDate().getTime());
	}

	public String getBotUserName() {
		return botUserName;
	}

	public void setBotUserName(String botUserName) {
		this.botUserName = botUserName;
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

	@Override
	public String toString() {
		return "NotificationBean [botUserName=" + botUserName + ", executed=" + executed + ", notificationBody="
				+ notificationBody + ", insertionDate=" + insertionDate + ", expectedRunDate=" + expectedRunDate
				+ ", actualRunDate=" + actualRunDate + "]";
	}
}
