package net.anzix.rahakott.model.output;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.anzix.rahakott.CurrencyConverter;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;

public class SummaryGenerator extends PageGenerator<Book> {

	// year --> account type --> account --> month --> amount
	Map<String, Map<String, Map<String, Map<String, Double>>>> monthly = new HashMap<String, Map<String, Map<String, Map<String, Double>>>>();

	// year --> account type --> month --> amount
	Map<String, Map<String, Map<String, Double[]>>> summary = new HashMap<String, Map<String, Map<String, Double[]>>>();

	@Override
	protected Map<String, Object> populateVariables(Book input) {
		Map<String, Object> result = new TreeMap<String, Object>();
		List<String> accountNames = new ArrayList<String>();
		for (String name : input.getAccountNames()) {
			Account a = input.getAccount(name);
			accountNames.add(a.getName());
			Date prev = null;
			double balance = a.getOpeningBalance();
			double monch = 0;
			for (Transaction t : a.getTransactions()) {
				if (prev == null
						|| (prev.getMonth() == t.getDate().getMonth() && prev
								.getYear() == t.getDate().getYear())) {
				} else {
					int month = prev.getMonth();
					put(input, "" + (prev.getYear() + 1900), a.getType(),
							a.getName(), (month < 9 ? "0" : "") + (month + 1),
							CurrencyConverter.INSTANCE.convert(prev,
									a.getCurrency(),
									input.getDefaultCurrency(), balance),
							CurrencyConverter.INSTANCE.convert(prev,
									a.getCurrency(),
									input.getDefaultCurrency(), monch));
					monch = 0;
				}
				balance = a.nextBalance(t, balance);
				monch = a.nextBalance(t, monch);
				prev = t.getDate();

			}
			if (prev != null) {
				int month = prev.getMonth();
				put(input, "" + (prev.getYear() + 1900), a.getType(),
						a.getName(), (month < 9 ? "0" : "") + (month + 1),
						CurrencyConverter.INSTANCE.convert(prev,
								a.getCurrency(), input.getDefaultCurrency(),
								balance), CurrencyConverter.INSTANCE.convert(
								prev, a.getCurrency(),
								input.getDefaultCurrency(), monch));
			}
		}

		result.put("root", monthly);
		result.put("sum", summary);

		return result;
	}

	private void put(Book b, String year, String accountType, String account,
			String month, double amount, double montam) {
		Map<String, Map<String, Map<String, Double>>> inYear = monthly
				.get(year);
		if (inYear == null) {
			inYear = new TreeMap<String, Map<String, Map<String, Double>>>();
			for (String type : b.getAccountTypes()) {
				inYear.put(type, new HashMap<String, Map<String, Double>>());
				for (Account ac : b.getAccountsPerType(type)) {
					inYear.get(type).put(ac.getName(),
							new TreeMap<String, Double>());
					// for (int i = 1; i <= 12; i++) {
					// inYear.get(type).get(ac.getName()).put("" + i, new
					// Double(0));
					// }
				}

			}

			monthly.put(year, inYear);
		}

		// initialize summary
		Map<String, Map<String, Double[]>> sumYear = summary.get(year);
		if (sumYear == null) {
			sumYear = new TreeMap<String, Map<String, Double[]>>();
			for (String type : b.getAccountTypes()) {
				sumYear.put(type, new HashMap<String, Double[]>());
				for (int i = 1; i <= 12; i++) {
					sumYear.get(type).put((i < 10 ? "0" : "") + i,
							new Double[] { 0d, 0d });
				}

			}

			summary.put(year, sumYear);
		}

		monthly.get(year).get(accountType).get(account).put(month, montam);
		summary.get(year).get(accountType).get(month)[0] = summary.get(year)
				.get(accountType).get(month)[0]
				+ amount;
		summary.get(year).get(accountType).get(month)[1] = summary.get(year)
				.get(accountType).get(month)[1]
				+ montam;

	}
}
