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
public class MisrIranDevelopmentBank extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {
		
		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Misr Iran Development Bank");
		bankDetails.setName_ar(
				"\u0628\u0646\u0643 \u0645\u0635\u0631 \u0625\u064a\u0631\u0627\u0646 \u0644\u0644\u062a\u0646\u0645\u064a\u0629");
		bankDetails.setSlug("MIDB");
		bankDetails.setHomepage("http://www.midb.com.eg/");

		return bankDetails;
		
	}

	@Override
	public void getCurrenciesDetails() {
		
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.midb.com.eg/currency.aspx")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Elements pricesRows = doc.getElementById("MainContent_grdcurrency").select("tr");

			for (int i = 1 ; i < pricesRows.size() ; i++ ) {

				Elements rowColumns = pricesRows.get(i).select("td");
				
				Currency currency = new Currency();

				currency.setCurrencyCode(CurrencyCode
						.valueOf(rowColumns.get(0).text()));
				currency.setCurrencyName(
						rowColumns.get(0).text());
				currency.setSellPrice(Double.parseDouble(
						rowColumns.get(1).text()));
				currency.setBuyPrice(Double.parseDouble(
						rowColumns.get(2).text()));				

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
