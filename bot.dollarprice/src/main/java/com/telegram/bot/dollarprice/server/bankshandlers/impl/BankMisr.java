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
public class BankMisr extends AbstractBankHandler {

	@Override
	public void getCurrenciesDetails() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.banquemisr.com/en/exchangerates")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Element pricesTable = doc.getElementsByClass("exchangeRates").get(0);
			Elements pricesRows = pricesTable.select("tr");
			for (int i = 2; i < pricesRows.size(); i++) {
				Currency currency = new Currency();

				Elements rowColumns = pricesRows.get(i).select("td");
								
				switch (rowColumns.get(0).text())
				{
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
				case "SAUDI RIYAL":
					currency.setCurrencyCode(CurrencyCode.SAR);
					break;
				case "QATARI RIAL":
					currency.setCurrencyCode(CurrencyCode.QTR);
					break;
				case "OMAN RIYAL":
					currency.setCurrencyCode(CurrencyCode.OMR);
					break;
				case "NORWAY KRONE":
					currency.setCurrencyCode(CurrencyCode.NOK);
					break;
				case "KUWAIT DINAR":
					currency.setCurrencyCode(CurrencyCode.KWD);
					break;
				case "JORDANIAN DINAR":
					currency.setCurrencyCode(CurrencyCode.JOD);
					break;
				case "JAPAN YEN":
					currency.setCurrencyCode(CurrencyCode.JPY);
					break;
				case "DENMARK KRONE":
					currency.setCurrencyCode(CurrencyCode.DKK);
					break;
				case "CANADA DOLLAR":
					currency.setCurrencyCode(CurrencyCode.CAD);
					break;
				case "GB POUND":
					currency.setCurrencyCode(CurrencyCode.GBP);
					break;
				case "BAHRAIN DINAR":
					currency.setCurrencyCode(CurrencyCode.BAD);
					break;
				case "AUSTRALIA DOLLAR":
					currency.setCurrencyCode(CurrencyCode.AUD);
					break;
				case "UAE DIRHAM":
					currency.setCurrencyCode(CurrencyCode.AED);
					break;
				case "CYPRUS POUND":
					currency.setCurrencyCode(CurrencyCode.NAN);
					break;
				default:
					throw new IllegalArgumentException("Can't find " + rowColumns.get(0).text() );
				}
				
				if (currency.getCurrencyCode() == CurrencyCode.NAN)
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

	@Override
	public BankDetails getBankDetails() {
		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Banque Misr");
		bankDetails.setName_ar("\u0628\u0646\u0643 \u0645\u0635\u0631");
		bankDetails.setSlug("BM");
		bankDetails.setHomepage("http://www.banquemisr.com/");			

		return bankDetails;
	}
	
}
