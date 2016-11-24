package com.telegram.bot.data.bot.beans;

import java.io.Serializable;
import java.util.Date;

import com.telegram.bot.data.bot.model.User;

public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6104740113544821140L;

	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Date insertionDate;
	
	public UserBean(User user)
	{
		userName = new String(user.getUserName());
		//password = new String(user.getPassword());
		firstName = new String(user.getFirstName());
		lastName = new String(user.getLastName());
		email = new String(user.getEmail());
		insertionDate = new Date(user.getInsertionDate().getTime());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	@Override
	public String toString() {
		return "UserBean [userName=" + userName + ", password= *** " +   ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", insertionDate=" + insertionDate + "]";
	}
			
}
