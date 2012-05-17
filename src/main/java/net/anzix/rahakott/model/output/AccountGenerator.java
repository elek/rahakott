package net.anzix.rahakott.model.output;

import java.io.PrintWriter;
import java.util.Scanner;

import net.anzix.rahakott.CurrencyConverter;
import net.anzix.rahakott.RahakottException;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Transaction;

public class AccountGenerator extends PageGenerator<Account> {

//	@Override
//	public void generateImpl(PrintWriter writer, Account a) {
//		try {
//			header = new Scanner(getClass().getResourceAsStream("/header-account.html")).useDelimiter("\\Z").next();
//			writer.write("<h1>"+a.getName()+"</h1>");
//			writer.write("<ul>");
//			writer.write("<li>Currency: " + a.getCurrency() + "</li>");
//			writer.write("<li>Type: " + a.getType() + "</li>");
//			writer.write("<li>Managed: " + a.isManaged() + "</li>");
//			writer.write("</ul>");
//			writer.write("<table id=\"table\" class=\"table-striped span12 table-bordered\">");
//			writer.write("<thead><tr><th>Date</th><th>Description</th><th>Amount</th><th></th><th>Converted</th><th></th><th>Balance</th><th></th></tr></thead>");
//			double balance = a.getOpeningBalance();
//			for (Transaction t : a.getTransactions()) {
//				double normalAmount = CurrencyConverter.INSTANCE.convert(
//						t.getDate(), t.getCurrency(), a.getCurrency(),
//						a.getAmount(t));
//				balance = a.nextBalance(t, balance);
//				writer.write("<tr>");
//				writer.write(String.format("<td>%1$ty-%1$tm-%1$td</td>",
//						t.getDate()));
//				writer.write(String.format(
//						"<td>%1$s</td>",
//						t.getDescription().substring(0,
//								Math.min(t.getDescription().length(), 60))));
//				writer.write(String.format("<td>%1$.2f</td>", a.getAmount(t)));
//				writer.write(String.format("<td>%1$s</td>", t.getCurrency()));
//				writer.write(String.format("<td>%1$.2f</td>", normalAmount));
//				writer.write(String.format("<td>%1$s</td>", a.getCurrency()));
//				writer.write(String.format("<td>%1$.2f</td>", balance));
//				Account other = a.getOtherSide(t);
//				writer.write(String
//						.format("<td><a href=\"%1$s.html\"><span class=\"label label-info\">%1$s</span></a></td>",
//								other.getName()));
//				writer.write("</tr>");
//
//			}
//			writer.write("</table>");
//		} catch (Exception ex) {
//			throw new RahakottException(ex);
//		}
//
//	}
}
