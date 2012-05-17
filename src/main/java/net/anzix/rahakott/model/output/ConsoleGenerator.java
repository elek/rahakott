package net.anzix.rahakott.model.output;

import java.io.File;

import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;

public class ConsoleGenerator {

	public void output(Book b, File file) {
		for (String accountName : b.getAccountNames()) {
			Account a = b.getAccount(accountName);
			System.out.println("*****" + a.getName() + "*****");
			double balance = 0;
			for (Transaction t : a.getTransactions()) {
				balance += a.getAmount(t);
				System.out.println(balance + ";" + t.getDescription() + ";"
						+ t.getAmount() + ";" + t.getTo().getName());

			}

		}

	}
}
