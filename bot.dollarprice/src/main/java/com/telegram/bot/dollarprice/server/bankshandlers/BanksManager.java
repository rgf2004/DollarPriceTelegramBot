package com.telegram.bot.dollarprice.server.bankshandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.bot.core.helper.SleepTimeGenerator;
import com.telegram.bot.dollarprice.beans.BankPrice;
import com.telegram.bot.dollarprice.server.beans.Currency;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

@Service
public class BanksManager implements Runnable{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SleepTimeGenerator sleepTimeGenerator;
	
	@Autowired
	List<BankHandler> bankHandlers;

	@PostConstruct
	private void init() {
		for (BankHandler bankHandler : bankHandlers) {
			bankHandler.initializeHandler();
		}
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@SuppressWarnings("unchecked")
	public List<BankPrice> getCurrencyBanksPrice(CurrencyCode currencyCode) {
		List<BankPrice> bankPrices = new ArrayList<>();

		for (BankHandler bankHandler : bankHandlers) {

			Currency currency = bankHandler.getCurrencyDetail(currencyCode);

			if (currency != null) {

				BankPrice bankPrice = new BankPrice();
				bankPrice.setBank(bankHandler.getBankDetails());
				bankPrice.setBuy(currency.getBuyPrice());
				bankPrice.setSell(currency.getSellPrice());

				bankPrices.add(bankPrice);
			}
		}
		
		Collections.sort(bankPrices);
		
		return bankPrices;
	}
	
	@Override
	public final void run() {
		while (true) {
			try {
				int sleepTime = sleepTimeGenerator.getRandomSleepTime();
				logger.info("Bank Manager will sleep for {} mSec", sleepTime);
				Thread.sleep(sleepTime);
				
				for (BankHandler bankHandler : bankHandlers) {
					logger.info("Bank Handler [{}] will refresh its details", bankHandler.getBankDetails().getName());
					bankHandler.getCurrenciesDetails();
				}
								
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
