package net.anzix.rahakott;

import org.junit.Test;

import com.google.gson.GsonBuilder;

public class ConfigurationTest {

	@Test
	public void test() {

		// AccountParsing parsing = new AccountParsing();
		// parsing.parser="CSVParser";
		// parsing.parameters.put("dateIdx", "1");
		// parsing.classification.put("vlami", "BANK");
		// parsing.fixAmount = -1;
		//
		// AccountConfig config = new AccountConfig();
		// config.parsing.put(".*.csv",parsing);
		System.out.println(new GsonBuilder().setPrettyPrinting().create()
				.toJson(CurrencyConverter.INSTANCE));

	}
}
