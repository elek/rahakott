package net.anzix.rahakott;

import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;

public class ManualClassifier {
	public void parse(Book book) {
		for (Account a : book.getAccounts()) {
			for (Transaction t : a.getTransactions()) {
				if (Rahakott.UNKNOWN.equals(t.getTo().getName())) {
					if (t.getCustomValue("classificationOverride") != null
							&& t.getCustomValue("classificationOverride")
									.length() > 0)
						book.moveTransaction(t, Rahakott.UNKNOWN,
								t.getCustomValue("classificationOverride"));

				}
			}
		}

	}
}
