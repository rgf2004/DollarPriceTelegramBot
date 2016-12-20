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
public class CentralBankOfEgypt extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {
		
		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Central Bank of Egypt");
		bankDetails.setName_ar(
				"\u0627\u0644\u0628\u0646\u0643 \u0627\u0644\u0645\u0631\u0643\u0632\u064a \u0627\u0644\u0645\u0635\u0631\u064a");
		bankDetails.setSlug("CBE");
		bankDetails.setHomepage("http://www.cbe.org.eg/");

		return bankDetails;
		
	}

	@Override
	public void getCurrenciesDetails() {
		
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.cbe.org.eg/en/EconomicResearch/Statistics/Pages/ExchangeRatesListing.aspx")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Elements pricesRows = doc.getElementsByClass("table").get(0).select("tr");

			for (int i = 1; i < pricesRows.size() - 1; i++) {
				Currency currency = new Currency();

				Elements rowColumns = pricesRows.get(i).select("td");

				
				if (rowColumns.get(0).text().contains("US Dollar​"))
					currency.setCurrencyCode(CurrencyCode.USD);
				else if (rowColumns.get(0).text().contains("Euro"))
					currency.setCurrencyCode(CurrencyCode.EUR);
				else if (rowColumns.get(0).text().contains("Pound Sterling​"))
					currency.setCurrencyCode(CurrencyCode.GBP);
				else if (rowColumns.get(0).text().contains("Swiss Franc​"))
					currency.setCurrencyCode(CurrencyCode.CHF);
				else if (rowColumns.get(0).text().contains("Japanese Yen 100​​"))
					currency.setCurrencyCode(CurrencyCode.JPY);
				else if (rowColumns.get(0).text().contains("Saudi Riyal​​​"))
					currency.setCurrencyCode(CurrencyCode.SAR);
				else if (rowColumns.get(0).text().contains("Kuwaiti Dinar"))
					currency.setCurrencyCode(CurrencyCode.KWD);
				else if (rowColumns.get(0).text().contains("UAE Dirham"))
					currency.setCurrencyCode(CurrencyCode.AUD);
				else
					continue;
				
				
				
				currency.setCurrencyName(rowColumns.get(0).text());
				currency.setBuyPrice(Double.parseDouble(rowColumns.get(1).text()));
				currency.setSellPrice(Double.parseDouble(rowColumns.get(2).text()));

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
