package net.anzix.rahakott.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.anzix.rahakott.CurrencyConverter;

public class Transaction {
	private Date date;
	private Account from;
	private Account to;
	private String description;
	double amount;
	private String currency;
	private Map<String, String> customFields = new HashMap<String, String>();
	
	MessageDigest md;
	DateFormat df = new SimpleDateFormat("yyyyMMdd");


	
	public Transaction(Date date, Account from, Account to, String description,
			double amount, String currency, Map<String, String> customFields) {
		super();
		this.date = date;
		this.from = from;
		this.to = to;
		this.description = description;
		this.amount = amount;
		this.currency = currency;
		this.customFields = customFields;
	}

	public Transaction(Account from, Account to, Date date, String description,
			double amount, String currency) {
		this.from = from;
		this.to = to;
		this.description = description;
		this.date = date;
		this.amount = amount;
		this.currency = currency;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}


	public Date getDate() {
		return date;
	}

	public Account getFrom() {
		return from;
	}

	public Account getTo() {
		return to;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public Account getOtherSide(Account a) {
		return a.getOtherSide(this);
	}

	public double getNormalAmount(Account a) {
		return CurrencyConverter.INSTANCE.convert(date, currency,
				a.getCurrency(), a.getAmount(this));
	}

	public String getKey() {
		String key = df.format(date) + ";" + description + ";"
				+ "".format("%1$.2f", amount);
		BigInteger bigInt = new BigInteger(1, md.digest(key.getBytes()));
		String hashtext = bigInt.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	public void addCustomValue(String key, String value) {
		customFields.put(key, value);
	}

	public String getCustomValue(String key) {
		return customFields.get(key);
	}

	public void setFrom(Account from) {
		this.from = from;
	}

	public void setTo(Account to) {
		this.to = to;
	}
	
	
}
