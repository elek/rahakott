package net.anzix.rahakott.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import net.anzix.rahakott.RahakottException;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;
import au.com.bytecode.opencsv.CSVReader;

public class ZKBParser implements FileParser {
	private SimpleDateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy");
	Stack<String[]> last = new Stack<String[]>();

	public void parse(Book b, File file, ParserContext p) {
		try {

			Account current = b.getOrCreateAccount("ZKB",
					p.getAccountConfig().currency);
			current.setManaged(true);
			current.setConfig(p.getAccountConfig());
			current.setCurrency(p.getAccountConfig().currency);
			CSVReader reader = new CSVReader(new InputStreamReader(
					new FileInputStream(file), "ISO-8859-1"), ',', '"');

			//skip first line
			reader.readNext();
			
			String[] nextLine;
			int i = 1;
			while ((nextLine = reader.readNext()) != null) {
				try {
					if (nextLine[0].trim().equals("")) {
						last.push(nextLine);
					} else {
						parseLines(current, b, last, p);
						last.push(nextLine);
					}
				} catch (Exception ex) {
					System.err.println("Error on line " + i);
					ex.printStackTrace();
				}
				i++;

			}
			parseLines(current, b, last, p);
			reader.close();
		} catch (Exception ex) {
			throw new RahakottException(ex);
		}
	}

	void parseLines(Account current, Book b, Stack<String[]> lines,
			ParserContext p) {
		if (lines.size() == 0) {
			return;
		} else if (lines.size() == 1) {

			Date date = getDate(lines.get(0)[0]);
			Account to = b.getOrCreateAccount("UNKNOWN",
					b.getDefaultCurrency());
			double amount = getAmount(lines.get(0)[6], lines.get(0)[5]);
			b.addTransaction(new Transaction(current, to, date,
					lines.get(0)[1], amount, p.getAccountConfig().currency));
		} else {
			double partial = 0;
			Date date = getDate(lines.get(0)[0]);
			for (int i = 1; i < lines.size(); i++) {
				String desc = getDesc(lines.get(0)[1], lines.get(i)[1]);
				Account to = b.getOrCreateAccount("UNKNOWN",
						b.getDefaultCurrency());
				double amount;
				if (lines.get(i)[2].trim().length() > 0) {
					amount = Double.valueOf(lines.get(i)[2].trim()) * -1;
					partial += amount;
				} else if (lines.size() > 2) {
					amount = getAmount(lines.get(i)[6], lines.get(i)[5]);
				} else {
					amount = getAmount(lines.get(0)[6], lines.get(0)[5]);
				}
				b.addTransaction(new Transaction(current, to, date, desc,
						amount, p.getAccountConfig().currency));
			}

			if (Math.abs(partial) > 0.01) {
				double amount = getAmount(lines.get(0)[6], lines.get(0)[5]);
				if (Math.abs(amount - partial) > 0.01) {
					b.addTransaction(new Transaction(current, b
							.getOrCreateAccount("BANK",
									p.getAccountConfig().currency), date,
							"ZKB fee", amount - partial,
							p.getAccountConfig().currency));
				}
			}
		}
		last.clear();

	}

	private String getDesc(String l0, String lc) {
		if (l0.startsWith("ONLINEBANK") || l0.startsWith("Lastschrift")
				|| l0.contains("Vergütung")) {
			return lc;
		}
		if (l0.length() > 8 && lc.length() > 8
				&& l0.substring(0, 8).equals(lc.substring(0, 8))) {
			return lc;
		}
		return l0 + " " + lc;
	}

	private Date getDate(String date) {
		try {
			return dateParser.parse(date);
		} catch (ParseException e) {
			throw new RahakottException(e);
		}
	}

	private double getAmount(String in, String out) {
		double res = 0;
		if (in != null && in.trim().length() > 0) {
			res += Double.valueOf(in);
		}
		if (out != null && out.trim().length() > 0) {
			res -= Double.valueOf(out);
		}

		return res;
	}
}
