package com.telegram.bot.dollarprice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.telegram.bot.dollarprice.beans.CurrencyDetails;

@Configuration
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DollarPriceApiTest {

	@Autowired
	private DollarPriceApi dollarPriceApi;
	
	@Test
	public void testGetCurrentBankPrices()
	{
		//CurrencyDetails currencyDetails = dollarPriceApi.getCurrencyDetails();
		//assert(currencyDetails != null);
	}
}
