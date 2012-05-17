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
	/**
	 * generic configuration.
	 */
	private BookConfiguration config;

	public Book(BookConfiguration config) {
		super();
		this.config = config;
	}

	public Account getAccount(String string) {
		return accounts.get(string);
	}

	public Account getOrCreateAccount(String name, String currency) {
		if (!accounts.containsKey(name)) {
			Account a = new Account(name, config.getType(name));
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

	public BookConfiguration getConfig() {
		return config;
	}
}
