package me.model.database;

import next.database.annotation.Key;

public class EmailAuth {
	
	@Key
	private String email;
	private String key;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
