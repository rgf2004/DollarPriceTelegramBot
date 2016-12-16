package com.telegram.bot.dollarprice.server.bankshandlers.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.telegram.bot.dollarprice.server.bankshandlers.AbstractBankHandler;
import com.telegram.bot.dollarprice.server.beans.BankDetails;
import com.telegram.bot.dollarprice.server.beans.Currency;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

@Service
public class BlomBankEgypt extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {
		
		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Blom Bank Egypt");
		bankDetails.setName_ar(
				"\u0628\u0646\u0643 \u0628\u0644\u0648\u0645 \u0645\u0635\u0631");
		bankDetails.setSlug("BLOOM");
		bankDetails.setHomepage("http://www.blombankegypt.com/");

		return bankDetails;
		
	}

	@Override
	public void getCurrenciesDetails() {
		
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.blombankegypt.com/BlomEgypt/Exchange-rates")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Elements pricesRows = doc.getElementsByClass("tableHolder").select("table").get(0).select("tr");

			for (int i = 2; i < pricesRows.size() - 1; i++) {
				Currency currency = new Currency();

				Elements rowColumns = pricesRows.get(i).select("td");

				
				if (rowColumns.get(0).text().contains("USD"))
					currency.setCurrencyCode(CurrencyCode.USD);
				else if (rowColumns.get(0).text().contains("EURO"))
					currency.setCurrencyCode(CurrencyCode.EUR);
				else if (rowColumns.get(0).text().contains("GBP​"))
					currency.setCurrencyCode(CurrencyCode.GBP);
				else if (rowColumns.get(0).text().contains("CHF"))
					currency.setCurrencyCode(CurrencyCode.CHF);
				else if (rowColumns.get(0).text().contains("100JPY"))
					currency.setCurrencyCode(CurrencyCode.JPY);
				else if (rowColumns.get(0).text().contains("SAR​​​"))
					currency.setCurrencyCode(CurrencyCode.SAR);
				else if (rowColumns.get(0).text().contains("KWD"))
					currency.setCurrencyCode(CurrencyCode.KWD);
				else if (rowColumns.get(0).text().contains("AED"))
					currency.setCurrencyCode(CurrencyCode.AUD);
				else
					continue;
				
				
				
				currency.setCurrencyName(rowColumns.get(0).text());
				currency.setBuyPrice(Double.parseDouble(rowColumns.get(2).text()));
				currency.setSellPrice(Double.parseDouble(rowColumns.get(3).text()));

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
