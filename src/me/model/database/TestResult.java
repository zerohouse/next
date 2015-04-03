package me.model.database;

import next.database.annotation.Key;

public class TestResult {

	@Key
	private String userEmail;
	@Key
	private String name;
	private String result;

	public void setName(String name) {
		this.name = name;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getResult() {
		return result;
	}

}
