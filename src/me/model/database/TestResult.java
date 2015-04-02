package me.model.database;

import next.database.annotation.Key;

public class TestResult {

	@Key
	private String userId;
	@Key
	private String name;
	private String result;

	public String getUserId() {
		return userId;
	} 

	public void setName(String name) {
		this.name = name;
	}

	public void setResult(String result) {
		this.result = result;
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
