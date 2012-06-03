package net.anzix.rahakott;

import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;

public class ManualClassifier {
	public void parse(Book book) {
		for (Account a : book.getAccounts()) {
			for (Transaction t : a.getTransactions()) {
				if (t.getCustomValue("classificationOverride") != null
						&& t.getCustomValue("classificationOverride").length() > 0) {
					String to = t.getCustomValue("classificationOverride");
					if (!to.equals(t.getTo().getName())) {
						book.moveTransaction(t, t.getTo().getName(),
								t.getCustomValue("classificationOverride"));
					}
				}

			}
		}

	}
}
