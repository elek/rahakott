package net.anzix.rahakott.model.config;

import java.io.File;
import java.io.FileReader;

import net.anzix.rahakott.RahakottException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Configuration {
	public BookConfiguration book = new BookConfiguration();

	public static <T> T read(File file, Class<? extends T> type) {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			FileReader reader = new FileReader(file);
			T c = gson.fromJson(reader, type);
			reader.close();
			return c;
		} catch (Exception ex) {
			throw new RahakottException(ex);
		}
	}
}
