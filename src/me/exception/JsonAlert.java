package me.exception;

import me.model.Result;
import next.mapping.exception.HandleException;
import next.mapping.http.Http;
import next.mapping.view.Json;

public class JsonAlert extends HandleException {

	private static final long serialVersionUID = -8132512868280285543L;

	public JsonAlert(String string) {
		super(string);
	}

	public void handle(Http http) {
		http.setView(new Json(new Result(true, getMessage())));
	}

}
