package com.telegram.bot.data.bot.dao;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.telegram.bot.data.bot.model.Bot;
import com.telegram.bot.data.bot.model.Notification;
import com.telegram.bot.data.bot.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@Transactional
public class TestNotificationDao {

	@Autowired
	UserDao userDao;
	
	@Autowired
	BotDao botDao;
	
	@Autowired
	NotificationDao notificationDao;
	
	@Test
	@Rollback
	public void testAddNewNotification()
	{
		User user = new User();
		user.setUserName("ramyfeteha");
		user.setPassword("1234");		
		user.setFirstName("Ramy");
		user.setLastName("Feteha");
		user.setEmail("ramyfeteha@gmail.com");
		
		user = userDao.createEntity(user);
		
		Bot bot = new Bot();
		bot.setBotToken("12345678");
		bot.setBotUserName("botUserName");
		bot.setBotUser(user);
		
		botDao.createEntity(bot);
		
		Notification notification = new Notification();
		notification.setBot(bot);
		notification.setExecuted(false);
		notification.setExpectedRunDate(new Date());
		
		notificationDao.createEntity(notification);
	}
}
