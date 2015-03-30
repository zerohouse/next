package uss.controllers.methods;

import java.io.UnsupportedEncodingException;

import next.mapping.annotation.HttpMethod;
import next.mapping.http.Http;

public class Methods {

	@HttpMethod("render")
	public void encording(Http http) {

		try {
			http.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
