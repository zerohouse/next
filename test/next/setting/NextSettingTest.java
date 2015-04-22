package next.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;

import next.setting.Setting;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class NextSettingTest {

	@Test
	public void test() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		Setting setting = gson.fromJson(new FileReader(Setting.class.getResource("/nextSetting.json").getFile()), Setting.class);
		System.out.println(gson.toJson(setting));
	}

}
