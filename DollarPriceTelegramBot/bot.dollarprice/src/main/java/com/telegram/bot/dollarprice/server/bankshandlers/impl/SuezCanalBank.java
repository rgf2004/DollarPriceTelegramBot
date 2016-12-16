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
public class SuezCanalBank extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {
		
		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Suez Canal Bank");
		bankDetails.setName_ar(
				"\u0628\u0646\u0643 \u0642\u0646\u0627\u0629 \u0627\u0644\u0633\u0648\u064a\u0633");
		bankDetails.setSlug("SCB");
		bankDetails.setHomepage("http://scbank.com.eg/");

		return bankDetails;
	}

	@Override
	public void getCurrenciesDetails() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://scbank.com.eg/CurrencyAll.aspx")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Element pricesTable = doc.select("table").get(14);
			Elements pricesRows = pricesTable.select("tr");
			for (int i = 2; i < pricesRows.size(); i++) {
				Currency currency = new Currency();

				Elements rowColumns = pricesRows.get(i).select("td");
								
				switch (rowColumns.get(1).text())
				{
				case "US Dollar":
					currency.setCurrencyCode(CurrencyCode.USD);
					break;
					
				case "EUR":
					currency.setCurrencyCode(CurrencyCode.EUR);
					break;
				case "Swiss Franc":
					currency.setCurrencyCode(CurrencyCode.CHF);
					break;
				case "Swedish Krone":
					currency.setCurrencyCode(CurrencyCode.SEK);
					break;
				case "Saudi Rial":
					currency.setCurrencyCode(CurrencyCode.SAR);
					break;
				case "QATARI RIAL":
					currency.setCurrencyCode(CurrencyCode.QTR);
					break;
				case "OMAN RIYAL":
					currency.setCurrencyCode(CurrencyCode.OMR);
					break;
				case "Norwegian Krone":
					currency.setCurrencyCode(CurrencyCode.NOK);
					break;
				case "Kuwaiti Dinar":
					currency.setCurrencyCode(CurrencyCode.KWD);
					break;
				case "JORDANIAN DINAR":
					currency.setCurrencyCode(CurrencyCode.JOD);
					break;
				case "YEN":
					currency.setCurrencyCode(CurrencyCode.JPY);
					break;
				case "Danish Krone":
					currency.setCurrencyCode(CurrencyCode.DKK);
					break;
				case "Canadian Dollar":
					currency.setCurrencyCode(CurrencyCode.CAD);
					break;
				case "Sterling Pound":
					currency.setCurrencyCode(CurrencyCode.GBP);
					break;
				case "BAHRAIN DINAR":
					currency.setCurrencyCode(CurrencyCode.BAD);
					break;
				case "Australian Dollar":
					currency.setCurrencyCode(CurrencyCode.AUD);
					break;
				case "UAE Dirham":
					currency.setCurrencyCode(CurrencyCode.AED);
					break;
				default:
					throw new IllegalArgumentException("Can't find " + rowColumns.get(1).text() );
				}
				
				if (currency.getCurrencyCode() == CurrencyCode.NAN)
					continue;
				
				currency.setCurrencyName(rowColumns.get(1).text());
				currency.setBuyPrice(Double.parseDouble(rowColumns.get(2).select("span").get(0).text()));
				currency.setSellPrice(Double.parseDouble(rowColumns.get(3).select("span").get(0).text()));

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
