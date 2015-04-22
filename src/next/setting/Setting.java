package next.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Setting {
	private MappingSetting mapping;
	private LoggerSetting logger;
	private DatabaseSetting database;

	private static Setting instance;

	public static void set(Setting setting) {
		instance = setting;
	}

	public static Setting get() {
		return instance;
	}

	static {
		Gson gson = new Gson();
		try {
			instance = gson.fromJson(new FileReader(Setting.class.getResource("/nextSetting.json").getFile()), Setting.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Setting(MappingSetting mapping, DatabaseSetting database) {
		this.mapping = mapping;
		logger = new LoggerSetting();
		this.database = database;
	}

	public MappingSetting getMapping() {
		return mapping;
	}

	public void setMapping(MappingSetting mapping) {
		this.mapping = mapping;
	}

	public LoggerSetting getLogger() {
		return logger;
	}

	public void setLogger(LoggerSetting logger) {
		this.logger = logger;
	}

	public DatabaseSetting getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseSetting database) {
		this.database = database;
	}

}
