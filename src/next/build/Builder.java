package next.build;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import next.build.jobject.JMap;
import next.setting.Setting;

import com.google.gson.stream.JsonReader;

/**
 * build.json을 Jobject로 인스턴스화합니다.<br>
 */
public class Builder {

	@Override
	public String toString() {
		return "Builder [builder=" + builder + "]";
	}

	JMap node;
	static Builder builder = new Builder();

	private Builder() {
		try {
			JsonReader reader = new JsonReader(new FileReader(Setting.class.getResource("/build.json").getFile()));
			node = new JMap(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: " + System.getProperty("user.dir"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String... keys) {
		return builder.node.get(keys).toString();
	}

	public static <T> T get(Class<T> type, String... keys) {
		return Setting.getGson().fromJson(get(keys), type);
	}

}
