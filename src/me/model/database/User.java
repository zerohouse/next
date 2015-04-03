package me.model.database;

import next.database.annotation.Column;
import next.database.annotation.Exclude;
import next.database.annotation.Key;
import next.database.annotation.OtherTable;
import next.database.annotation.RequiredRegex;

public class User {

	@Exclude
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@RequiredRegex(EMAIL_PATTERN)
	@Key
	private String email;
	@Column(DATA_TYPE = "TINYINT(1)")
	private Integer authEmail;
	private String password;
	@Column(DATA_TYPE = "TINYINT")
	private Integer gender;
	@Column(DATA_TYPE = "TINYINT")
	private Integer age;
	private String profileUrl;

	@OtherTable(COLUMN_NAME = "TestResult_result")
	private String mbti;
	@Exclude
	private Integer point;

	public Integer getAuthEmail() {
		return authEmail;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getMbti() {
		return mbti;
	}

	public void setMbti(String mbti) {
		this.mbti = mbti;
	}

}
