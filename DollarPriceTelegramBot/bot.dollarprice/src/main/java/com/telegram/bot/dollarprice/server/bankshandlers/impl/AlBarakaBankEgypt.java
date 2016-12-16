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
public class AlBarakaBankEgypt extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {

		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("AlBaraka Bank Egypt");
		bankDetails.setName_ar(
				"\u0628\u0646\u0643 \u0627\u0644\u0628\u0631\u0643\u0629 \u0645\u0635\u0631");
		bankDetails.setSlug("BBE");
		bankDetails.setHomepage("http://www.albaraka-bank.com.eg/");

		return bankDetails;
		
	}

	@Override
	public void getCurrenciesDetails() {
		
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.albaraka-bank.com.eg/%D8%A7%D9%84%D8%AE%D8%AF%D9%85%D8%A7%D8%AA-%D8%A7%D9%84%D9%85%D8%B5%D8%B1%D9%81%D9%8A%D8%A9/%D8%A7%D8%B3%D8%B9%D8%A7%D8%B1-%D8%A7%D9%84%D8%B5%D8%B1%D9%81.aspx")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Elements pricesRows = doc.getElementById("sidebar").select("table").get(0).select("tr");

			for (int i = 1 ; i < pricesRows.size() ; i++ ) {

				Elements rowColumns = pricesRows.get(i).select("td");
				
				Currency currency = new Currency();

				switch (rowColumns.get(1).text())
				{
				case "EURO":
					currency.setCurrencyCode(CurrencyCode.EUR);
					break;
				
				case "BHD":
					currency.setCurrencyCode(CurrencyCode.BAD);
					break;
				
				default:
					currency.setCurrencyCode(CurrencyCode
							.valueOf(rowColumns.get(1).text()));
				}
				
				
				currency.setCurrencyName(
						rowColumns.get(0).text());
				currency.setBuyPrice(Double.parseDouble(
						rowColumns.get(2).text())/100);
				currency.setSellPrice(Double.parseDouble(
						rowColumns.get(3).text())/100);

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}

}
