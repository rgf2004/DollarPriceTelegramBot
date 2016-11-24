package com.telegram.bot.data.bot.handler;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.bot.data.bot.beans.UserBean;
import com.telegram.bot.data.bot.dao.UserDao;
import com.telegram.bot.data.bot.model.User;

@Service
public class UserHandler {

	@Autowired
	UserDao userDao;
	
	@Transactional
	public boolean saveUser(User user) throws Exception 
	{
		try
		{
			userDao.createEntity(user);
			return true;
		}
		catch (Exception ex)
		{
			throw ex;
		}		
	}
		
	public List<UserBean> getAllUsers()
	{
		List<User> dbUsers = userDao.findAll();
		List<UserBean> users = new ArrayList<>();
		
		for(User user : dbUsers)
			users.add(new UserBean(user));
		
		return users;
	}
	
	public List<UserBean> getUserByUserName(String userName)
	{
		List<User> dbUsers = userDao.getUserByUserName(userName);
		List<UserBean> users = new ArrayList<>();
		
		for(User user : dbUsers)
			users.add(new UserBean(user));
		
		return users;
	}
	
	public List<UserBean> getUserByUserNameAndPassword(String userName,String password)
	{
		List<User> dbUsers = userDao.getUserByUserNameAndPassword(userName, password);
		List<UserBean> users = new ArrayList<>();
		
		for(User user : dbUsers)
			users.add(new UserBean(user));
		
		return users;
	}
}
