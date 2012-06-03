package net.anzix.rahakott.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private String[] customFields = new String[0];
	private Map<String, Transaction> idx = new HashMap<String, Transaction>();

	public Account(String string, String type) {
		this.name = string;
		this.type = type;
	}

	public void add(Transaction transaction) {
		transactions.add(transaction);
		idx.put(transaction.getKey(), transaction);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Transaction> getTransactions() {
		return new ArrayList(transactions);
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void recalculate() {
		if (config.twoSide) {
			for (Transaction t : getTransactions()) {
				if (t.getTo().equals(this) && t.getFrom().isManaged()
						&& t.getTo().isManaged()) {
					removeTransaction(t);
				}
			}
		}
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
		return CurrencyConverter.INSTANCE.convert(new Date(), currency,
				info.getDefaultCurrency(), balance);
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

	public void removeTransaction(Transaction t) {
		transactions.remove(t);
		idx.remove(t.getKey());

	}

	public Transaction getTransaction(String key) {
		return idx.get(key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String[] getCustomFields() {
		return customFields;
	}

	public void setCustomFields(String[] customFields) {
		this.customFields = customFields;
	}

	@Override
	public String toString() {
		return "Account[name=" + name + "]";
	}

}
