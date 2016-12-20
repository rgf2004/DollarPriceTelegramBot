package com.telegram.bot.dollarprice.server.bankshandlers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.bot.core.helper.SleepTimeGenerator;
import com.telegram.bot.dollarprice.server.beans.Currency;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

@Service
public abstract class AbstractBankHandler implements BankHandler {

	@Autowired
	SleepTimeGenerator sleepTimeGenerator;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Map<CurrencyCode, Currency> currencies = new HashMap<>();

	public final void initializeHandler() {
		logger.info("Start Initialize of Bank Handler [{}] ...", this.getBankDetails().getName());
		getCurrenciesDetails();		
	}

	@Override
	public final Currency getCurrencyDetail(CurrencyCode currencyCode) {
		return currencies.get(currencyCode);
	}
	
}
