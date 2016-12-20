package com.telegram.bot.dollarprice.beans;

import java.util.Date;

import com.telegram.bot.dollarprice.server.beans.BankDetails;

public class BankPrice implements Comparable {

	private BankDetails bank;
	private Currency currency;
	private Date added;
	private double buy;
	private int buy_status;
	private double sell;
	private int sell_status;
	
	public BankDetails getBank() {
		return bank;
	}
	public void setBank(BankDetails bank) {
		this.bank = bank;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public Date getAdded() {
		return added;
	}
	public void setAdded(Date added) {
		this.added = added;
	}
	public double getBuy() {
		return buy;
	}
	public void setBuy(double buy) {
		this.buy = buy;
	}
	public int getBuy_status() {
		return buy_status;
	}
	public void setBuy_status(int buy_status) {
		this.buy_status = buy_status;
	}
	public double getSell() {
		return sell;
	}
	public void setSell(double sell) {
		this.sell = sell;
	}
	public int getSell_status() {
		return sell_status;
	}
	public void setSell_status(int sell_status) {
		this.sell_status = sell_status;
	}
	
	@Override
	public int compareTo(Object o) {
		
		BankPrice otherObject = (BankPrice)o;
		
		if (buy == otherObject.getBuy())
			return 0;
		
		return buy < otherObject.getBuy() ? 1 : -1;		
	}
	
}
