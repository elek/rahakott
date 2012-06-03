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

public class CustomFieldProcessor {
	private static final String FILE_NAME = "unknown.rahakott";
	private DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	private Map<Account, Map<String, Map<String, String>>> parameters = new HashMap<Account, Map<String, Map<String, String>>>();

	public void process(File root, Book book) {
		for (Account a : book.getAccounts()) {
			load(root, a);
		}

	}

	/**
	 * checks if a custom file has been loaded for a specific account, and loads
	 * it if not.
	 * 
	 * @param root
	 * @param account
	 */
	private void load(File root, Account account) {
		try {
			if (parameters.containsKey(account)) {
				return;
			}
			// key -> custom attribute -> value
			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			File f = new File(root, account.getName().toUpperCase() + "/"
					+ FILE_NAME);
			if (!f.exists()) {
				return;
			}
			CSVReader reader = new CSVReader(new FileReader(f));
			String[] line;
			String[] types = new String[] { "classificationOverride" };
			account.setCustomFields(types);
			while ((line = reader.readNext()) != null) {
				String key = line[1];
				Transaction t = account.getTransaction(key);
				if (t == null) {
					System.err.println("Can't find the transaction for key "
							+ line[1] + " " + f.getAbsolutePath());
					continue;
				}
				for (int i = 0; i < types.length; i++) {
					t.addCustomValue(types[i], line[i]);
				}
			}
			reader.close();
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
				CSVWriter writer = new CSVWriter(new FileWriter(new File(root,
						a.getName().toUpperCase() + "/" + FILE_NAME)));
				for (Transaction t : a.getTransactions()) {
					String[] customs = a.getCustomFields();
					String[] lineTo = new String[customs.length + 2];
					for (int i = 0; i < customs.length; i++) {
						String val = t.getCustomValue(customs[i]);
						if (val != null) {
							lineTo[i] = val;
						}
					}

					lineTo[lineTo.length - 2] = t.getKey();
					lineTo[lineTo.length - 1] = t.getOtherSide(a).getName()
							+ "-" + f.format(t.getDate()) + "-"
							+ t.getDescription() + "-" + t.getAmount();
					writer.writeNext(lineTo);

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
