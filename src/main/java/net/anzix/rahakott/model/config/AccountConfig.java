package net.anzix.rahakott.model.config;

import java.util.HashMap;
import java.util.Map;

public class AccountConfig {
	public String currency;
	public String type;
	public Map<String, AccountParsing> parsing = new HashMap<String,AccountParsing>();
	public Double endBalance;
	public boolean twoSide = true;
}
