package net.anzix.rahakott.parser;

import java.util.HashMap;
import java.util.Map;

import net.anzix.rahakott.model.config.AccountConfig;
import net.anzix.rahakott.model.config.AccountParsing;

public class ParserContext {
	public static final String UNKNOWN = "UNKNOWN";
	private final AccountConfig accountConfig;
	private final AccountParsing parsing;

	public ParserContext(AccountConfig accountConfig, AccountParsing parsing) {
		this.accountConfig = accountConfig;
		this.parsing = parsing;
	}

	public String guessAccount(String description) {
		for (String pattern : parsing.classification.keySet()) {
			if (description.matches(".*" + pattern + ".*")) {
				return parsing.classification.get(pattern);
			}
		}
		return UNKNOWN;
	}

	public AccountConfig getAccountConfig() {
		return accountConfig;
	}

	public AccountParsing getParsing() {
		return parsing;
	}
	

}
