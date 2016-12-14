package com.telegram.bot.dollarprice.server.bankshandlers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.bot.dollarprice.beans.BankPrice;
import com.telegram.bot.dollarprice.server.beans.Currency;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

@Service
public class BanksManager {

	@Autowired
	List<BankHandler> bankHandlers;

	@PostConstruct
	private void init() {
		for (BankHandler bankHandler : bankHandlers) {
			bankHandler.initializeHandler();
		}
	}

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
		return bankPrices;
	}
}
