package com.telegram.bot.dollarprice.beans;

import java.util.Date;
import java.util.List;

public class CurrencyDetails {

	private Date lastUpdate;
	private List<BankPrice> bankPrices;
	private BankPrice bestSellBank;
	private BankPrice bestBuyBank;

	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<BankPrice> getBankPrices() {
		return bankPrices;
	}
	
	public void setBankPrices(List<BankPrice> bankPrices) {
		this.bankPrices = bankPrices;
	}

	public BankPrice getBestSellBank() {
		return bestSellBank;
	}

	public void setBestSellBank(BankPrice bestSellBank) {
		this.bestSellBank = bestSellBank;
	}

	public BankPrice getBestBuyBank() {
		return bestBuyBank;
	}

	public void setBestBuyBank(BankPrice bestBuyBank) {
		this.bestBuyBank = bestBuyBank;
	}

}
