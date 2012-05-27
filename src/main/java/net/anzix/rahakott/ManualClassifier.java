package net.anzix.rahakott;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class ManualClassifier {
	private static final String FILE_NAME = "unknown.rahakott";
	private DateFormat f = new SimpleDateFormat("yyyy-MM-dd");

	public void process(File root, Book book) {
		for (Account a : book.getAccounts()) {
			if (!a.isManaged()) {
				continue;
			}
			Map<String, String> associations = getPattern(root, a.getName());
			for (Transaction t : a.getTransactions()) {
				if (Rahakott.UNKNOWN.equals(t.getTo().getName())) {
					String newAccountName = detect(t.getKey(), associations);
					if (newAccountName != null) {
						book.moveTransaction(t, Rahakott.UNKNOWN,
								newAccountName);
					}
				}
			}
		}

	}

	private Map<String, String> getPattern(File root, String account) {
		try {
			Map<String, String> result = new HashMap<String, String>();
			File f = new File(root, account.toUpperCase() + "/" + FILE_NAME);
			if (!f.exists()) {
				return result;
			}
			CSVReader reader = new CSVReader(new FileReader(f));
			String[] line;
			while ((line = reader.readNext()) != null) {
				if (line[0].trim().length() > 0) {
					result.put(line[1], line[0]);
				}
			}
			reader.close();
			return result;
		} catch (Exception ex) {
			throw new RuntimeException("Error on reading file", ex);
		}

	}

	private String detect(String key, Map<String, String> assoc) {
		return assoc.get(key);
	}

	public void save(File root, Book book) {
		try {
			for (Account a : book.getAccounts()) {
				if (!a.isManaged()) {
					continue;
				}
				Map<String, String> oldAssoc = getPattern(root, a.getName());
				CSVWriter writer = new CSVWriter(new FileWriter(new File(root,
						a.getName().toUpperCase() + "/" + FILE_NAME)));
				for (Transaction t : a.getTransactions()) {
					if (oldAssoc.containsKey(t.getKey())) {
						writer.writeNext(new String[] {
								oldAssoc.get(t.getKey()), t.getKey(),
								f.format(t.getDate()), t.getDescription(),
								"" + t.getAmount() });
					}

					else if (Rahakott.UNKNOWN.equals(t.getTo().getName())) {
						writer.writeNext(new String[] { "", t.getKey(),
								f.format(t.getDate()), t.getDescription(),
								"" + t.getAmount() });
					}
				}
				if (writer != null) {
					writer.close();
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
