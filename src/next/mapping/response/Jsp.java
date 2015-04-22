package next.mapping.response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import next.mapping.http.Http;
import next.setting.Setting;

public class Jsp implements Response {

	private String jspFileName;

	private static final String PATH = Setting.get().getMapping().getJspPath();

	private Map<String, Object> variables = new HashMap<String, Object>();

	public Jsp() {
	}

	public void setJspFileName(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public Jsp(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public void put(String key, Object obj) {
		variables.put(key, obj);
	}

	public Object get(String key) {
		return variables.get(key);
	}

	public void render(Http http) {
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			http.setAttribute(entry.getKey(), entry.getValue());
		}
		try {
			http.forword(PATH + jspFileName);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	public String getJspFileName() {
		return jspFileName;
	}

	@Override
	public String toString() {
		return "Jsp [" + jspFileName + "] variables : " + variables;
	}

}
