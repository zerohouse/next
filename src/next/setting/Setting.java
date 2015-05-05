package next.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Setting {
	private static Gson gson;

	private Mapping mapping = new Mapping();
	private Logger logger = new Logger();
	private Database database = new Database();

	private static Setting instance;

	public static Setting get() {
		return instance;
	}

	public static Gson getGson(){
		return gson;
	}
	
	static {
		Gson gsons = new Gson();
		try {
			instance = gsons.fromJson(new FileReader(Setting.class.getResource("/next.json").getFile()), Setting.class);
			gson = new GsonBuilder().setDateFormat(Setting.get().getMapping().getDateFormat()).create();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

}
