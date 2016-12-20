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
public class ArabAfricanInternationalBank extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {

		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Arab African International Bank");
		bankDetails.setName_ar(
				"\u0627\u0644\u0628\u0646\u0643 \u0627\u0644\u0639\u0631\u0628\u064a \u0627\ufef7\u0641\u0631\u064a\u0642\u064a \u0627\u0644\u062f\u0648\u0644\u064a");
		bankDetails.setSlug("AAIB");
		bankDetails.setHomepage("http://aaib.com/");

		return bankDetails;

	}

	@Override
	public void getCurrenciesDetails() {

		Document doc = null;
		try {
			doc = Jsoup.connect("http://aaib.com/en/rates")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Elements pricesRows = doc.getElementById("currency-rates").select("table").select("tr");

			for (int i = 1; i < pricesRows.size() - 1; i++) {
				Currency currency = new Currency();

				Elements rowColumns = pricesRows.get(i).select("td");

				switch (rowColumns.get(0).text()) {
				case "US DOLLAR":
					currency.setCurrencyCode(CurrencyCode.USD);
					break;

				case "EURO CURRENCY":
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
				case "KUWAITI DINAR":
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
				case "POUND STERLING":
					currency.setCurrencyCode(CurrencyCode.GBP);
					break;
				case "BAHRAIN DINAR":
					currency.setCurrencyCode(CurrencyCode.BAD);
					break;
				case "AUSTRALIA DOLLAR":
					currency.setCurrencyCode(CurrencyCode.AUD);
					break;
				case "U.A.E DIRHAM":
					currency.setCurrencyCode(CurrencyCode.AED);
					break;
				case "CYPRUS POUND":
					currency.setCurrencyCode(CurrencyCode.NAN);
					break;
				default:
					throw new IllegalArgumentException("Can't find " + rowColumns.get(0).text());
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

}
