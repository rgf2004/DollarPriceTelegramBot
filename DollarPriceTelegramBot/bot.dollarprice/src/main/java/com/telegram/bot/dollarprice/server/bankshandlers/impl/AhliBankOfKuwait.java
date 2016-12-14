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
public class AhliBankOfKuwait extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {

		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Al Ahli Bank of Kuwait");
		bankDetails.setName_ar(
				"\u0627\u0644\u0628\u0646\u0643 \u0627\ufef7\u0647\u0644\u064a \u0627\u0644\u0643\u0648\u064a\u062a\u064a - \u0628\u0646\u0643 \u0628\u064a\u0631\u064a\u0648\u0633");
		bankDetails.setSlug("ABK");
		bankDetails.setHomepage("http://www.piraeusbank.com.eg/");

		return bankDetails;
	}

	@Override
	protected void getCurrenciesDetails() {

		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.abkegypt.com/rates_abk.aspx")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Elements pricesRows = doc.select("div[id^=ctl00_ContentPlaceHolder1_Repeater1_]");

			for (Element priceRow : pricesRows) {

				Currency currency = new Currency();

				currency.setCurrencyCode(CurrencyCode
						.valueOf(priceRow.getElementsByClass("col-xs-12 col-sm-4").select("span").get(0).text()));
				currency.setCurrencyName(
						priceRow.getElementsByClass("col-xs-12 col-sm-4").select("span").get(0).text());
				currency.setBuyPrice(Double.parseDouble(
						priceRow.getElementsByClass("col-xs-6 col-sm-2").get(2).select("span").get(0).text()));
				currency.setSellPrice(Double.parseDouble(
						priceRow.getElementsByClass("col-xs-6 col-sm-2").get(3).select("span").get(0).text()));

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
