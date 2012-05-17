package net.anzix.rahakott.model.config;

import java.util.HashMap;
import java.util.Map;

import net.anzix.rahakott.parser.ParserContext;

public class AccountParsing {
	public String parser;
	public Map<String, String> parameters = new HashMap<String, String>();
	public Map<String, String> classification = new HashMap<String, String>();
	public double fixAmount = 1;

	public Character getChar(String string, Character def) {
		if (getParameters().containsKey(string)) {
			return getParameters().get(string).charAt(0);
		} else {
			return def;
		}
	}

	public char getChar(String string) {
		Character val = getChar(string, null);
		if (val == null) {
			throw new IllegalArgumentException("parameter " + string
					+ " is missing for the parser " + parser);
		}
		return val;
	}

	public Integer getInt(String string, Integer def) {
		if (getParameters().containsKey(string)) {
			return Integer.valueOf(getParameters().get(string));
		} else {
			return def;
		}
	}

	public int getInt(String string) {
		Integer val = getInt(string, null);
		if (val == null) {
			throw new IllegalArgumentException("parameter " + string
					+ " is missing for the parser " + parser);
		}
		return val;
	}

	public String getStr(String string, String def) {
		if (getParameters().containsKey(string)) {
			return getParameters().get(string);
		} else {
			return def;
		}
	}

	public String getStr(String string) {
		String val = getStr(string, null);
		if (val == null) {
			throw new IllegalArgumentException("parameter " + string
					+ " is missing for the parser " + parser);
		}
		return val;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
}
