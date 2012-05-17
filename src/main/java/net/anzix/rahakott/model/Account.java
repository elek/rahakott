package net.anzix.rahakott.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import net.anzix.rahakott.CurrencyConverter;
import net.anzix.rahakott.model.config.AccountConfig;

public class Account {
	private String currency;
	private String name;
	private String type;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	private boolean managed;
	private double openingBalance;
	private AccountConfig config = new AccountConfig();
	private double balance = 0;

	Account(String string, String type) {
		this.name = string;
		this.type = type;
	}

	public void add(Transaction transaction) {
		transactions.add(transaction);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void recalculate() {
		Collections.sort(transactions, new Comparator<Transaction>() {

			@Override
			public int compare(Transaction o1, Transaction o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});
		calculateBalance();
		if (config != null && config.endBalance != null) {
			if (Math.abs(config.endBalance - balance) > 0.01) {
				openingBalance = config.endBalance - balance;
				calculateBalance();
			}
		}

	}

	public double nextBalance(Transaction t, double current) {
		double res = current;
		if (config.twoSide && t.getTo().isManaged() && t.getFrom().isManaged()
				&& t.getTo().equals(this)) {
			return current;
		}
		double normalAmount = CurrencyConverter.INSTANCE.convert(t.getDate(),
				t.getCurrency(), getCurrency(), getAmount(t));
		res += normalAmount;
		return res;
	}

	void calculateBalance() {
		balance = openingBalance;
		for (Transaction t : transactions) {
			balance = nextBalance(t, balance);
		}
	}

	public double getBalance() {
		return balance;
	}
	
	public double getNormalBalance(Book info) {
		return CurrencyConverter.INSTANCE.convert(new Date(), currency, info.getConfig().defaultCurrency, balance);
	}

	public double getAmount(Transaction t) {
		if (t.getFrom().equals(this)) {
			return t.getAmount();
		} else {
			return t.getAmount() * -1;
		}
	}

	public Account getOtherSide(Transaction t) {
		if (t.getFrom().equals(this)) {
			return t.getTo();
		} else {
			return t.getFrom();
		}
	}

	public String getType() {
		return type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isManaged() {
		return managed;
	}

	public void setManaged(boolean managed) {
		this.managed = managed;
	}

	public AccountConfig getConfig() {
		return config;
	}

	public void setConfig(AccountConfig config) {
		this.config = config;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

}