package com.telegram.bot.dollarprice.server.bankshandlers.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.telegram.bot.dollarprice.server.bankshandlers.AbstractBankHandler;
import com.telegram.bot.dollarprice.server.beans.BankDetails;
import com.telegram.bot.dollarprice.server.beans.Currency;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

@Service
public class ExportDevelopmentBankOfEgypt extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {
		
		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Export Development Bank of Egypt");
		bankDetails.setName_ar(
				"\u0627\u0644\u0628\u0646\u0643 \u0627\u0644\u0645\u0635\u0631\u064a \u0644\u062a\u0646\u0645\u064a\u0629 \u0627\u0644\u0635\u0627\u062f\u0631\u0627\u062a");
		bankDetails.setSlug("EDBE");
		bankDetails.setHomepage("http://www.edbebank.com/EN/");

		return bankDetails;	
		
	}

	@Override
	public void getCurrenciesDetails() {
		
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.edbebank.com/EN/BankingServices/TreasuryFiles/exchangerates.xml")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();
			
		
			Element pricesRow = doc.select("rates ").get(0);
			
			
			Currency usdCurrency = new Currency();
			usdCurrency.setCurrencyCode(CurrencyCode.USD);
			usdCurrency.setBuyPrice(Double.parseDouble(pricesRow.attr("USDBbuy")));
			usdCurrency.setSellPrice(Double.parseDouble(pricesRow.attr("USDBsell")));										
			currencies.put(usdCurrency.getCurrencyCode(), usdCurrency);
			
			Currency euroCurrency = new Currency();
			euroCurrency.setCurrencyCode(CurrencyCode.EUR);
			euroCurrency.setBuyPrice(Double.parseDouble(pricesRow.attr("EURBbuy")));
			euroCurrency.setSellPrice(Double.parseDouble(pricesRow.attr("EURBsell")));										
			currencies.put(euroCurrency.getCurrencyCode(), euroCurrency);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
