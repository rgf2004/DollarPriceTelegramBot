package com.telegram.bot.dollarprice;

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
}
