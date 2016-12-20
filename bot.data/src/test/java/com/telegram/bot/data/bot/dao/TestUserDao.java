package com.telegram.bot.data.bot.dao;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.telegram.bot.data.bot.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@Transactional
public class TestUserDao {

	@Autowired
	UserDao userDao;
	
	@Test
	@Rollback
	public void testAddNewUser()
	{
		User user = new User();
		user.setUserName("ramy123");
		user.setPassword("12345678");		
		user.setFirstName("Ramy");
		user.setLastName("Feteha");
		user.setEmail("ramyfeteha@gmail.com");
		
		userDao.createEntity(user);
		
	}
	
}
