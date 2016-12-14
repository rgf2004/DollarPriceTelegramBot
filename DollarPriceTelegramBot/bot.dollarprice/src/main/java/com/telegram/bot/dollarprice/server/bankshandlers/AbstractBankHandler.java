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
public abstract class AbstractBankHandler implements BankHandler, Runnable {

	@Autowired
	SleepTimeGenerator sleepTimeGenerator;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Map<CurrencyCode, Currency> currencies = new HashMap<>();

	protected abstract void getCurrenciesDetails();

	public final void initializeHandler() {
		logger.info("Start Initialize of Bank Handler [{}] ...", this.getBankDetails().getName());
		getCurrenciesDetails();
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public final Currency getCurrencyDetail(CurrencyCode currencyCode) {
		return currencies.get(currencyCode);
	}

	@Override
	public final void run() {
		while (true) {
			try {
				int sleepTime = sleepTimeGenerator.getRandomSleepTime();
				logger.info("Bank Handler will sleep for {} mSec", sleepTime);
				Thread.sleep(sleepTime);
				getCurrenciesDetails();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
