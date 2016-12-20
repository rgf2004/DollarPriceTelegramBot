package com.telegram.bot.dollarprice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.telegram.bot.dollarprice.beans.BankPrice;
import com.telegram.bot.dollarprice.server.bankshandlers.BanksManager;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

@Configuration
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BankParserTest {

	@Autowired
	BanksManager banksManager;

	@Test
	public void testBankParser() {

		for (BankPrice bankPrice : banksManager.getCurrencyBanksPrice(CurrencyCode.USD)) {
			System.out.print(bankPrice);
		}
	}

	// @Test
	// public void test()
	// {
	// try {
	// Document doc =
	// Jsoup.connect("http://www.cibeg.com/_layouts/15/LINKDev.CIB.CurrenciesFunds/FundsCurrencies.aspx/GetCurrencies")
	// .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36
	// (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
	// .header("Accept", "application/json")
	// .header("Accept-Encoding", "gzip,deflate,sdch")
	// .header("Accept-Language", "es-ES,es;q=0.8")
	// .header("Connection", "keep-alive")
	// .header("X-Requested-With", "XMLHttpRequest")
	// .header("Content-Type", "application/json; charset=utf-8")
	// .data("{'lang':'en'}", "")
	// .post();
	//
	// System.out.println(doc.toString());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

//	@Test
//	public void test_2() {
//		try {
//			Document doc = Jsoup.connect("http://www.edbebank.com/EN/BankingServices/TreasuryFiles/exchangerates.xml")
//					.userAgent(
//							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
//					.get();
//
//			System.out.println(doc);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
