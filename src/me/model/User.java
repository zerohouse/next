package me.model;

import next.database.annotation.Column;
import next.database.annotation.Key;

public class User {

	@Key
	private String id;
	private String password;
	private String email;
	@Column(DATA_TYPE="TINYINT")
	private Integer gender;
	private String profileUrl;
	
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public Integer getGender() {
		return gender;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	
	
}
