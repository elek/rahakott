package net.anzix.rahakott.model;

import java.util.ArrayList;
import java.util.List;

public class AccountGroup {
	private String type;
	private List<Account> accounts = new ArrayList<Account>();

	public AccountGroup(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
