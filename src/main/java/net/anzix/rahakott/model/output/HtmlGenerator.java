package net.anzix.rahakott.model.output;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import net.anzix.rahakott.RahakottException;
import net.anzix.rahakott.model.Account;
import net.anzix.rahakott.model.Book;

public class HtmlGenerator {
	public void output(Book b, File root) {
		new HomeGenerator().generate(new File(root, "index.html"), "index.tpl",
				b);
		new SummaryGenerator().generate(new File(root, "summary.html"),
				"summary.tpl", b);
		for (String accountName : b.getAccountNames()) {
			Account a = b.getAccount(accountName);
			new AccountGenerator().generate(new File(root, accountName
					+ ".html"), "account.tpl", a);
		}
		copyResources(root);
		new TransferGenerator().generate(root, b);

	}

	private void copyResources(File dir) {
		String[] resources = new String[] {
				"/datatable/images/back_enabled_hover.png",
				"/datatable/images/back_enabled.png",
				"/datatable/images/sort_desc.png",
				"/datatable/images/sort_both.png",
				"/datatable/images/Sorting icons.psd",
				"/datatable/images/sort_desc_disabled.png",
				"/datatable/images/forward_disabled.png",
				"/datatable/images/forward_enabled_hover.png",
				"/datatable/images/back_disabled.png",
				"/datatable/images/forward_enabled.png",
				"/datatable/images/sort_asc.png",
				"/datatable/images/favicon.ico",
				"/datatable/images/sort_asc_disabled.png",
				"/datatable/js/jquery.js",
				"/datatable/js/jquery.dataTables.js",
				"/datatable/js/jquery.dataTables.min.js",
				"/datatable/css/jquery.dataTables_themeroller.css",
				"/datatable/css/jquery.dataTables.css",
				"/img/glyphicons-halflings.png",
				"/img/glyphicons-halflings-white.png", "/js/bootstrap.min.js",
				"/js/bootstrap.js", "/css/bootstrap.min.css",
				"/css/bootstrap.css", "/css/bootstrap-responsive.css",
				"/css/bootstrap-responsive.min.css", };
		for (String resource : resources) {
			try {
				File output = new File(dir, resource);
				if (!output.getParentFile().exists()) {
					output.getParentFile().mkdirs();
				}
				FileWriter writer = new FileWriter(output);
				writer.write(new Scanner(getClass().getResourceAsStream(
						resource)).useDelimiter("\\Z").next());
				writer.close();
			} catch (Exception ex) {
				throw new RahakottException("Error during extract file: "
						+ resource + " from the jar");
			}
		}

	}
}
