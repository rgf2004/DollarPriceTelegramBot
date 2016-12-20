package com.telegram.bot.dollarprice.server.beans;

import com.telegram.bot.dollarprice.server.enums.CurrencyCode;

public class Currency {

	private String currencyName;
	private CurrencyCode currencyCode;
	private double sellPrice;
	private double buyPrice;
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public CurrencyCode getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(CurrencyCode currencyCode) {
		this.currencyCode = currencyCode;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	@Override
	public String toString() {
		return "Currency [currencyName=" + currencyName + ", currencyCode=" + currencyCode + ", sellPrice=" + sellPrice
				+ ", buyPrice=" + buyPrice + "]";
	}
}
