package com.telegram.bot.data.bot.dao.warpper;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;

import com.telegram.bot.data.bot.model.User;

public class TestUserDaoWrapper {

	@Test
	@Rollback
	public void testAddNewUserByWrapper()
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:**/applicationContext*.xml");
		
		User user = new User();
		user.setUserName("ramyfeteha");
		user.setPassword("1234");		
		user.setFirstName("Ramy");
		user.setLastName("Feteha");
		user.setEmail("ramyfeteha@gmail.com");
				
		//UserDaoWrapper.getUserDao(context).createEntity(user);
	}
	
}
