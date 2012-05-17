package net.anzix.rahakott.model.output;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import net.anzix.rahakott.RahakottException;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class PageGenerator<T> {

	@Deprecated
	String diagram = "";

	String header = "";

	public void generate(File output, String templateName, T input) {
		try {

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			T object = input;
			Map<String, Object> values = populateVariables(input);
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

	protected Map<String, Object> populateVariables(T input) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("root", input);
		return values;
	}

	// public abstract T generateImpl(T input) throws Exception;
}
