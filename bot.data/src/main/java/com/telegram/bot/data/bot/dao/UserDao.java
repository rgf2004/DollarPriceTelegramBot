package com.telegram.bot.data.bot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.telegram.bot.data.bot.model.User;
import com.telegram.bot.data.dao.impl.JPADao;

@Repository
public class UserDao extends JPADao<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1373084335521102057L;
	
	public List<User> getUserByUserName(String userName)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);		

		return super.findByNamedQuery("User.findByUserName", params);
	}
	
	public List<User> getUserByUserNameAndPassword(String userName,String password)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("password", password);

		return super.findByNamedQuery("User.findByUserNameAndPassword", params);
	}		
}
