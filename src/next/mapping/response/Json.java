package next.mapping.response;

import next.mapping.http.Http;
import next.util.GsonInstance;

public class Json implements Response {

	private Boolean error;
	private String errorMessage;
	private Object object;

	public Json() {
	}

	public Json(Boolean error, String errorMessage, Object object) {
		super();
		this.error = error;
		this.errorMessage = errorMessage;
		this.object = object;
	}

	public void setJsonObj(Object jsonObj) {
		this.object = jsonObj;
	}

	public Json(Object obj) {
		this.object = obj;
	}

	@Override
	public String toString() {
		return "Json [error=" + error + ", errorMessage=" + errorMessage + ", object=" + object + "]";
	}

	public Object getObject() {
		return object;
	}

	public String getJsonString() {
		return GsonInstance.get().toJson(this);
	}

	public void render(Http http) {
		http.setContentType("application/json");
		http.write(getJsonString());
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
