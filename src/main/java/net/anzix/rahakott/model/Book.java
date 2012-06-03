package net.anzix.rahakott.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.anzix.rahakott.model.config.BookConfiguration;

/**
 * Parent container of all the accounts.
 * 
 * @author eszti
 * 
 */
public class Book {

	/**
	 * Accounts per name.
	 */
	private Map<String, Account> accounts = new HashMap<String, Account>();
	/**
	 * Account per type.
	 */
	private Map<String, List<String>> accountsPerType = new HashMap<String, List<String>>();

	private String defaultCurrency = "USD";

	private String defaultType = "expenses";

	private Map<String, String> accountTypes = new HashMap<String, String>();

	public String getType(String name) {
		if (accountTypes.containsKey(name)) {
			return accountTypes.get(name);
		}
		return defaultType;
	}

	public Book() {
		super();
	}

	public Account getAccount(String string) {
		return accounts.get(string);
	}

	public void moveTransaction(Transaction t, String original, String moved) {
		Account movedAcc = getOrCreateAccount(moved, defaultCurrency);
		Account originalAcc = getAccount(original);
		if (t.getFrom().equals(originalAcc)) {
			t.setFrom(movedAcc);
		} else if (t.getTo().equals(originalAcc)) {
			t.setTo(movedAcc);
		} else {
			throw new IllegalArgumentException("Move from an unknown side " + t.getFrom()+"/"+t.getTo()
					+ " " + original + " " + moved);
		}
		originalAcc.removeTransaction(t);
		movedAcc.add(t);
	}

	public Account getOrCreateAccount(String name, String currency) {
		if (!accounts.containsKey(name)) {
			Account a = new Account(name, getType(name));
			a.setCurrency(currency);
			accounts.put(name, a);
			if (accountsPerType.get(a.getType()) == null) {
				accountsPerType.put(a.getType(), new ArrayList<String>());
			}
			accountsPerType.get(a.getType()).add(a.getName());
		}
		return accounts.get(name);
	}

	public void addTransaction(Transaction transaction) {
		if (accounts.get(transaction.getFrom().getName()) == null) {
			throw new IllegalArgumentException("No source transaction: "
					+ transaction.getFrom().getName());
		}
		if (accounts.get(transaction.getTo().getName()) == null) {
			throw new IllegalArgumentException("No destinationtransaction: "
					+ transaction.getTo().getName());
		}
		accounts.get(transaction.getFrom().getName()).add(transaction);
		accounts.get(transaction.getTo().getName()).add(transaction);
	}

	public Collection<String> getAccountNames() {
		return accounts.keySet();
	}

	public void recalculate() {
		for (Account a : accounts.values()) {
			a.recalculate();
		}
	}

	public Collection<String> getAccountTypes() {
		return accountsPerType.keySet();
	}

	public List<AccountGroup> getGroups() {
		List<AccountGroup> res = new ArrayList<AccountGroup>();
		for (String type : accountsPerType.keySet()) {
			AccountGroup g = new AccountGroup(type);
			for (String name : accountsPerType.get(type)) {
				g.getAccounts().add(accounts.get(name));
			}
			res.add(g);

		}
		return res;
	}

	public Collection<Account> getAccountsPerType(String type) {
		List<Account> res = new ArrayList<Account>();
		for (String accountName : accountsPerType.get(type)) {
			res.add(accounts.get(accountName));
		}
		return res;
	}

	public Collection<Account> getAccounts() {
		return new ArrayList<Account>(accounts.values());
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}
	
}
