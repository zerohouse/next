package me.controllers.methods;

import java.io.UnsupportedEncodingException;

import next.mapping.annotation.Before;
import next.mapping.http.Http;

public class Methods {

	@Before
	public void setFilter(Http http){
		try {
			http.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
