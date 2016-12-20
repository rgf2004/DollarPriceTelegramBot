package com.telegram.bot.dollarprice.server.bankshandlers.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
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
public class SocieteArabeInternationaleDeBanque extends AbstractBankHandler {

	@Override
	public BankDetails getBankDetails() {

		BankDetails bankDetails = new BankDetails();

		bankDetails.setName("Societe Arabe Internationale de Banque");
		bankDetails.setName_ar(
				"\u0628\u0646\u0643 \u0627\u0644\u0634\u0631\u0643\u0629 \u0627\u0644\u0645\u0635\u0631\u0641\u064a\u0629 \u0627\u0644\u0639\u0631\u0628\u064a\u0629 \u0627\u0644\u062f\u0648\u0644\u064a\u0629");
		bankDetails.setSlug("SAIB");
		bankDetails.setHomepage("http://www.saib.com.eg/");

		return bankDetails;

	}

	@Override
	public void getCurrenciesDetails() {

		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.saib.com.eg/foreign-currencies/")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.get();

			Element pricesTable = doc.getElementsByClass("cont").select("table").get(0);
			Elements pricesRows = pricesTable.select("tr");
			for (int i = 1; i < pricesRows.size(); i++) {
				Currency currency = new Currency();

				Elements rowColumns = pricesRows.get(i).select("td");
				
				if (rowColumns.get(0).text().contains("EUR"))
					currency.setCurrencyCode(CurrencyCode.EUR);
				else if (StringUtils.isEmpty(rowColumns.get(0).text()))
					continue;
				else
					currency.setCurrencyCode(CurrencyCode.valueOf(rowColumns.get(0).text()));

				
				currency.setCurrencyName(rowColumns.get(0).text());
				currency.setBuyPrice(Double.parseDouble(rowColumns.get(1).text()) / 100);
				currency.setSellPrice(Double.parseDouble(rowColumns.get(2).text()) / 100);

				currencies.put(currency.getCurrencyCode(), currency);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
