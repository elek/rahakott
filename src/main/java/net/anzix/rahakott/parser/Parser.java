package net.anzix.rahakott.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.anzix.rahakott.CurrencyConverter;
import net.anzix.rahakott.RahakottException;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.config.AccountConfig;
import net.anzix.rahakott.model.config.AccountParsing;
import net.anzix.rahakott.model.config.Configuration;

public class Parser {
	private Map<String, FileParser> parser = new HashMap<String, FileParser>();

	public Parser() {
		super();
		parser.put("csv", new CSVParser());
		parser.put("zkb", new ZKBParser());

	}

	public Book read(File root) {
		Configuration c = Configuration.read(new File(root, "rahakott.json"),
				Configuration.class);
		File fx = new File(root, "fx.json");
		if (fx.exists()) {
			CurrencyConverter.INSTANCE = Configuration.read(fx,
					CurrencyConverter.class);
		}
		Book b = new Book(c.book);

		for (File f : root.listFiles()) {
			if (f.isDirectory()) {
				File configFile = new File(root, f.getName() + "/"
						+ f.getName().toLowerCase() + ".json");
				if (configFile.exists()) {
					System.out.println("Loading data from dir "
							+ f.getAbsolutePath());

					AccountConfig accountConfig = Configuration.read(
							configFile, AccountConfig.class);
					Account a = b.getOrCreateAccount(f.getName(),
							accountConfig.currency);
					a.setConfig(accountConfig);
					a.setCurrency(accountConfig.currency);
					readDir(f, b, a);
				}
			}
		}

		return b;
	}

	private void readDir(File dir, Book b, Account a) {
		AccountConfig accountConfig = a.getConfig();

		for (File file : dir.listFiles()) {
			for (String pattern : accountConfig.parsing.keySet()) {
				if (file.getName().matches(pattern)) {
					FileParser p = getParser(accountConfig.parsing.get(pattern));
					p.parse(b, file, new ParserContext(accountConfig,
							accountConfig.parsing.get(pattern)));
				}
			}
		}

	}

	private FileParser getParser(AccountParsing accountParsing) {
		if (!parser.containsKey(accountParsing.parser)) {
			throw new RahakottException("No such parser "
					+ accountParsing.parser);
		}
		return parser.get(accountParsing.parser);
	}

}
