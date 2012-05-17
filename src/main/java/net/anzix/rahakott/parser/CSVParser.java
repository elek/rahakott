package net.anzix.rahakott.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import net.anzix.rahakott.RahakottException;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;
import net.anzix.rahakott.model.config.AccountParsing;
import au.com.bytecode.opencsv.CSVReader;

public class CSVParser implements FileParser {

	public void parse(Book b, File file, ParserContext p) {
		SimpleDateFormat dateParser = new SimpleDateFormat(p.getParsing()
				.getStr("datePattern", "yyyyMMdd"));
		try {
			Account current = b.getOrCreateAccount(file.getParentFile()
					.getName(), p.getAccountConfig().currency);
			current.setManaged(true);
			current.setCurrency(p.getAccountConfig().currency);
			current.setConfig(p.getAccountConfig());
			CSVReader reader = new CSVReader(new InputStreamReader(
					new FileInputStream(file), Charset.forName(p.getParsing()
							.getStr("charset", "ISO-8859-2"))), p.getParsing()
					.getChar("separator", ';'), '"');

			String[] nextLine;
			for (int i = 0; i < p.getParsing().getInt("skipLine", 0); i++) {
				reader.readNext();
			}
			while ((nextLine = reader.readNext()) != null) {
				try {
					Transaction t = parseLine(current, b, nextLine, p,
							dateParser);
					if (t != null) {
						b.addTransaction(t);
					}
				} catch (Exception ex) {
					System.err.println("Error on parsing line"
							+ Arrays.asList(nextLine));
					ex.printStackTrace();
				}
			}
			reader.close();
		} catch (Exception ex) {
			throw new RahakottException(ex);
		}
	}

	Transaction parseLine(Account current, Book b, String[] nextLine,
			ParserContext p, DateFormat df) {

		AccountParsing conf = p.getParsing();
		String description = getDescription(nextLine, p);
		String accName = p.guessAccount(description);

		Account c = b
				.getOrCreateAccount(accName, p.getAccountConfig().currency);
		double amount = getAmount(nextLine[conf.getInt("depositIdx")],
				nextLine[conf.getInt("withdrawalIdx")]) * p.getParsing().fixAmount;
		return new Transaction(current, c, getDate(df,
				nextLine[conf.getInt("dateIdx")]), description, amount,
				p.getAccountConfig().currency);
	}

	private String getDescription(String[] nextLine, ParserContext p) {
		String[] v = p.getParsing().getStr("descriptionIdx").split(",");
		StringBuilder b = new StringBuilder();
		for (String idx : v) {
			b.append(nextLine[Integer.valueOf(idx)] + " ");

		}
		return b.toString().trim();
	}

	private Date getDate(DateFormat dateParser, String date) {
		try {
			return dateParser.parse(date);
		} catch (ParseException e) {
			throw new RahakottException(e);
		}
	}

	private double getAmount(String in, String out) {
		double res = 0;
		if (in.equals(out)) {
			res += Double.valueOf(in);
		} else {
			if (in != null && in.trim().length() > 0) {
				res += Double.valueOf(in);
			}
			if (out != null && out.trim().length() > 0) {
				res -= Double.valueOf(out);
			}
		}

		return res;
	}

}
