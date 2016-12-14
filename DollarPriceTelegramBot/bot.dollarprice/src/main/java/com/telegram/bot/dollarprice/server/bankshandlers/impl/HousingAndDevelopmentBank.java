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
public class HousingAndDevelopmentBank extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {
		
		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Housing and Development Bank");
		bankDetails.setName_ar(
				"\u0628\u0646\u0643 \u0627\u0644\u062a\u0639\u0645\u064a\u0631 \u0648\u0627\u0644\u0625\u0633\u0643\u0627\u0646");
		bankDetails.setSlug("HDB");
		bankDetails.setHomepage("http://www.hdb-egy.com/");

		return bankDetails;
		
	}

	@Override
	protected void getCurrenciesDetails() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.hdb-egy.com/main.php?page=exhange")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Elements pricesRows = doc.getElementsByClass("Currency-Table").select("tr");

			for (int i = 0 ; i < pricesRows.size() ; i++ ) {

				Elements rowColumns = pricesRows.get(i).select("td");
				
				Currency currency = new Currency();

				switch(rowColumns.get(0).text())
				{
				case "دولار امريكى":
					currency.setCurrencyCode(CurrencyCode.USD);
					break;
					
				case "جنيه استرلينى":
					currency.setCurrencyCode(CurrencyCode.GBP);
					break;
				
				case "دينار كويتى":
					currency.setCurrencyCode(CurrencyCode.KWD);
					break;
					
				case "ريال سعودى":
					currency.setCurrencyCode(CurrencyCode.SAR);
					break;
					
				case "درهم الإمارات":
					currency.setCurrencyCode(CurrencyCode.AED);
					break;
					
				case "اليورو":
					currency.setCurrencyCode(CurrencyCode.EUR);
					break;
					
				default:
					continue;						
				}
				
				currency.setCurrencyName(
						rowColumns.get(0).text());				
				currency.setBuyPrice(Double.parseDouble(
						rowColumns.get(2).text()));
				currency.setSellPrice(Double.parseDouble(
						rowColumns.get(4).text()));

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

}
