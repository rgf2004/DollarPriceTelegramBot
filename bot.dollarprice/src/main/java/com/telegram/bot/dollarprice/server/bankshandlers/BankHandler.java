package com.telegram.bot.dollarprice.server.bankshandlers;

import com.telegram.bot.dollarprice.server.beans.BankDetails;
import com.telegram.bot.dollarprice.server.beans.Currency;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

public interface BankHandler {

	public void initializeHandler();
	
	public Currency getCurrencyDetail(CurrencyCode currencyCode);
	
	public BankDetails getBankDetails();
	
	public void getCurrenciesDetails();
		
}
