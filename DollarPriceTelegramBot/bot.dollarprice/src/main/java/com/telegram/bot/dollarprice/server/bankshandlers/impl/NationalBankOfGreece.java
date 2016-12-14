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

//@Service
public class NationalBankOfGreece extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {

		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("National Bank of Greece");
		bankDetails.setName_ar(
				"\u0627\u0644\u0628\u0646\u0643 \u0627\ufef7\u0647\u0644\u064a \u0627\u0644\u064a\u0648\u0646\u0627\u0646\u064a");
		bankDetails.setSlug("NBG");
		bankDetails.setHomepage("http://www.nbg.com.eg/");

		return bankDetails;	
	}

	@Override
	protected void getCurrenciesDetails() {
		
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.nbg.com.eg/en/exchange-rates")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();
			
			doc = Jsoup.connect("http://www.nbg.com.eg/en/exchange-rates")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Elements pricesRows = doc.getElementById("exchange").select("tr");

			for (int i = 2 ; i < pricesRows.size() ; i++ ) {

				Elements rowColumns = pricesRows.get(i).select("td");
				
				Currency currency = new Currency();

				if (rowColumns.get(1).text().equalsIgnoreCase("QAR"))
					currency.setCurrencyCode(CurrencyCode
							.QTR);
				else
					currency.setCurrencyCode(CurrencyCode
							.valueOf(rowColumns.get(1).text()));
				
				currency.setCurrencyName(
						rowColumns.get(0).text());				
				currency.setBuyPrice(Double.parseDouble(
						rowColumns.get(2).text()));
				currency.setSellPrice(Double.parseDouble(
						rowColumns.get(3).text()));

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
