package net.anzix.rahakott.parser;

import java.util.HashMap;
import java.util.Map;

import net.anzix.rahakott.model.config.AccountConfig;
import net.anzix.rahakott.model.config.AccountParsing;

public class ParserContext {

	private final AccountConfig accountConfig;
	private final AccountParsing parsing;

	public ParserContext(AccountConfig accountConfig, AccountParsing parsing) {
		this.accountConfig = accountConfig;
		this.parsing = parsing;
	}

	public AccountConfig getAccountConfig() {
		return accountConfig;
	}

	public AccountParsing getParsing() {
		return parsing;
	}

}
