package net.anzix.rahakott;

import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Rahakott {
	public static final String UNKNOWN = "UNKNOWN";
	public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static <T> T read(File file, Class<? extends T> type) {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			FileReader reader = new FileReader(file);
			T c = gson.fromJson(reader, type);
			reader.close();
			return c;
		} catch (Exception ex) {
			throw new RahakottException("Error on loading file"
					+ file.getAbsolutePath(), ex);
		}
	}
}
