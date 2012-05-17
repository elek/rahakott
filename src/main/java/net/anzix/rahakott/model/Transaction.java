package net.anzix.rahakott.model;

import java.util.Date;

import net.anzix.rahakott.CurrencyConverter;

public class Transaction {
	private Date date;
	private Account from;
	private Account to;
	private String description;
	double amount;
	private String currency;

	public Transaction(Account from, Account to, Date date, String description,
			double amount, String currency) {
		this.from = from;
		this.to = to;
		this.description = description;
		this.date = date;
		this.amount = amount;
		this.currency = currency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Account getFrom() {
		return from;
	}

	public void setFrom(Account from) {
		this.from = from;
	}

	public Account getTo() {
		return to;
	}

	public void setTo(Account to) {
		this.to = to;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String curreny) {
		this.currency = curreny;
	}

	public Account getOtherSide(Account a) {
		return a.getOtherSide(this);
	}

	public double getNormalAmount(Account a) {
		return CurrencyConverter.INSTANCE.convert(date,
				currency, a.getCurrency(), a.getAmount(this));
	}

}
