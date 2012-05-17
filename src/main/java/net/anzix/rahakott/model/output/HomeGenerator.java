package net.anzix.rahakott.model.output;

import java.io.PrintWriter;
import java.util.Date;

import net.anzix.rahakott.CurrencyConverter;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;

public class HomeGenerator extends PageGenerator<Book> {
//
//	@Override
//	public Book generateImpl(Book input) throws Exception {
//
//		// Create the data table.
//		diagram += "var data = new google.visualization.DataTable();\n";
//		diagram += "data.addColumn('string', 'Account');\n";
//		diagram += "data.addColumn('number', 'balance');\n";
//		diagram += "data.addRows([\n";
//
//		for (String type : input.getAccountTypes()) {
//			writer.write("<div class=\"row\">");
//			writer.write("<div class=\"span4\">");
//			writer.write("<h1>" + type + "</h1>");
//			writer.write("<table class=\"table-striped span4\">");
//			for (Account a : input.getAccountsPerType(type)) {
//
//				writer.write("<tr>");
//				writer.write(String.format(
//						"<td><a href=\"%1$s.html\">%1$s</a></td>", a.getName()));
//				double normalBalance = CurrencyConverter.INSTANCE.convert(
//						new Date(), a.getCurrency(), "CHF", a.getBalance());
//				writer.write(String.format("<td>%1$.2f %2$s</td>",
//						normalBalance, "CHF"));
//				writer.write("</tr>");
//				if (a.getType().equals("expense")) {
//					diagram += "['"
//							+ a.getName()
//							+ "', "
//							+ (normalBalance > 0 ? normalBalance
//									: normalBalance * -1) + "],\n";
//				}
//
//			}
//			writer.write("</table>");
//			writer.write("</div>");
//			writer.write("<div class=\"span8\">");
//			writer.write("<div id=\"chart_" + type + "\" ></div>");
//			writer.write("</div>");
//
//			writer.write("</div>");
//		}
//
//		diagram += "]);";
//
//		// Set chart options
//		diagram += "var options = {'title':'Expenses',\n";
//		diagram += "'width':600,\n";
//		diagram += "'height':300};\n";
//
//		// Instantiate and draw our chart, passing in some options.
//		diagram += "var chart = new google.visualization.PieChart(document.getElementById('chart_expense'));\n";
//		diagram += "chart.draw(data, options);\n";
//
//	}
}
