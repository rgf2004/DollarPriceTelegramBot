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

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.telegram.bot.core.config.TelegramConfig;
import com.telegram.bot.core.constants.TelegramConstants;
import com.telegram.bot.core.helper.SleepTimeGenerator;
import com.telegram.bot.dollarprice.beans.BankPrice;
import com.telegram.bot.dollarprice.beans.CurrencyDetails;
import com.telegram.bot.dollarprice.constants.DollarPriceConstants;
import com.telegram.bot.dollarprice.server.bankshandlers.BanksManager;
import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import gui.ava.html.image.generator.HtmlImageGenerator;

@Service
@DependsOn({"sleepTimeGenerator","banksManager"})
public class DollarPriceApi implements Runnable {

	final static Logger logger = LoggerFactory.getLogger(DollarPriceApi.class);

	@Autowired
	TelegramConfig telegramConfig;
	
	@Autowired
	BanksManager banksManager;
	
	@Autowired
	SleepTimeGenerator sleepTimeGenerator;

	private Properties config = new Properties();

	private InputStream input = null;

	private File usdCurrentCurrencyDetailsImage = null;

	private File euroCurrentCurrencyDetailsImage = null;
	
	private boolean isInitialized = false;
	
	public DollarPriceApi() {
		input = this.getClass().getClassLoader().getResourceAsStream(DollarPriceConstants.propertiesFileName);

		try {
			config.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}							
	}
	
	@PostConstruct
	public void init()
	{
		refreshCurrencyDetails();
		isInitialized = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	private List<BankPrice> getCurrentBankPrices(CurrencyCode currencyCode) {

		return banksManager.getCurrencyBanksPrice(currencyCode);
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
		List<BankPrice> banksPrices;
		File tempFile;

		tempCurrencyDetails = new CurrencyDetails();

		banksPrices = getCurrentBankPrices(CurrencyCode.USD);

		if (banksPrices == null)
			return;

		tempCurrencyDetails.setBankPrices(banksPrices);
		tempCurrencyDetails.setBestSellBank(getBestSellBankPrice(banksPrices));
		tempCurrencyDetails.setBestBuyBank(getBestBuyBankPrice(banksPrices));
		tempCurrencyDetails.setLastUpdate(new Date());

		// usdCurrencyDetails = tempCurrencyDetails;

		tempFile = saveCurrencyQueryResultAsImage(tempCurrencyDetails,
				config.getProperty(DollarPriceConstants.HTML_DOLLAR_PRICE_LABEL), usdCurrentCurrencyDetailsImage);

		usdCurrentCurrencyDetailsImage = tempFile;

		tempCurrencyDetails = new CurrencyDetails();

		banksPrices = getCurrentBankPrices(CurrencyCode.EUR);

		if (banksPrices == null)
			return;

		tempCurrencyDetails.setBankPrices(banksPrices);
		tempCurrencyDetails.setBestSellBank(getBestSellBankPrice(banksPrices));
		tempCurrencyDetails.setBestBuyBank(getBestBuyBankPrice(banksPrices));
		tempCurrencyDetails.setLastUpdate(new Date());

		tempFile = saveCurrencyQueryResultAsImage(tempCurrencyDetails,
				config.getProperty(DollarPriceConstants.HTML_EURO_PRICE_LABEL), euroCurrentCurrencyDetailsImage);

		euroCurrentCurrencyDetailsImage = tempFile;

	}

	private File saveCurrencyQueryResultAsImage(CurrencyDetails currencyDetails, String priceLabel,
			File currentImageFile) {

		HtmlImageGenerator htmlImageGenerator = new HtmlImageGenerator();

		String html = getCurrencyQueryResultAsHtml(currencyDetails, priceLabel);

		File tempFile = new File(telegramConfig.getProperty(TelegramConstants.TEMP_FOLDER_PATH) + "/"
				+ String.valueOf(new Date().getTime()) + ".png");

		htmlImageGenerator.loadHtml(html);

		htmlImageGenerator.saveAsImage(tempFile);

		synchronized (this) {
			if (currentImageFile != null) {
				currentImageFile.delete();
			}
		}

		return tempFile;
	}

	private String getCurrencyQueryResultAsHtml(CurrencyDetails currencyDetails, String priceLabel) {

		Configuration cfg = new Configuration();

		try {
			cfg.setDirectoryForTemplateLoading(new File(telegramConfig.getProperty(TelegramConstants.TEMPLATE_PATH)));
			Template template = cfg.getTemplate(DollarPriceConstants.TEMPLATE_NAME);
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("price_label", priceLabel);
			data.put("latest_update_date_label", config.getProperty(DollarPriceConstants.HTML_LATEST_UPDATE_DATE));

			SimpleDateFormat sdf = new SimpleDateFormat(config.getProperty(DollarPriceConstants.DATE_FORMAT_KEY));
			data.put("latest_update_date", sdf.format(currencyDetails.getLastUpdate()));

			data.put(DollarPriceConstants.BANK_KEYWORD_LABEL,
					config.getProperty(DollarPriceConstants.BANK_KEYWORD_LABEL));
			data.put(DollarPriceConstants.BUY_KEYWORD_LABEL,
					config.getProperty(DollarPriceConstants.BUY_KEYWORD_LABEL));
			data.put(DollarPriceConstants.SELL_KEYWORD_LABEL,
					config.getProperty(DollarPriceConstants.SELL_KEYWORD_LABEL));

			data.put(DollarPriceConstants.BEST_SELL_STRING,
					String.format(config.getProperty(DollarPriceConstants.BEST_SELL_STRING),
							currencyDetails.getBestSellBank().getBuy(),
							currencyDetails.getBestSellBank().getBank().getName_ar()));
			data.put(DollarPriceConstants.BEST_BUY_STRING,
					String.format(config.getProperty(DollarPriceConstants.BEST_BUY_STRING),
							currencyDetails.getBestBuyBank().getSell(),
							currencyDetails.getBestBuyBank().getBank().getName_ar()));

			data.put("banks", currencyDetails.getBankPrices());

			StringWriter strWriter = new StringWriter();
			template.process(data, strWriter);

			//System.out.println(strWriter.toString());

			return strWriter.toString();

		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public File getUSDCurrentCurrencyDetailsImage(String userQueryKey) {
		File image = null;
		if (config.getProperty(DollarPriceConstants.GET_CURRENT_PRICE_EURO_KEY).equalsIgnoreCase(userQueryKey)) {
			if (euroCurrentCurrencyDetailsImage == null) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			image = euroCurrentCurrencyDetailsImage;

		} else {
			if (usdCurrentCurrencyDetailsImage == null) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			image = usdCurrentCurrencyDetailsImage;
		}

		return image;
	}

	public List<String> getKeyboards() {
		List<String> keyboards = new ArrayList<>();

		keyboards.add(config.getProperty(DollarPriceConstants.GET_CURRENT_PRICE_USD_KEY));
		keyboards.add(config.getProperty(DollarPriceConstants.GET_CURRENT_PRICE_EURO_KEY));

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
			try 
			{										
				Thread.sleep(60000);				
				refreshCurrencyDetails();
							
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public boolean isInitialized() {
		return isInitialized;
	}
}