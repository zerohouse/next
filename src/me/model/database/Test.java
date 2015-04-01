package me.model.database;

import next.database.annotation.Key;

public class Test {

	@Key
	private String userId;
	@Key
	private String name;
	private String result;

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResult() {
		return result;
	}

}
