package next.mapping.response;

import next.mapping.http.Http;
import next.setting.Setting;
import next.util.LoggerUtil;

import org.slf4j.Logger;

public class Json implements Response {

	private final static Logger logger = LoggerUtil.getLogger(Json.class);

	private Boolean error;
	private String errorMessage;
	private Object response;

	public Json() {
	}

	public Json(Boolean error, String errorMessage, Object object) {
		this.error = error;
		this.errorMessage = errorMessage;
		this.response = object;
	}

	public void setJsonObj(Object jsonObj) {
		this.response = jsonObj;
	}

	public Json(Object obj) {
		this.response = obj;
	}

	@Override
	public String toString() {
		return "Json [error=" + error + ", errorMessage=" + errorMessage + ", object=" + response + "]";
	}

	public Object getResponse() {
		return response;
	}

	public String getJsonString() {
		return Setting.getGson().toJson(this);
	}

	public void render(Http http) {
		http.setContentType("application/json");
		http.write(getJsonString());
		logger.debug(String.format("render : %s", getJsonString()));
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
