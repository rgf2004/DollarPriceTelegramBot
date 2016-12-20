package com.telegram.bot.core.exceptions;

public class ValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4103887789535049717L;

	public ValidationException()
	{
		super();
	}
	
	public ValidationException(String message, Throwable ex)
	{
		super(message, ex);
	}
	
}
