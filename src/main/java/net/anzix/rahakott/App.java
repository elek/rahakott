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
		Book b = Rahakott.read(new File(root, "rahakott.json"), Book.class);
		p.read(root,b);
		PatternClassifier cl = Rahakott.read(new File(root,
				"classification.json"), PatternClassifier.class);
		
		ManualClassifier m = new ManualClassifier();
		m.save(root, b);
		m.process(root, b);
		cl.parse(b);
		b.recalculate();
		new HtmlGenerator().output(b, new File(args[1]));

	}
}
