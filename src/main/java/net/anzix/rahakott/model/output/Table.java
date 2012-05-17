package net.anzix.rahakott.model.output;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Table {
	List<List<Object>> cells = new ArrayList<List<Object>>();

	public void addNewLine() {
		cells.add(new ArrayList<Object>());
	}

	public void addCell(Object value) {
		cells.get(cells.size() - 1).add(value);
	}
	
	public void output(PrintWriter writer){
		
	}

}
