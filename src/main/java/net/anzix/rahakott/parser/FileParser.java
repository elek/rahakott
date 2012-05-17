package net.anzix.rahakott.parser;

import java.io.File;

import net.anzix.rahakott.model.Book;
import net.anzix.rahakott.model.config.AccountParsing;

public interface FileParser {

	void parse(Book b, File file, ParserContext context);

}
