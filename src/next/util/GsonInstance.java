package next.util;

import next.setting.Setting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonInstance {
	private static Gson gson = new GsonBuilder().setDateFormat(Setting.get().getMapping().getDateFormat()).create();
	
	public static Gson get(){
		return gson;
	}
	
}
