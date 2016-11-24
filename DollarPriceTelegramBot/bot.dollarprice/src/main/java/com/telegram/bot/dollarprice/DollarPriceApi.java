package com.telegram.bot.dollarprice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.bot.core.httpclient.HttpClient;
import com.telegram.bot.core.httpclient.beans.HttpGetRequest;
import com.telegram.bot.dollarprice.beans.BankPrice;
import com.telegram.bot.dollarprice.beans.CurrencyDetails;
import com.telegram.bot.dollarprice.constants.DollarPriceConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import gui.ava.html.image.generator.HtmlImageGenerator;

@Service
public class DollarPriceApi implements Runnable {

	final static Logger logger = LoggerFactory.getLogger(DollarPriceApi.class);

	private Properties config = new Properties();

	InputStream input = null;

	private HttpClient httpClient = new HttpClient();

	private final String USD_URL = "https://eldollarbkam.com/api/prices/usd/";
	private CurrencyDetails usdCurrencyDetails = null;
	private File usdCurrentCurrencyDetailsImage = null;
	
	private final String EURO_URL = "https://eldollarbkam.com/api/prices/eur/";
	private CurrencyDetails euroCurrencyDetail = null;
	private File euroCurrentCurrencyDetailsImage = null;
	
	public DollarPriceApi() {
		input = this.getClass().getClassLoader().getResourceAsStream(DollarPriceConstants.propertiesFileName);

		try {
			config.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread thread = new Thread(this);
		thread.start();
	}

	private List<BankPrice> getCurrentBankPrices(String URL) {

		ObjectMapper mapper = new ObjectMapper();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		mapper.setDateFormat(dateFormat);

		HttpGetRequest request = new HttpGetRequest();
		request.setURL(URL);
		try {
			String responseBody = httpClient.GetRequestJson(request);
			List<BankPrice> bankPrices = mapper.readValue(responseBody, new TypeReference<List<BankPrice>>() {
			});
			return bankPrices;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}						
	}

	private BankPrice getBestSellBankPrice(List<BankPrice> banksPrices) {
		BankPrice bestSellBankPrice = banksPrices.get(0);

		for (BankPrice tempBankPrice : banksPrices) {
			if (tempBankPrice.getBuy() > bestSellBankPrice.getBuy())
				bestSellBankPrice = tempBankPrice;
		}

		return bestSellBankPrice;
	}

	private BankPrice getBestBuyBankPrice(List<BankPrice> banksPrices) {
		BankPrice bestBuyBankPrice = banksPrices.get(0);

		for (BankPrice tempBankPrice : banksPrices) {
			if (tempBankPrice.getSell() < bestBuyBankPrice.getSell())
				bestBuyBankPrice = tempBankPrice;
		}

		return bestBuyBankPrice;
	}

	private void refreshCurrencyDetails() {

		CurrencyDetails tempCurrencyDetails;
		List<BankPrice> banksPrices ;
		
		tempCurrencyDetails = new CurrencyDetails();

		
		banksPrices = getCurrentBankPrices(USD_URL);

		if (banksPrices == null)
			return;

		tempCurrencyDetails.setBankPrices(banksPrices);
		tempCurrencyDetails.setBestSellBank(getBestSellBankPrice(banksPrices));
		tempCurrencyDetails.setBestBuyBank(getBestBuyBankPrice(banksPrices));
		tempCurrencyDetails.setLastUpdate(new Date());

		usdCurrencyDetails = tempCurrencyDetails;

		saveCurrencyQueryResultAsImage();
		
		
		tempCurrencyDetails = new CurrencyDetails();
		
		banksPrices = getCurrentBankPrices(EURO_URL);

		if (banksPrices == null)
			return;

		tempCurrencyDetails.setBankPrices(banksPrices);
		tempCurrencyDetails.setBestSellBank(getBestSellBankPrice(banksPrices));
		tempCurrencyDetails.setBestBuyBank(getBestBuyBankPrice(banksPrices));
		tempCurrencyDetails.setLastUpdate(new Date());

		euroCurrencyDetail = tempCurrencyDetails;

	}

	private String getCurrencyQueryResultAsHtml() {

		Configuration cfg = new Configuration();

		try {
			cfg.setDirectoryForTemplateLoading(
					new File("C:/Users/feteha/Desktop/Temp/20161119/DollarPriceTelegramBot/bot.dollarprice/templates"));
			Template template = cfg.getTemplate("currencyDetails.ftl");
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("dollar_price_label", config.getProperty(DollarPriceConstants.HTML_DOLLAR_PRICE_LABEL));
			data.put("latest_update_date_label", config.getProperty(DollarPriceConstants.HTML_LATEST_UPDATE_DATE));

			SimpleDateFormat sdf = new SimpleDateFormat(config.getProperty(DollarPriceConstants.DATE_FORMAT_KEY));
			data.put("latest_update_date", sdf.format(usdCurrencyDetails.getLastUpdate()));

			data.put(DollarPriceConstants.BANK_KEYWORD_LABEL,
					config.getProperty(DollarPriceConstants.BANK_KEYWORD_LABEL));
			data.put(DollarPriceConstants.BUY_KEYWORD_LABEL,
					config.getProperty(DollarPriceConstants.BUY_KEYWORD_LABEL));
			data.put(DollarPriceConstants.SELL_KEYWORD_LABEL,
					config.getProperty(DollarPriceConstants.SELL_KEYWORD_LABEL));

			data.put(DollarPriceConstants.BEST_SELL_STRING,
					String.format(config.getProperty(DollarPriceConstants.BEST_SELL_STRING),
							usdCurrencyDetails.getBestSellBank().getBuy(),
							usdCurrencyDetails.getBestSellBank().getBank().getName_ar()));
			data.put(DollarPriceConstants.BEST_BUY_STRING,
					String.format(config.getProperty(DollarPriceConstants.BEST_BUY_STRING),
							usdCurrencyDetails.getBestBuyBank().getSell(),
							usdCurrencyDetails.getBestBuyBank().getBank().getName_ar()));

			
			data.put("banks", usdCurrencyDetails.getBankPrices());
			
			/*
			 * Writer out = new OutputStreamWriter(System.out);
			 * template.process(data, out); out.flush();
			 */

			StringWriter strWriter = new StringWriter();
			template.process(data, strWriter);

			return strWriter.toString();

		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	private void saveCurrencyQueryResultAsImage() {

		HtmlImageGenerator htmlImageGenerator = new HtmlImageGenerator();

		String html = getCurrencyQueryResultAsHtml();

		// logger.debug(html);

		File tempFile = new File("c:\\png\\" + String.valueOf(new Date().getTime()) + ".png");

		htmlImageGenerator.loadHtml(html);

		htmlImageGenerator.saveAsImage(tempFile);

		synchronized (this) {
			if (usdCurrentCurrencyDetailsImage != null) {
				usdCurrentCurrencyDetailsImage.delete();
			}
			usdCurrentCurrencyDetailsImage = tempFile;
		}
	}

	public File getUSDCurrentCurrencyDetailsImage() {
		return usdCurrentCurrencyDetailsImage;
	}

	public List<String> getKeyboards() {
		List<String> keyboards = new ArrayList<>();

		keyboards.add(config.getProperty(DollarPriceConstants.GET_CURRENT_PRICE_KEY));
		// keyboards.add(config.getProperty(DollarPriceConstants.GET_HOURLY_PRICE_KEY));

		return keyboards;
	}

	public String getImageCaption() {
		return config.getProperty(DollarPriceConstants.IMAGE_CAPTION_KEY);
	}

	public Properties getConfig() {
		return config;
	}

	@Override
	public void run() {
		while (true) {
			refreshCurrencyDetails();
			try {
				Thread.sleep(DollarPriceConstants.REFRESH_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}