package net.anzix.rahakott.parser;

import java.text.SimpleDateFormat;

import junit.framework.Assert;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;
import net.anzix.rahakott.model.config.AccountConfig;
import net.anzix.rahakott.model.config.AccountParsing;
import net.anzix.rahakott.model.config.BookConfiguration;

import org.junit.Test;

public class CSVParserTest {

	@Test
	public void test() {
		CSVParser parser = new CSVParser();
		Book b = new Book(new BookConfiguration());
		Account c = b.getOrCreateAccount("A","HUF");
		Transaction t = parser.parseLine(c, b,
				"2011-01-24,Jahresgeb Karte ,BANK,,20,-20,,".split(","),new ParserContext(new AccountConfig(), new AccountParsing()), new SimpleDateFormat());
		Assert.assertTrue(-20 - t.getAmount() < 00.1);
	}

}
