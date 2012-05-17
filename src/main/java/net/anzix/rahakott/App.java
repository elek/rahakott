package net.anzix.rahakott;

import java.io.File;

import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.output.HtmlGenerator;
import net.anzix.rahakott.parser.Parser;

public class App {
	public static void main(String[] args) {
		new App().run(args);
	}

	private void run(String args[]) {
		Parser p = new Parser();
		File root = new File(args[0]);
		Book b = p.read(root);
		b.recalculate();
		new HtmlGenerator().output(b, new File(args[1]));

	}
}
