package net.anzix.rahakott;

import java.util.Map;

import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;

public class PatternClassifier {
	private Map<String, String> patterns;

	public void parse(Book book) {
		for (Account a : book.getAccounts()) {
			for (Transaction t : a.getTransactions()) {
				if (Rahakott.UNKNOWN.equals(t.getTo().getName())) {
					String newAccountName = detect(t.getDescription());
					if (newAccountName != null) {
						book.moveTransaction(t, Rahakott.UNKNOWN,
								newAccountName);
					}
				}
			}
		}

	}

	private String detect(String description) {
		for (String pattern : patterns.keySet()) {
			if (description.matches(".*" + pattern + ".*")) {
				return patterns.get(pattern);
			}
		}
		return null;
	}
}
