package net.anzix.rahakott.model.config;

import java.util.HashMap;
import java.util.Map;

public class BookConfiguration {
	public String defaultCurrency;
	public Map<String, String> accountTypes = new HashMap<String, String>();
	public String defaultType = "expense";

	public String getType(String name) {
		if (accountTypes.containsKey(name)) {
			return accountTypes.get(name);
		}
		return defaultType;
	}
}
