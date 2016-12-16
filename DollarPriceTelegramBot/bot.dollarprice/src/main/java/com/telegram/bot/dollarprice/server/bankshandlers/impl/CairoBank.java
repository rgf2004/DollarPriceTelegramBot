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
public class CairoBank extends AbstractBankHandler {

	@Override
	public void getCurrenciesDetails() {

		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.banqueducaire.com/Arabic/MarketUpdates/Pages/CurrencyExchange.aspx")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Element pricesTable = doc.getElementsByClass("curTbl").get(0);
			Elements pricesRows = pricesTable.select("tr");
			for (int i = 2; i < pricesRows.size(); i++) {
				Currency currency = new Currency();

				Elements rowColumns = pricesRows.get(i).select("td");

				currency.setCurrencyName(rowColumns.get(0).text());

				switch (rowColumns.get(0).text()) {
				case "US DOLLAR":
					currency.setCurrencyCode(CurrencyCode.USD);
					break;

				case "EURO":
					currency.setCurrencyCode(CurrencyCode.EUR);
					break;
				case "SWISS FRANC":
					currency.setCurrencyCode(CurrencyCode.CHF);
					break;
				case "SWEDISH KRONA":
					currency.setCurrencyCode(CurrencyCode.SEK);
					break;
				case "SAUDI ARABIAN RIYAL":
					currency.setCurrencyCode(CurrencyCode.SAR);
					break;
				case "QATAR RIAL":
					currency.setCurrencyCode(CurrencyCode.QTR);
					break;
				case "OMANI RIAL":
					currency.setCurrencyCode(CurrencyCode.OMR);
					break;
				case "NORWEGIAN KRONE":
					currency.setCurrencyCode(CurrencyCode.NOK);
					break;
				case "KUWAITI DINAR":
					currency.setCurrencyCode(CurrencyCode.KWD);
					break;
				case "JORDANIAN DINAR":
					currency.setCurrencyCode(CurrencyCode.JOD);
					break;
				case "JAPANESE YEN":
					currency.setCurrencyCode(CurrencyCode.JPY);
					break;
				case "DANISH KRONE":
					currency.setCurrencyCode(CurrencyCode.DKK);
					break;
				case "CANADIAN DOLLAR":
					currency.setCurrencyCode(CurrencyCode.CAD);
					break;
				case "BRITISH POUND":
					currency.setCurrencyCode(CurrencyCode.GBP);
					break;
				case "BAHRAIN DINAR":
					currency.setCurrencyCode(CurrencyCode.BAD);
					break;
				case "AUSTRALIAN DOLLAR":
					currency.setCurrencyCode(CurrencyCode.AUD);
					break;
				case "ARAB EMIRATES DIRHAM":
					currency.setCurrencyCode(CurrencyCode.AED);
					break;
				}

				currency.setBuyPrice(Double.parseDouble(rowColumns.get(1).text()));
				currency.setSellPrice(Double.parseDouble(rowColumns.get(2).text()));

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

		bankDetails.setName("Banque du Caire");
		bankDetails.setHomepage("http://www.banqueducaire.com/");
		bankDetails.setSlug("BDC");
		bankDetails.setName_ar("\u0628\u0646\u0643 \u0627\u0644\u0642\u0627\u0647\u0631\u0629");

		return bankDetails;
	}

}
