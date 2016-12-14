package com.telegram.bot.dollarprice.server.bankshandlers.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.telegram.bot.dollarprice.server.bankshandlers.AbstractBankHandler;
import com.telegram.bot.dollarprice.server.beans.BankDetails;
import com.telegram.bot.dollarprice.server.beans.Currency;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

@Service
public class BankNationalAhliBank extends AbstractBankHandler {
	
	@Override
	protected void getCurrenciesDetails() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.nbe.com.eg/ExchangeRate.aspx")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Element pricesTable = doc.getElementById("dgPrices");
			Elements pricesRows = pricesTable.select("tr");
			for (int i = 1; i < pricesRows.size(); i++) {
				Currency currency = new Currency();

				Elements rowColumns = pricesRows.get(i).select("td");

				currency.setCurrencyName(rowColumns.get(0).text());
				currency.setCurrencyCode(CurrencyCode.valueOf(rowColumns.get(1).text()));
				currency.setBuyPrice(Double.parseDouble(rowColumns.get(2).select("input").get(0).attr("value")));
				currency.setSellPrice(Double.parseDouble(rowColumns.get(3).select("input").get(0).attr("value")));

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public BankDetails getBankDetails() {
		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("National Bank of Egypt");
		bankDetails.setName_ar("\u0627\u0644\u0628\u0646\u0643 \u0627\ufef7\u0647\u0644\u064a \u0627\u0644\u0645\u0635\u0631\u064a");
		bankDetails.setSlug("NBE");
		bankDetails.setHomepage("http://www.nbe.com.eg/en/Main.aspx");			

		return bankDetails;
	}

}
