package net.anzix.rahakott.model.output;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.anzix.rahakott.RahakottException;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.Transaction;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TransferGenerator {

	public void generate(File root, Book b) {
		boolean first = false;
		Set<AccountPair> pairs = new HashSet<AccountPair>();
		for (Account a1 : b.getAccounts()) {
			for (Account a2 : b.getAccounts()) {
				if (a1.isManaged() && a2.isManaged() && !a1.equals(a2)) {
					if (a1.getName().compareTo(a2.getName()) > 0) {
						pairs.add(new AccountPair(a1, a2));
					} else {
						pairs.add(new AccountPair(a2, a1));
					}
				}
			}
		}

		for (AccountPair as : pairs) {
			List<Transaction> transactions = new ArrayList<Transaction>();
			Account transferAccount = new Account(as.a.getName() + "-"
					+ as.b.getName(), "transfer");
			transferAccount.setCurrency(b.getDefaultCurrency());
			for (Transaction t : as.a.getTransactions()) {
				if ((t.getFrom().equals(as.a) && t.getTo().equals(as.b))) {
					transactions.add(new Transaction(t.getFrom(),
							transferAccount, t.getDate(), t.getDescription(), t
									.getAmount(), t.getCurrency(),
							new HashMap<>(t.getCustomValues())));
				} else if (t.getFrom().equals(as.b) && t.getTo().equals(as.a)) {

				}
			}
			for (Transaction t : as.b.getTransactions()) {
				if ((t.getFrom().equals(as.a) && t.getTo().equals(as.b))) {

				} else if (t.getFrom().equals(as.b) && t.getTo().equals(as.a)) {
					transactions.add(new Transaction(t.getFrom(),
							transferAccount, t.getDate(), t.getDescription(), t
									.getAmount(), t.getCurrency(),
							new HashMap<>(t.getCustomValues())));
				}
			}
			Collections.sort(transactions, new Comparator<Transaction>() {

				@Override
				public int compare(Transaction o1, Transaction o2) {
					return o1.getDate().compareTo(o2.getDate());
				}
			});
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("transactions", transactions);
			values.put("root", transferAccount);
			values.put("pairs", pairs);
			if (!first) {
				generate(new File(root, "transfers.html"), "transfer.tpl",
						values);
			}
			generate(new File(root, as.a.getName() + "-" + as.b.getName()
					+ ".html"), "transfer.tpl", values);
		}
	}

	public void generate(File output, String templateName,
			Map<String, Object> values) {
		try {

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			Configuration c = new Configuration();
			c.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));

			FileWriter writer = new FileWriter(output);
			Template temp = c.getTemplate(templateName);

			temp.process(values, writer);

			writer.close();
		} catch (Exception ex) {
			throw new RahakottException(ex);
		}

	}

	public class AccountPair {
		public Account a;
		public Account b;

		public AccountPair(Account a, Account b) {
			super();
			this.a = a;
			this.b = b;
		}

		public Account getA() {
			return a;
		}

		public void setA(Account a) {
			this.a = a;
		}

		public Account getB() {
			return b;
		}

		public void setB(Account b) {
			this.b = b;
		}

		@Override
		public String toString() {
			return "[a=" + a + ", b=" + b + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((b == null) ? 0 : b.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AccountPair other = (AccountPair) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (a == null) {
				if (other.a != null)
					return false;
			} else if (!a.equals(other.a))
				return false;
			if (b == null) {
				if (other.b != null)
					return false;
			} else if (!b.equals(other.b))
				return false;
			return true;
		}

		private TransferGenerator getOuterType() {
			return TransferGenerator.this;
		}

		
		

	}
}
