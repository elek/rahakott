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
		PatternClassifier patternClassifier = Rahakott.read(new File(root,
				"classification.json"), PatternClassifier.class);
		
		CustomFieldProcessor customFieldProcessor = new CustomFieldProcessor();
		
		customFieldProcessor.process(root, b);
		
		
		customFieldProcessor.save(root, b);
		
		new ManualClassifier().parse(b);
		patternClassifier.parse(b);
		
		
		
		
		
		b.recalculate();
		new HtmlGenerator().output(b, new File(args[1]));

	}
}
